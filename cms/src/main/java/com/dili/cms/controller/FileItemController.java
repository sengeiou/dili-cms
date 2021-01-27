/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileItemController created on 2021/1/27 17:45 by Tab.Xie
 */
package com.dili.cms.controller;

import com.dili.cms.service.impl.FileItemServiceImpl;
import com.dili.ss.domain.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 2021/1/27  Tab.Xie  Initial version.
 * </pre>
 */
@Controller
@RequestMapping("/fileItem")
public class FileItemController {

    @Autowired
    private FileItemServiceImpl fileItemService;


    /**
     * TODO 根据文件id查询文件下面的所有文件
     *
     * @param fileId:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Tab.Xie
     * @date：2021/1/27 17:47
     */
    @RequestMapping(value = "listByFileId.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput listByFileId(Long fileId) {
        return BaseOutput.success().setData(fileItemService.listByFileId(fileId));
    }
}