/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileItemServiceImpl created on 2021/1/27 17:41 by Tab.Xie
 */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.FileItemMapper;
import com.dili.cms.sdk.domain.IFileItem;
import com.dili.cms.service.FileItemService;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.dto.DTOUtils;
import org.springframework.stereotype.Service;

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
 * 2021/1/27  Tab.Xie  Initial version.
 * </pre>
 */
@Service
public class FileItemServiceImpl extends BaseServiceImpl<IFileItem, Long> implements FileItemService {
    public FileItemMapper getActualDao() {
        return (FileItemMapper) getDao();
    }

    @Override
    public List<IFileItem> listByFileId(Long fileId) {
        IFileItem example = DTOUtils.newInstance(IFileItem.class);
        example.setFileId(fileId);
        return listByExample(example);
    }
}