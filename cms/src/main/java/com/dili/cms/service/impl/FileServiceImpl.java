/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileServiceImpl.java created on 2021/1/20 15:34 by Tab.Xie
 */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.FileMapper;
import com.dili.cms.sdk.domain.File;
import com.dili.cms.service.FileService;
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
public class FileServiceImpl extends BaseServiceImpl<File, Long> implements FileService {

    public FileMapper getActualDao() {
        return (FileMapper) getDao();
    }
}