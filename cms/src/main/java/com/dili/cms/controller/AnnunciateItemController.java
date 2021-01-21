/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateItemController.java created on 2021/1/21 16:00 by Henry.Huang 
  */
package com.dili.cms.controller;

import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.cms.service.AnnunciateItemService;
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
@RequestMapping("/annunciateItem")
public class AnnunciateItemController{
    @Autowired
    AnnunciateItemService annunciateItemService;

    /**
     * 跳转到AnnunciateItem页面
     * @param modelMap
     * @return String
     */
    @RequestMapping(value="/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "annunciateItem/index";
    }

    /**
     * 分页查询AnnunciateItem，返回easyui分页信息
     * @param annunciateItem
     * @return String
     * @throws Exception
     */
    @RequestMapping(value="/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String listPage(AnnunciateItem annunciateItem) throws Exception {
        return annunciateItemService.listEasyuiPageByExample(annunciateItem, true).toString();
    }

    /**
     * 新增AnnunciateItem
     * @param annunciateItem
     * @return BaseOutput
     */
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput insert(AnnunciateItem annunciateItem) {
        annunciateItemService.insertSelective(annunciateItem);
        return BaseOutput.success("新增成功");
    }

    /**
     * 修改AnnunciateItem
     * @param annunciateItem
     * @return BaseOutput
     */
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput update(AnnunciateItem annunciateItem) {
        annunciateItemService.updateSelective(annunciateItem);
        return BaseOutput.success("修改成功");
    }

    /**
     * 删除AnnunciateItem
     * @param id
     * @return BaseOutput
     */
    @RequestMapping(value="/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseOutput delete(Long id) {
        annunciateItemService.delete(id);
        return BaseOutput.success("删除成功");
    }
}