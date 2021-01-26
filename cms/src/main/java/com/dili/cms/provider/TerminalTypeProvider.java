/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * TerminalTypeProvider created on 2021/1/26 16:21 by Ron.Peng
 */
package com.dili.cms.provider;

import com.dili.cms.sdk.glossary.ReadType;
import com.dili.cms.sdk.glossary.TerminalType;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class TerminalTypeProvider implements ValueProvider {
    private static final List<ValuePair<?>> BUFFER = new ArrayList<>();

    static {
        BUFFER.addAll(Stream.of(TerminalType.values())
                .map(e -> new ValuePairImpl<>(e.getName(), e.getValue().toString()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<ValuePair<?>> getLookupList(Object o, Map map, FieldMeta fieldMeta) {
        return BUFFER;
    }

    @Override
    public String getDisplayText(Object object, Map map, FieldMeta fieldMeta) {
        if (null == object) {
            return null;
        }
        ValuePair<?> valuePair = BUFFER.stream().filter(val -> object.toString().equals(val.getValue())).findFirst().orElseGet(null);
        if (null != valuePair) {
            return valuePair.getText();
        }
        return null;
    }
}
