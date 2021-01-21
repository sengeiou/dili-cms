/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeServiceImpl.java created on 2021/1/20 15:34 by Tab.Xie
 */
package com.dili.cms.service.impl;

import com.dili.cms.dao.FileTypeMapper;
import com.dili.cms.domain.FileType;
import com.dili.cms.service.FileTypeService;
import com.dili.ss.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

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
@Service
public class FileTypeServiceImpl extends BaseServiceImpl<FileType, Long> implements FileTypeService {

    public FileTypeMapper getActualDao() {
        return (FileTypeMapper) getDao();
    }
}