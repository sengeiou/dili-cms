/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateItemServiceImpl.java created on 2021/1/21 16:01 by Henry.Huang 
  */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.AnnunciateItemMapper;
import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.glossary.AnnunciateItemOpType;
import com.dili.cms.sdk.glossary.ReadType;
import com.dili.cms.service.AnnunciateItemService;
import com.dili.cms.service.AnnunciateService;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

/**
  * <pre> 
  * Description 
  * 信息通告项service
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
public class AnnunciateItemServiceImpl extends BaseServiceImpl<AnnunciateItem, Long> implements AnnunciateItemService {

    @Autowired
    AnnunciateService annunciateService;

    public AnnunciateItemMapper getActualDao() {
        return (AnnunciateItemMapper)getDao();
    }

    private final Integer ONE=1;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseOutput readByAnnunciateId(Long annunciateId, Integer updateType) throws AppException{
        if(annunciateId==null){
            return BaseOutput.failure("未传入annunciateId！");
        }
        AnnunciateItem annunciateItem= DTOUtils.newInstance(AnnunciateItem.class);
        Example example=new Example(AnnunciateItem.class);
        if(AnnunciateItemOpType.OP_READ.getValue().equals(updateType)){
            annunciateItem.setReadState(ReadType.READ.getValue());
            annunciateItem.setReadTime(LocalDateTime.now());
            example.createCriteria().andEqualTo("annunciateId",annunciateId)
                    .andEqualTo("readState",ReadType.NO_READ.getValue());
            int updateCount=getActualDao().updateByExampleSelective(annunciateItem,example);
            if(updateCount==0){
                return BaseOutput.failure("标记已读数量为0");
            }
            AnnunciateDto annunciateDto = DTOUtils.newInstance(AnnunciateDto.class);
            annunciateDto.setId(annunciateId);
            annunciateDto.setReadCount(updateCount);
            int updateAnnunciateFlag= annunciateService.updateReadCountById(annunciateDto);
            if(updateAnnunciateFlag==0){
                throw new AppException("该通告不存在未读信息！");
            }
        }
        if(AnnunciateItemOpType.OP_DEL.getValue().equals(updateType)){
            annunciateItem.setReadState(ReadType.DELETE.getValue());
            example.createCriteria().andEqualTo("annunciateId",annunciateId)
                    .andNotEqualTo("readState",ReadType.DELETE.getValue());
            getActualDao().updateByExampleSelective(annunciateItem,example);
        }
        return BaseOutput.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput readByUserId(Long userId, Integer updateType) throws AppException{
        if(userId==null){
            return BaseOutput.failure("未传入userId！");
        }
        Example example=new Example(AnnunciateItem.class);
        AnnunciateItem annunciateItem= DTOUtils.newInstance(AnnunciateItem.class);
        if(AnnunciateItemOpType.OP_READ.getValue().equals(updateType)){
            example.createCriteria().andEqualTo("targetId",userId)
                    .andEqualTo("readState",ReadType.NO_READ.getValue());
            List<AnnunciateItem> annunciateItems=getActualDao().selectByExample(example);
            if(annunciateItems==null || annunciateItems.size()==0){
                BaseOutput.failure("该用户不存在未读信息！");
            }
            annunciateItem.setReadState(ReadType.READ.getValue());
            annunciateItem.setReadTime(LocalDateTime.now());
            int updateCount=getActualDao().updateByExampleSelective(annunciateItem,example);
            if(updateCount != annunciateItems.size()){
                throw new AppException("系统出错，请刷新后重试！");
            }
            for (AnnunciateItem obj : annunciateItems) {
                AnnunciateDto annunciateDto = DTOUtils.newInstance(AnnunciateDto.class);
                annunciateDto.setId(obj.getAnnunciateId());
                annunciateDto.setReadCount(ONE);
                annunciateService.updateReadCountById(annunciateDto);
            }
        }
        if(AnnunciateItemOpType.OP_DEL.getValue().equals(updateType)){
            annunciateItem.setReadState(ReadType.DELETE.getValue());
            example.createCriteria().andEqualTo("targetId",userId)
                    .andNotEqualTo("readState",ReadType.DELETE.getValue());
            getActualDao().updateByExampleSelective(annunciateItem,example);
        }
        return BaseOutput.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput readByAnnunciateItemId(Long annunciateId, Long targetId, Integer updateType) throws AppException{
        if(annunciateId==null){
            return BaseOutput.failure("未传入annunciateItemId！");
        }
        AnnunciateItem annunciateItem= DTOUtils.newInstance(AnnunciateItem.class);
        Example example=new Example(AnnunciateItem.class);
        if(AnnunciateItemOpType.OP_READ.getValue().equals(updateType)){
            annunciateItem.setReadState(ReadType.READ.getValue());
            annunciateItem.setReadTime(LocalDateTime.now());
            example.createCriteria().andEqualTo("annunciateId",annunciateId)
                    .andEqualTo("targetId",targetId)
                    .andEqualTo("readState",ReadType.NO_READ.getValue());
            int updateCount=getActualDao().updateByExampleSelective(annunciateItem,example);
            if(updateCount != ONE){
                throw new AppException("标记已读出错，请刷新重试！");
            }
            //获取annunciateID修改已读数量
            AnnunciateDto annunciateDto = DTOUtils.newInstance(AnnunciateDto.class);
            annunciateDto.setId(annunciateId);
            annunciateDto.setReadCount(ONE);
            int updateAnnunciateFlag= annunciateService.updateReadCountById(annunciateDto);
            if(updateAnnunciateFlag == 0){
                throw new AppException("更改已读失败,请刷新后重试！");
            }
        }
        if(AnnunciateItemOpType.OP_DEL.getValue().equals(updateType)){
            annunciateItem.setReadState(ReadType.DELETE.getValue());
            example.createCriteria().andEqualTo("annunciateId",annunciateId)
                    .andEqualTo("targetId",targetId)
                    .andNotEqualTo("readState",ReadType.DELETE.getValue());
            int updateAnnunciateFlag=getActualDao().updateByExampleSelective(annunciateItem,example);
            if(updateAnnunciateFlag == 0){
                throw new AppException("更改已读失败,请刷新后重试！");
            }
        }
        return BaseOutput.success();
    }

    @Override
    public BaseOutput<Integer> getNoReadCountByTargetId(AnnunciateDto annunciateDto) {
        AnnunciateItem annunciateItem= DTOUtils.newInstance(AnnunciateItem.class);
        if(annunciateDto==null){
            BaseOutput.successData(0);
        }
        annunciateItem.setTargetId(annunciateDto.getTargetId());
        annunciateItem.setReadState(ReadType.NO_READ.getValue());
        Integer onReadCount=getActualDao().selectCount(annunciateItem);
        return BaseOutput.successData(onReadCount);
    }
}