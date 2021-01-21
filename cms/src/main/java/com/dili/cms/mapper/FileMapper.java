/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileMapper.java created on 2021/1/20 15:32 by Tab.Xie
 */
package com.dili.cms.mapper;

import com.dili.cms.sdk.domain.IFile;
import com.dili.ss.base.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * Description
 * TODO 文件类型Mapper
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Mapper
public interface FileMapper extends MyMapper<IFile> {
}