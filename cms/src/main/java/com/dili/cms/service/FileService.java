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
}