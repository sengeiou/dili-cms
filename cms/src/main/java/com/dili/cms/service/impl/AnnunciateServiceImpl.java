/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateServiceImpl.java created on 2021/1/21 13:33 by Henry.Huang
  */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.AnnunciateMapper;
import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.cms.sdk.domain.AnnunciateTarget;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.cms.sdk.glossary.*;
import com.dili.cms.service.AnnunciateItemService;
import com.dili.cms.service.AnnunciateService;
import com.dili.cms.service.AnnunciateTargetService;
import com.dili.customer.sdk.domain.CharacterType;
import com.dili.customer.sdk.domain.Customer;
import com.dili.customer.sdk.domain.dto.CustomerQueryInput;
import com.dili.customer.sdk.domain.dto.CustomerSimpleExtendDto;
import com.dili.customer.sdk.enums.CustomerEnum;
import com.dili.customer.sdk.rpc.CustomerRpc;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.PageOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.exception.AppException;
import com.dili.uap.sdk.domain.User;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.domain.dto.AnnunciateMessages;
import com.dili.uap.sdk.domain.dto.UserQuery;
import com.dili.uap.sdk.rpc.AnnunciateMessageRpc;
import com.dili.uap.sdk.rpc.UserRpc;
import com.dili.uap.sdk.session.SessionContext;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
  * <pre>
  * Description
  * 信息通告service
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/21 Henry.Huang Initial version.
  * </pre>
  */
@Service
public class AnnunciateServiceImpl extends BaseServiceImpl<Annunciate, Long> implements AnnunciateService {

    @Autowired
    AnnunciateItemService annunciateItemService;

    @Autowired
    AnnunciateTargetService annunciateTargetService;

    @Autowired
    UserRpc userRpc;

    @Autowired
    CustomerRpc customerRpc;

    @Autowired
    AnnunciateMessageRpc annunciateMessageRpc;

    private final Integer USER_NORMAL=1;

    public AnnunciateMapper getActualDao() {
        return (AnnunciateMapper) getDao();
    }

