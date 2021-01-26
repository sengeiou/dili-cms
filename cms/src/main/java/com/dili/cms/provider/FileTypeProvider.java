/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeProvide created on 2021/1/26 11:09 by Ron.Peng
 */
package com.dili.cms.provider;

import com.dili.cms.sdk.domain.IFileType;
import com.dili.cms.service.FileTypeService;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * Description
 * TODO 文件类型Provider
 *
 * @author Ron.Peng
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/26  Ron.Peng  Initial version.
 * </pre>
 */
@Component
@Scope("prototype")
public class FileTypeProvider implements ValueProvider {

    @Autowired
    private FileTypeService fileTypeService;

    @Override
    public List<ValuePair<?>> getLookupList(Object val, Map metaMap, FieldMeta fieldMeta) {
        List<IFileType> list = fileTypeService.listByExample(DTOUtils.newInstance(IFileType.class));
        List<ValuePair<?>> resultList = list.stream().map(f -> {
            return (ValuePair<?>) new ValuePairImpl(f.getName(), f.getId());
        }).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public String getDisplayText(Object val, Map metaMap, FieldMeta fieldMeta) {
        return null;
    }
}