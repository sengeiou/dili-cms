/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateController.java created on 2021/1/21 9:38 by Henry.Huang
  */
package com.dili.cms.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.dili.cms.commons.Constants;
import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.cms.sdk.domain.AnnunciateTarget;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.cms.sdk.glossary.*;
import com.dili.cms.service.AnnunciateItemService;
import com.dili.cms.service.AnnunciateService;
import com.dili.cms.service.AnnunciateTargetService;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.domain.PageOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.metadata.ValueProviderUtils;
import com.dili.uap.sdk.domain.Department;
import com.dili.uap.sdk.domain.User;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.domain.dto.DepartmentDto;
import com.dili.uap.sdk.domain.dto.UserQuery;
import com.dili.uap.sdk.rpc.DepartmentRpc;
import com.dili.uap.sdk.rpc.UserRpc;
import com.dili.uap.sdk.session.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
  * <pre>
  * Description
  * 信息通告controller
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/21 Henry.Huang Initial version.
  * </pre>
  */
@Controller
@RequestMapping("/annunciate")
public class AnnunciateController extends BaseController{
    @Autowired
    AnnunciateService annunciateService;

    @Autowired
    AnnunciateTargetService annunciateTargetService;

    @Autowired
    AnnunciateItemService annunciateItemService;

    @Resource
    private DepartmentRpc departmentRpc;

    @Autowired
    UserRpc userRpc;

    /**
      * 进入信息通告列表页面
      * @param modelMap:
      * @return：java.lang.String
      * @author：Henry.Huang
      * @date：2021/1/21 9:38
      */
    @RequestMapping(value="/list.html", method = RequestMethod.GET)
    public String list(ModelMap modelMap) {
        modelMap.put("createTimeStart", LocalDate.now().minusDays(6) + " 00:00:00");
        modelMap.put("createTimeEnd", LocalDate.now() + " 23:59:59");
        return "annunciate/list";
    }

    /**
      * 信息通告分页
      * @param annunciateDto:
      * @return：java.lang.String
      * @author：Henry.Huang
      * @date：2021/1/21 9:38
      */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(AnnunciateDto annunciateDto) throws Exception {
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        annunciateDto.setFirmId(userTicket.getFirmId());
        PageOutput<List<AnnunciateVo>> output =annunciateService.listByQueryParams(annunciateDto);
        return new EasyuiPageOutput(output.getTotal(), ValueProviderUtils.buildDataByProvider(annunciateDto, output.getData())).toString();
    }

    /**
     * 进入信息通告新增页面
     * @param modelMap:
     * @return：java.lang.String
     * @author：Henry.Huang
     * @date：2021/1/21 9:38
     */
    @RequestMapping(value="/add.html", method = RequestMethod.GET)
    public String add(Long id,ModelMap modelMap) {
        if(id!=null){
            modelMap.put("opType", JSON.toJSONString("update"));
            //获取主数据
            Annunciate annunciate=annunciateService.get(id);
            if(annunciate.getStartTime()!=null){
                annunciate.setStartTimeStr(LocalDateTimeUtil.format(annunciate.getStartTime(), Constants.GLOBAL_HOUR_FORMAT));
            }
            annunciate.setEndTimeStr(LocalDateTimeUtil.format(annunciate.getEndTime(), Constants.GLOBAL_HOUR_FORMAT));
            modelMap.put("annunciate", JSON.toJSONString(annunciate));
            //获取通告目标
            AnnunciateTarget annunciateTarget=DTOUtils.newInstance(AnnunciateTarget.class);
            annunciateTarget.setAnnunciateId(id);
            List<AnnunciateTarget> annunciateTargets=annunciateTargetService.listByExample(annunciateTarget);
            modelMap.put("annunciateTargets", JSON.toJSONString(annunciateTargets));
            //如果是指定用户的需要查出用户数据进行填充到列表
            boolean flag=false;
            for (AnnunciateTarget obj:annunciateTargets) {
                if(AnnunciateTargetRange.APPOINT_USER.getValue().equals(obj.getTargetRange())){
                    //获取通告项
                    AnnunciateItem annunciateItem=DTOUtils.newInstance(AnnunciateItem.class);
                    annunciateItem.setAnnunciateId(id);
                    List<AnnunciateItem> annunciateItems=annunciateItemService.listByExample(annunciateItem);
                    List<String> ids=new ArrayList<>(annunciateItems.size());
                    for (AnnunciateItem objItem : annunciateItems) {
                        if(AnnunciateTargetType.SYSTEM_USER.getValue().equals(objItem.getTargetType())){
                            ids.add(objItem.getTargetId().toString());
                        }
                    }
                    UserQuery userQuery = DTOUtils.newInstance(UserQuery.class);
                    userQuery.setIds(ids);
                    BaseOutput<List<User>> userResult = userRpc.listByExample(userQuery);
                    if(userResult.isSuccess()){
                        modelMap.put("appointUsers",JSON.toJSONString(userResult.getData()));
                        flag=true;
                    }
                    break;
                }
            }
            if(!flag){
                modelMap.put("appointUsers",JSON.toJSONString(""));
            }
        }else{
            modelMap.put("opType", JSON.toJSONString("add"));
            modelMap.put("annunciate", JSON.toJSONString(""));
            modelMap.put("annunciateTargets", JSON.toJSONString(""));
            modelMap.put("appointUsers",JSON.toJSONString(""));
        }
        //获取部门列表
        DepartmentDto departmentDto = DTOUtils.newInstance(DepartmentDto.class);
        departmentDto.setFirmId(getFirmId());
        BaseOutput<List<Department>> departmentList = departmentRpc.listByExample(departmentDto);
        modelMap.put("departmentList", JSON.toJSONString(departmentList.getData()));
        return "annunciate/add";
    }

