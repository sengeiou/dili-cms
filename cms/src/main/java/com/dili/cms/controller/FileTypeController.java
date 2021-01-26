/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeController.java created on 2021/1/20 15:32 by Tab.Xie
 */
package com.dili.cms.controller;

import com.dili.cms.sdk.domain.IFileType;
import com.dili.cms.sdk.dto.IFileTypeDto;
import com.dili.cms.service.FileTypeService;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.exception.AppException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    private FileTypeService fileTypeService;
    /**
     * TODO 树形菜单查询
     *
     * @param iFileTypeDto:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Ron.Peng
     * @date：2021/1/23 15:24
     */
    @RequestMapping(value = "/getAllFileToTree.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput getAllFileToTree(IFileTypeDto iFileTypeDto) {
        try {
            List<IFileTypeDto> iFileTypeDtoShowList = new ArrayList<>();
            List<IFileType> iFileTypes = fileTypeService.listByExample(iFileTypeDto);
            iFileTypes.forEach(f ->{
                IFileTypeDto iFileTypeDtoShow= DTOUtils.newDTO(IFileTypeDto.class);
                BeanUtils.copyProperties(f, iFileTypeDtoShow);
                iFileTypeDtoShow.setTreeShow(iFileTypeDtoShow.getName()+"("+iFileTypeDtoShow.getNodeFileCount()+")");
                iFileTypeDtoShowList.add(iFileTypeDtoShow);
            });
            return BaseOutput.successData(iFileTypeDtoShowList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * TODO 新增文件类型
     * @param iFileType:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Ron.Peng
     * @date：2021/1/25 17:24
     */
    @RequestMapping(value = "/saveOrUpdateFileType.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput saveOrUpdateFileType(@RequestBody IFileType iFileType) {
        try {
            return this.fileTypeService.saveOrUpdateFileType(iFileType);
        } catch (AppException e) {
            logger.error(e.getMessage(), e);
            return BaseOutput.failure(e.getMessage());
        }
    }
}