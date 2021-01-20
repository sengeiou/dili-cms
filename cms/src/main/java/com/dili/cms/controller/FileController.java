/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileController.java created on 2021/1/20 15:31 by Tab.Xie
 */
package com.dili.cms.controller;

import com.dili.cms.domain.File;
import com.dili.cms.service.FileService;
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
 * TODO 文件管理
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
@RequestMapping("/file")
public class FileController extends BaseController {
    @Autowired
    FileService fileService;

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "file/index";
    }


    @RequestMapping(value = "/list.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<File> list(File file) {
        return fileService.list(file);
    }


    @RequestMapping(value = "/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(File file) throws Exception {
        return fileService.listEasyuiPageByExample(file, true).toString();
    }


    @RequestMapping(value = "/insert.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput insert(File file) {
        fileService.insertSelective(file);
        return BaseOutput.success("新增成功");
    }

    @RequestMapping(value = "/update.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput update(File file) {
        fileService.updateSelective(file);
        return BaseOutput.success("修改成功");
    }

    @RequestMapping(value = "/delete.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput delete(Long id) {
        fileService.delete(id);
        return BaseOutput.success("删除成功");
    }
}