    /**
     * 查看页面
     * @param modelMap:
     * @return：java.lang.String
     * @author：Ron.Peng
     * @date：2021/1/26 15:34
     */
    @RequestMapping(value="/view.html", method = RequestMethod.GET)
    public String view(Long id,ModelMap modelMap) {
        //获取主数据
        Annunciate annunciate=annunciateService.get(id);
        if(annunciate.getStartTime()!=null){
            annunciate.setStartTimeStr(LocalDateTimeUtil.format(annunciate.getStartTime(), Constants.GLOBAL_HOUR_FORMAT));
        }
        annunciate.setEndTimeStr(LocalDateTimeUtil.format(annunciate.getEndTime(), Constants.GLOBAL_HOUR_FORMAT));
        modelMap.put("annunciate", JSON.toJSONString(annunciate));
        //获取通告目标
        AnnunciateTarget annunciateTarget=DTOUtils.newInstance(AnnunciateTarget.class);
        annunciateTarget.setAnnunciateId(id);
        List<AnnunciateTarget> annunciateTargets=annunciateTargetService.listByExample(annunciateTarget);
        modelMap.put("annunciateTargets", JSON.toJSONString(annunciateTargets));
        //如果是指定用户的需要查出用户数据进行填充到列表
        boolean flag=false;
        for (AnnunciateTarget obj:annunciateTargets) {
            if(AnnunciateTargetRange.APPOINT_USER.getValue().equals(obj.getTargetRange())){
                //获取通告项
                AnnunciateItem annunciateItem=DTOUtils.newInstance(AnnunciateItem.class);
                annunciateItem.setAnnunciateId(id);
                List<AnnunciateItem> annunciateItems=annunciateItemService.listByExample(annunciateItem);
                List<String> ids=new ArrayList<>(annunciateItems.size());
                for (AnnunciateItem objItem : annunciateItems) {
                    if(AnnunciateTargetType.SYSTEM_USER.getValue().equals(objItem.getTargetType())){
                        ids.add(objItem.getTargetId().toString());
                    }
                }
                UserQuery userQuery = DTOUtils.newInstance(UserQuery.class);
                userQuery.setIds(ids);
                BaseOutput<List<User>> userResult = userRpc.listByExample(userQuery);
                if(userResult.isSuccess()){
                    modelMap.put("appointUsers",JSON.toJSONString(userResult.getData()));
                    flag=true;
                }
                break;
            }
        }
        if(!flag){
            modelMap.put("appointUsers",JSON.toJSONString(""));
        }
        //获取部门列表
        DepartmentDto departmentDto = DTOUtils.newInstance(DepartmentDto.class);
        departmentDto.setFirmId(getFirmId());
        BaseOutput<List<Department>> departmentList = departmentRpc.listByExample(departmentDto);
        modelMap.put("departmentList", JSON.toJSONString(departmentList.getData()));
        return "annunciate/view";
    }

    /**
     * TODO 进入已读页面
     * @param modelMap:
     * @return：java.lang.String
     * @author：Ron.Peng
     * @date：2021/1/26 15:34
     */
    @RequestMapping(value="/viewReaded.html", method = RequestMethod.GET)
    public String viewReaded(ModelMap modelMap, Long id) {
        modelMap.put("readTimeStart", LocalDate.now().minusDays(6) + " 00:00:00");
        modelMap.put("readTimeEnd", LocalDate.now() + " 23:59:59");
        modelMap.put("annunciateId",id);
        return "annunciate/viewReaded";
    }

    /**
      * 信息通告新增
      * @param annunciateDto:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/21 9:42
      */
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput insert(@RequestBody AnnunciateDto annunciateDto) {
        String tips=checkAnnunciate(annunciateDto);
        if (!"".equals(tips)){
            return BaseOutput.failure(tips);
        }
        setDefaultValue(annunciateDto);
        return annunciateService.insertAnnunciate(annunciateDto);
    }

