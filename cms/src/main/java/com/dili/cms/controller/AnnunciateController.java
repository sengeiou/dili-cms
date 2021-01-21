/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateController.java created on 2021/1/21 9:38 by Henry.Huang
  */
package com.dili.cms.controller;

import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.cms.service.AnnunciateService;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.domain.PageOutput;
import com.dili.ss.metadata.ValueProviderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
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
public class AnnunciateController{
    @Autowired
    AnnunciateService annunciateService;

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
    public String add(ModelMap modelMap) {
        return "annunciate/add";
    }

    /**
      * 信息通告新增
      * @param annunciate:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/21 9:42
      */
    @RequestMapping(value="/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput insert(Annunciate annunciate) {
        annunciateService.insertSelective(annunciate);
        return BaseOutput.success("新增成功");
    }

    /**
      * 信息通告修改
      * @param annunciate:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/21 9:42
      */
    @RequestMapping(value="/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput update(Annunciate annunciate) {
        annunciateService.updateSelective(annunciate);
        return BaseOutput.success("修改成功");
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
        annunciateService.delete(id);
        return BaseOutput.success("删除成功");
    }
}