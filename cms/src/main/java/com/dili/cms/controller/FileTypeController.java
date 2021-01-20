/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeController.java created on 2021/1/20 15:32 by Tab.Xie
 */
package com.dili.cms.controller;

import com.dili.cms.domain.FileType;
import com.dili.cms.service.FileTypeService;
import com.dili.ss.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <pre>
 * Description
 * TODO 文件类型管理
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Controller
@RequestMapping("/fileType")
public class FileTypeController extends BaseController {
    @Autowired
    FileTypeService fileTypeService;


    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "fileType/index";
    }


    @RequestMapping(value = "/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<FileType> list(FileType fileType) {
        return fileTypeService.list(fileType);
    }


    @RequestMapping(value = "/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(FileType fileType) throws Exception {
        return fileTypeService.listEasyuiPageByExample(fileType, true).toString();
    }


    @RequestMapping(value = "/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput insert(FileType fileType) {
        fileTypeService.insertSelective(fileType);
        return BaseOutput.success("新增成功");
    }


    @RequestMapping(value = "/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput update(FileType fileType) {
        fileTypeService.updateSelective(fileType);
        return BaseOutput.success("修改成功");
    }


    @RequestMapping(value = "/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput delete(Long id) {
        fileTypeService.delete(id);
        return BaseOutput.success("删除成功");
    }
}