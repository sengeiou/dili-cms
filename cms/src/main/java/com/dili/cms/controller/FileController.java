/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileController created on 2021/1/21 15:24 by Tab.Xie
 */
package com.dili.cms.controller;

import com.dili.cms.sdk.dto.IFileDto;
import com.dili.cms.service.impl.FileServiceImpl;
import com.dili.ss.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 2021/1/21  Tab.Xie  Initial version.
 * </pre>
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private FileServiceImpl fileService;


    /**
     * TODO 新增页面
     *
     * @return：java.lang.String
     * @author：Tab.Xie
     * @date：2021/1/21 16:36
     */
    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public String add() {
        return "file/add";
    }

    /**
     * TODO 新增
     *
     * @param fileDto:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Tab.Xie
     * @date：2021/1/21 15:25
     */
    @RequestMapping(value = "insert.action", method = {RequestMethod.POST})
    @ResponseBody
    public BaseOutput insert(@RequestBody IFileDto fileDto) {
        return fileService.create(fileDto);
    }
}