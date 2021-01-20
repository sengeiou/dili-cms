/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileAuthItemMapper.java created on 2021/1/20 15:32 by Tab.Xie
 */
package com.dili.cms.dao;

import com.dili.cms.domain.FileAuthItem;
import com.dili.ss.base.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <pre>
 * Description
 * TODO 文件具体权限Mapper
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
public interface FileAuthItemMapper extends MyMapper<FileAuthItem> {
}