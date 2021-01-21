/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateTargetController.java created on 2021/1/21 16:00 by Henry.Huang 
  */
package com.dili.cms.controller;

import com.dili.cms.sdk.domain.AnnunciateTarget;
import com.dili.cms.service.AnnunciateTargetService;
import com.dili.ss.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
  * <pre> 
  * Description 
  * TODO file description here 
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
@RequestMapping("/annunciateTarget")
public class AnnunciateTargetController{
    @Autowired
    AnnunciateTargetService annunciateTargetService;

    /**
     * 跳转到AnnunciateTarget页面
     * @param modelMap
     * @return String
     */
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "annunciateTarget/index";
    }

    /**
     * 分页查询AnnunciateTarget，返回easyui分页信息
     * @param annunciateTarget
     * @return String
     * @throws Exception
     */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(AnnunciateTarget annunciateTarget) throws Exception {
        return annunciateTargetService.listEasyuiPageByExample(annunciateTarget, true).toString();
    }

    /**
     * 新增AnnunciateTarget
     * @param annunciateTarget
     * @return BaseOutput
     */
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(AnnunciateTarget annunciateTarget) {
        annunciateTargetService.insertSelective(annunciateTarget);
        return BaseOutput.success("新增成功");
    }

    /**
     * 修改AnnunciateTarget
     * @param annunciateTarget
     * @return BaseOutput
     */
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(AnnunciateTarget annunciateTarget) {
        annunciateTargetService.updateSelective(annunciateTarget);
        return BaseOutput.success("修改成功");
    }

    /**
     * 删除AnnunciateTarget
     * @param id
     * @return BaseOutput
     */
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        annunciateTargetService.delete(id);
        return BaseOutput.success("删除成功");
    }
}