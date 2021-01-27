/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileService.java created on 2021/1/20 15:35 by Tab.Xie
 */
package com.dili.cms.service;


import com.dili.cms.sdk.domain.IFileItem;
import com.dili.ss.base.BaseService;

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
public interface FileItemService extends BaseService<IFileItem, Long> {

    /**
     * 根据文件id查询文件下面的列表
     *
     * @param fileId
     * @return
     */
    List<IFileItem> listByFileId(Long fileId);
}