    @Override
    public PageOutput<List<AnnunciateVo>> listByQueryParams(AnnunciateDto annunciateDto) {
        Integer page = annunciateDto.getPage();
        page = (page == null) ? Integer.valueOf(1) : page;
        if (annunciateDto.getRows() != null && annunciateDto.getRows() >= 1) {
            PageHelper.startPage(page, annunciateDto.getRows());
        }
        List<AnnunciateVo> list = getActualDao().listByQueryParams(annunciateDto);
        Long total = list instanceof Page ? ((Page) list).getTotal() : list.size();
        int totalPage = list instanceof Page ? ((Page) list).getPages() : 1;
        int pageNum = list instanceof Page ? ((Page) list).getPageNum() : 1;
        PageOutput<List<AnnunciateVo>> output = PageOutput.success();
        output.setData(list).setPageNum(pageNum).setTotal(total).setPageSize(annunciateDto.getPage()).setPages(totalPage);
        return output;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateReadCountById(AnnunciateDto annunciateDto) {
        return getActualDao().updateReadCountById(annunciateDto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput bachUpdateReadCountById(List<Annunciate> annunciates) {
        getActualDao().bachUpdateReadCountById(annunciates);
        return BaseOutput.success();
    }

    @Override
    public BaseOutput<List<AnnunciateVo>> getListByUserId(Long userId) {
        List<AnnunciateVo> annunciates = getActualDao().getListByUserId(userId);
        return BaseOutput.successData(annunciates);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput insertAnnunciate(AnnunciateDto annunciateDto) throws AppException {
        //保存主体
        getActualDao().insert(annunciateDto);
        //通告项存放点
        List<AnnunciateItem> annunciateItems = new ArrayList<>();
        //通告项设置
        if (annunciateDto.getAnnunciateTargets() != null && annunciateDto.getAnnunciateTargets().size()>0) {
            setAnnunciteItems(annunciateDto, annunciateItems);
            annunciateTargetService.batchInsert(annunciateDto.getAnnunciateTargets());
            //保存通告项
            if (annunciateItems.size()>0) {
                annunciateItemService.batchInsert(annunciateItems);
                //保存成功后将信息推送给uap待办 立即发布才发
                if(AnnunciateSendState.PUBLISH.getValue().equals(annunciateDto.getSendState())){
                    AnnunciateMessages annunciateMessages=DTOUtils.newInstance(AnnunciateMessages.class);
                    List<String> targetIds=new ArrayList<>(annunciateItems.size());
                    for (AnnunciateItem annunciateItem:annunciateItems) {
                        targetIds.add(annunciateItem.getTargetId().toString());
                    }
                    annunciateMessages.setTargetIds(targetIds);
                    setAnnunciateMessagesValue(annunciateMessages,annunciateDto);
                    annunciateMessageRpc.sendAnnunciates(annunciateMessages);
                }
            }
        }
        return BaseOutput.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput updateAnnunciate(AnnunciateDto annunciateDto) throws AppException{
        //乐观锁更新主数据
        Example example=new Example(Annunciate.class);
        example.createCriteria().andEqualTo("id",annunciateDto.getId())
                .andEqualTo("version",annunciateDto.getVersion());
        annunciateDto.setVersion(annunciateDto.getVersion()+1);
        int updateFlag=getActualDao().updateByExampleExact(annunciateDto,example);
        if(updateFlag!=1){
            throw new AppException("更新数据失败，请重新打开页面进行编辑提交！");
        }
        //通告项存放点
        List<AnnunciateItem> annunciateItems = new ArrayList<>();
        //先删除旧的通告项和通告目标
        AnnunciateTarget deleteTarget=DTOUtils.newInstance(AnnunciateTarget.class);
        deleteTarget.setAnnunciateId(annunciateDto.getId());
        annunciateTargetService.deleteByExample(deleteTarget);
        AnnunciateItem deleteItem=DTOUtils.newInstance(AnnunciateItem.class);
        deleteItem.setAnnunciateId(annunciateDto.getId());
        annunciateItemService.deleteByExample(deleteItem);
        //通告项设置
        if (annunciateDto.getAnnunciateTargets() != null && annunciateDto.getAnnunciateTargets().size()>0) {
            setAnnunciteItems(annunciateDto, annunciateItems);
            annunciateTargetService.batchInsert(annunciateDto.getAnnunciateTargets());
            //保存通告项
            if (annunciateItems.size()>0) {
                annunciateItemService.batchInsert(annunciateItems);
                //保存成功后将信息推送给uap待办 立即发布才发 updateType 1为未发布更新 2位已撤销更新 只有未发布更新才发送消息到uap
                if(AnnunciateSendState.PUBLISH.getValue().equals(annunciateDto.getSendState())){
                    AnnunciateMessages annunciateMessages=DTOUtils.newInstance(AnnunciateMessages.class);
                    List<String> targetIds=new ArrayList<>(annunciateItems.size());
                    for (AnnunciateItem annunciateItem:annunciateItems) {
                        targetIds.add(annunciateItem.getTargetId().toString());
                    }
                    annunciateMessages.setTargetIds(targetIds);
                    setAnnunciateMessagesValue(annunciateMessages,annunciateDto);
                    annunciateMessageRpc.sendAnnunciates(annunciateMessages);
                }
            }
        }
        return BaseOutput.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput deleteAnnunciate(Long id) throws AppException{
        List<Integer> sendList=new ArrayList<>(2);
        sendList.add(AnnunciateSendState.CREATED.getValue());
        sendList.add(AnnunciateSendState.REVOKE.getValue());
        Example example=new Example(Annunciate.class);
        example.createCriteria().andEqualTo("id",id)
                .andIn("sendState",sendList);
        int deleteFlag=getActualDao().deleteByExample(example);
        if(deleteFlag!=1){
            throw new AppException("删除失败，刷新列表后重试");
        }
        AnnunciateItem deleteItem=DTOUtils.newInstance(AnnunciateItem.class);
        deleteItem.setAnnunciateId(id);
        annunciateItemService.deleteByExample(deleteItem);
        AnnunciateTarget deleteTarget=DTOUtils.newInstance(AnnunciateTarget.class);
        deleteTarget.setAnnunciateId(id);
        annunciateTargetService.deleteByExample(deleteTarget);
        return BaseOutput.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput stick(Long id) throws AppException{
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        //先把所有公告改成非置顶锁住数据
        Annunciate noStickAnnunciate=DTOUtils.newInstance(Annunciate.class);
        noStickAnnunciate.setSendState(AnnunciateSendState.PUBLISH.getValue());
        noStickAnnunciate.setEndTime(LocalDateTime.now());
        noStickAnnunciate.setFirmId(userTicket.getFirmId());
        getActualDao().updateAllNoStick(noStickAnnunciate);
        Annunciate annunciate=get(id);
        //校验数据
        if(annunciate==null){
            throw new AppException("数据不存在,无法置顶！");
        }
        if(!AnnunciateSendState.PUBLISH.getValue().equals(annunciate.getSendState())
                || LocalDateTime.now().isAfter(annunciate.getEndTime())
                || AnnunciateStickState.STICK.getValue().equals(annunciate.getStickState())){
            throw new AppException("数据不存在或发布时间已结束或消息已为置顶状态,无法置顶！");
        }
        annunciate.setStickState(AnnunciateStickState.STICK.getValue());
        annunciate.setModifierId(userTicket.getId());
        annunciate.setModifyTime(LocalDateTime.now());
        annunciate.setVersion(annunciate.getVersion()+1);
        getActualDao().updateByPrimaryKey(annunciate);
        //先将所有发布中的
        return BaseOutput.success();
    }

    @Override
    public BaseOutput revoke(Annunciate annunciate) throws AppException{
        Example example=new Example(Annunciate.class);
        example.createCriteria().andEqualTo("id",annunciate.getId())
                .andEqualTo("sendState",AnnunciateSendState.PUBLISH.getValue());
        int revokeFlag=getActualDao().updateByExampleSelective(annunciate,example);
        if(revokeFlag!=1){
            throw new AppException("只有已发布的信息通告才能撤销！");
        }
        //将通告项改为已撤销
        AnnunciateItem annunciateItemCondition=DTOUtils.newInstance(AnnunciateItem.class);
        annunciateItemCondition.setAnnunciateId(annunciate.getId());
        AnnunciateItem annunciateItem=DTOUtils.newInstance(AnnunciateItem.class);
        annunciateItem.setReadState(ReadType.DELETE.getValue());
        annunciateItemService.updateSelectiveByExample(annunciateItem,annunciateItemCondition);
        //调用uap接口进行撤销消息
        annunciateItemCondition.setTargetType(AnnunciateTargetType.SYSTEM_USER.getValue());
        List<AnnunciateItem> annunciateItems=annunciateItemService.listByExample(annunciateItemCondition);
        if(annunciateItems!=null&&annunciateItems.size()>0){
            AnnunciateMessages annunciateMessages=DTOUtils.newInstance(AnnunciateMessages.class);
            List<String> targetIds=new ArrayList<>(annunciateItems.size());
            for (AnnunciateItem obj:annunciateItems) {
                targetIds.add(obj.getTargetId().toString());
            }
            annunciateMessages.setTargetIds(targetIds);
            annunciateMessages.setId(annunciate.getId());
            annunciateMessageRpc.withdrawAnnunciates(annunciateMessages);
        }
        return BaseOutput.success();
    }

    /**
      * 生成uap平台消息
      * @param annunciateMessages:
      * @param annunciateDto:
      * @return：com.dili.uap.sdk.domain.dto.AnnunciateMessage
      * @author：Henry.Huang
      * @date：2021/1/23 15:47
      */
    private void setAnnunciateMessagesValue(AnnunciateMessages annunciateMessages, AnnunciateDto annunciateDto) {
        annunciateMessages.setId(annunciateDto.getId());
        annunciateMessages.setTitle(annunciateDto.getTitle());
        annunciateMessages.setType(annunciateDto.getType());
    }

    /**
     * 信息通告项设置值
     *
     * @param annunciateDto:
     * @param user:
     * @param customer:
     * @return：com.dili.cms.sdk.domain.AnnunciateItem
     * @author：Henry.Huang
     * @date：2021/1/23 10:07
     */
    private AnnunciateItem setAnnunciateItemValue(AnnunciateDto annunciateDto, User user, Customer customer,AnnunciateItem annunciateItemNew) {
        if(annunciateItemNew!=null){
            annunciateItemNew.setAnnunciateId(annunciateDto.getId());
            annunciateItemNew.setReadState(ReadType.NO_READ.getValue());
            annunciateItemNew.setSendTime(annunciateDto.getStartTime());
            annunciateItemNew.setTargetType(AnnunciateTargetType.SYSTEM_USER.getValue());
            annunciateItemNew.setCreateTime(annunciateDto.getCreateTime());
            return annunciateItemNew;
        }
        AnnunciateItem annunciateItem = DTOUtils.newInstance(AnnunciateItem.class);
        if (user != null) {
            annunciateItem.setAnnunciateId(annunciateDto.getId());
            annunciateItem.setTargetId(user.getId());
            annunciateItem.setReadState(ReadType.NO_READ.getValue());
            annunciateItem.setSendTime(annunciateDto.getStartTime());
            annunciateItem.setCreateTime(annunciateDto.getCreateTime());
            annunciateItem.setTargetType(AnnunciateTargetType.SYSTEM_USER.getValue());
            annunciateItem.setTargetName(user.getRealName());
        }
        if (customer != null) {
            annunciateItem.setAnnunciateId(annunciateDto.getId());
            annunciateItem.setTargetId(customer.getId());
            annunciateItem.setReadState(ReadType.NO_READ.getValue());
            annunciateItem.setSendTime(annunciateDto.getStartTime());
            annunciateItem.setCreateTime(annunciateDto.getCreateTime());
            annunciateItem.setTargetType(AnnunciateTargetType.CUSTOMER.getValue());
        }
        return annunciateItem;
    }

    /**
      * 组装通告项数据
      * @param annunciateDto:
      * @param annunciateItems:
      * @return：void
      * @author：Henry.Huang
      * @date：2021/1/23 16:10
      */
    private void setAnnunciteItems(AnnunciateDto annunciateDto, List<AnnunciateItem> annunciateItems) {
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        for (AnnunciateTarget annunciateTarget : annunciateDto.getAnnunciateTargets()) {
            annunciateTarget.setAnnunciateId(annunciateDto.getId());
            //所有用户
            if (AnnunciateTargetRange.ALL_USER.getValue().equals(annunciateTarget.getTargetRange())) {
                UserQuery userQuery = DTOUtils.newInstance(UserQuery.class);
                userQuery.setState(USER_NORMAL);
                userQuery.setFirmCode(userTicket.getFirmCode());
                BaseOutput<List<User>> allUserResult = userRpc.list(userQuery);
                if (!allUserResult.isSuccess()) {
                    throw new AppException("查询所有用户失败");
                }
                for (User user : allUserResult.getData()) {
                    annunciateItems.add(setAnnunciateItemValue(annunciateDto, user, null,null));
                }
            }
            //指定用户
            if(AnnunciateTargetRange.APPOINT_USER.getValue().equals(annunciateTarget.getTargetRange())
                    &&annunciateDto.getAnnunciateItems() != null){
                for (AnnunciateItem annunciateItem : annunciateDto.getAnnunciateItems()) {
                    annunciateItems.add(setAnnunciateItemValue(annunciateDto, null, null,annunciateItem));
                }
            }
            //指定部门
            if(AnnunciateTargetRange.DEPARTMENT.getValue().equals(annunciateTarget.getTargetRange())){
                UserQuery userQuery = DTOUtils.newInstance(UserQuery.class);
                userQuery.setState(USER_NORMAL);
                userQuery.setFirmCode(userTicket.getFirmCode());
                userQuery.setDepartmentId(annunciateTarget.getTargetRangeId());
                BaseOutput<List<User>> departmentUserResult = userRpc.list(userQuery);
                if (!departmentUserResult.isSuccess()) {
                    throw new AppException("根据部门查询用户失败");
                }
                for (User user : departmentUserResult.getData()) {
                    annunciateItems.add(setAnnunciateItemValue(annunciateDto, user, null,null));
                }
            }
            //公司暂时没有需求
            //if(AnnunciateTargetRange.COMPANY.getValue().equals(annunciateTarget.getTargetRange())){
            //}
            //客户
            if(AnnunciateTargetRange.DRIVER.getValue().equals(annunciateTarget.getTargetRange())
                    || AnnunciateTargetRange.BUYER.getValue().equals(annunciateTarget.getTargetRange())
                    || AnnunciateTargetRange.SELLER.getValue().equals(annunciateTarget.getTargetRange())){
                String customerType=null;
                if(AnnunciateTargetRange.DRIVER.getValue().equals(annunciateTarget.getTargetRange())){
                    customerType= CustomerEnum.CharacterType.其他类型.getValue();
                }
                //买家
                if(AnnunciateTargetRange.BUYER.getValue().equals(annunciateTarget.getTargetRange())){
                    customerType=CustomerEnum.CharacterType.买家.getValue();
                }
                //卖家
                if(AnnunciateTargetRange.SELLER.getValue().equals(annunciateTarget.getTargetRange())){
                    customerType=CustomerEnum.CharacterType.经营户.getValue();
                }
                CustomerQueryInput customerQueryInput=new CustomerQueryInput();
                CharacterType characterType=new CharacterType();
                characterType.setCharacterType(customerType);
                customerQueryInput.setCharacterType(characterType);
                customerQueryInput.setMarketId(annunciateDto.getFirmId());
                BaseOutput<List<CustomerSimpleExtendDto>> customerResult= customerRpc.listSimple(customerQueryInput);
                if (!customerResult.isSuccess()) {
                    throw new AppException("查询其他客户类型失败");
                }
                for (CustomerSimpleExtendDto customerSimpleExtendDto : customerResult.getData()) {
                    annunciateItems.add(setAnnunciateItemValue(annunciateDto, null, customerSimpleExtendDto,null));
                }
            }
        }
    }
}