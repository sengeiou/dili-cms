/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileService.java created on 2021/1/20 15:35 by Tab.Xie
 */
package com.dili.cms.service;


import com.dili.cms.sdk.domain.IFile;
import com.dili.cms.sdk.dto.IFileDto;
import com.dili.ss.base.BaseService;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;

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
public interface FileService extends BaseService<IFile, Long> {

    /**
     * 新增文件：更新文件类型数量和新增文件权限
     *
     * @param fileDto
     * @return
     */
    BaseOutput create(IFileDto fileDto);


    /**
     * 新编辑文件：更新文件类型数量和新增文件权限
     *
     * @param fileDto
     * @return
     */
    BaseOutput edit(IFileDto fileDto);


    /**
     * 根据id获取文件详细信息
     *
     * @param id
     * @return
     */
    IFileDto getDetailById(Long id);


    /**
     * 根据id删除文件
     *
     * @param ids
     * @return
     */
    BaseOutput deleteByIds(List<Long> ids);

    /**
     * TODO 文件列表查询
     *
     * @param iFileDto
     * @return：
     * @author：Ron.Peng
     * @date：2021/1/23 15:19
     */
    EasyuiPageOutput listPage(IFileDto iFileDto, Boolean useProvider) throws Exception;
}