/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeService.java created on 2021/1/20 15:40 by Tab.Xie
 */
package com.dili.cms.service;


import com.dili.cms.sdk.domain.IFileType;
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
public interface FileTypeService extends BaseService<IFileType, Long> {
    /**
     * TODO 新增文件类型
     * @param iFileType:
     * @return：
     * @author：Ron.Peng
     * @date：2021/1/25 17:28
     */
    BaseOutput saveOrUpdateFileType(IFileType iFileType);
}