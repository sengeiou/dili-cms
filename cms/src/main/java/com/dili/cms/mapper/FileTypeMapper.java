/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeMapper.java created on 2021/1/20 15:33 by Tab.Xie
 */
package com.dili.cms.mapper;

import com.dili.cms.sdk.domain.IFileType;
import com.dili.ss.base.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

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
public interface FileTypeMapper extends MyMapper<IFileType> {

    /**
     * 根据id批量给节点文件的数量+1
     *
     * @param ids
     * @return
     */
    int updateNodeCountBatch(Set<Long> ids);
}