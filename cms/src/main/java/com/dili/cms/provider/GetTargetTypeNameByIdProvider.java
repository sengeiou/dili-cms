/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * GetTargetNameByIdProvider created on 2021/1/26 19:23 by Ron.Peng
 */
package com.dili.cms.provider;

import com.dili.cms.sdk.domain.AnnunciateTarget;
import com.dili.cms.sdk.domain.IFileType;
import com.dili.cms.sdk.glossary.AnnunciateTargetType;
import com.dili.cms.service.AnnunciateTargetService;
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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <pre>
 * Description
 * TODO file description here
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
public class GetTargetTypeNameByIdProvider implements ValueProvider {
    @Autowired
    private AnnunciateTargetService annunciateTargetService;

    @Override
    public List<ValuePair<?>> getLookupList(Object val, Map metaMap, FieldMeta fieldMeta) {
        return null;
    }

    @Override
    public String getDisplayText(Object object, Map metaMap, FieldMeta fieldMeta) {
        if (null == object) {
            return null;
        }
        AnnunciateTarget annunciateTarget = annunciateTargetService.get(Long.valueOf(object.toString()));
        return AnnunciateTargetType.getNameByValue(annunciateTarget.getTargetType()).orElseGet(null).getName();
    }
}