    /**
      * 信息通告修改
      * @param annunciateDto:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/21 9:42
      */
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput update(@RequestBody AnnunciateDto annunciateDto) {
        String tips=checkAnnunciate(annunciateDto);
        if (!"".equals(tips)){
            return BaseOutput.failure(tips);
        }
        setUpdateDefaultValue(annunciateDto);
        return annunciateService.updateAnnunciate(annunciateDto);
    }

    /**
      * 信息通告删除
      * @param id:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/21 9:42
      */
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput delete(Long id) {
        return annunciateService.deleteAnnunciate(id);
    }

    /**
     * 信息通告置顶
     * @param id:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Henry.Huang
     * @date：2021/1/21 9:42
     */
    @RequestMapping(value="/stick.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput stick(Long id) {
        return annunciateService.stick(id);
    }

    /**
     * 信息通告撤销
     * @param id:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Henry.Huang
     * @date：2021/1/21 9:42
     */
    @RequestMapping(value="/revoke.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput revoke(Long id) {
        if(id==null){
            BaseOutput.failure("参数请求错误！");
        }
        Annunciate annunciate=annunciateService.get(id);
        if(annunciate==null){
            BaseOutput.failure("通告不存在");
        }
        if(!AnnunciateSendState.PUBLISH.getValue().equals(annunciate.getSendState())){
            BaseOutput.failure("只有已发布的消息才能撤销");
        }
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        annunciate.setVersion(annunciate.getVersion()+1);
        annunciate.setModifyTime(LocalDateTime.now());
        annunciate.setModifierId(userTicket.getId());
        annunciate.setSendState(AnnunciateSendState.REVOKE.getValue());
        return annunciateService.revoke(annunciate);
    }

    /**
     * 校验 Annunciate
     * @param annunciateDto:
     * @return：java.lang.String
     * @author：Henry.Huang
     * @date：2021/1/22 18:01
     */
    private String checkAnnunciate(AnnunciateDto annunciateDto) {
        StringBuffer tips=new StringBuffer();
        if(annunciateDto.getAnnunciateTargets()==null){
            tips.append("发送对象、发送范围不能为空,");
        }
        //校验开始时间必须小于发布时间
        if(AnnunciatePublishType.SYSTEM_USER.getValue().equals(annunciateDto.getPublishType())){
            if(LocalDateTime.now().isAfter(annunciateDto.getEndTime())){
                tips.append("立即发布则需要结束时间大于当前时间1小时,");
            }
        }else{
            if(LocalDateTime.now().isAfter(annunciateDto.getStartTime())){
                tips.append("开始时间必须大于当前时间1小时,");
            }
            if(annunciateDto.getStartTime().isAfter(annunciateDto.getEndTime())){
                tips.append("开始时间必须大于结束时间,");
            }
        }
        if(tips.length()>0){
            return tips.substring(0,tips.length()-1);
        }
        return tips.toString();
    }

    /**
      * 设置默认值
      * @param annunciateDto:
      * @return：void
      * @author：Henry.Huang
      * @date：2021/1/22 17:53
      */
    private void setDefaultValue(AnnunciateDto annunciateDto) {
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        annunciateDto.setCreateTime(LocalDateTime.now());
        annunciateDto.setCreatorId(userTicket.getId());
        annunciateDto.setModifierId(userTicket.getId());
        annunciateDto.setModifyTime(LocalDateTime.now());
        annunciateDto.setType(AnnunciateType.PLATFFORM.getValue());
        annunciateDto.setStickState(AnnunciateStickState.NORMAL.getValue());
        annunciateDto.setVersion(0);
        annunciateDto.setReadCount(0);
        annunciateDto.setFirmId(userTicket.getFirmId());
        if(AnnunciatePublishType.SYSTEM_USER.getValue().equals(annunciateDto.getPublishType())){
            annunciateDto.setSendState(AnnunciateSendState.PUBLISH.getValue());
            annunciateDto.setStartTime(LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now(), Constants.GLOBAL_HOUR_FORMAT), Constants.GLOBAL_HOUR_FORMAT));
        }else {
            annunciateDto.setSendState(AnnunciateSendState.CREATED.getValue());
        }
    }

    /**
      * 更新设置值
      * @param annunciateDto:
      * @return：void
      * @author：Henry.Huang
      * @date：2021/1/23 15:43
      */
    private void setUpdateDefaultValue(AnnunciateDto annunciateDto) {
        UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        annunciateDto.setFirmId(userTicket.getFirmId());
        annunciateDto.setModifyTime(LocalDateTime.now());
        annunciateDto.setModifierId(userTicket.getId());
        if(AnnunciatePublishType.SYSTEM_USER.getValue().equals(annunciateDto.getPublishType())){
            annunciateDto.setSendState(AnnunciateSendState.PUBLISH.getValue());
            annunciateDto.setStartTime(LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now(), Constants.GLOBAL_HOUR_FORMAT), Constants.GLOBAL_HOUR_FORMAT));
        }else {
            annunciateDto.setSendState(AnnunciateSendState.CREATED.getValue());
        }
    }
}