/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateSendStateProvider.java created on 2021/1/25 13:48 by Henry.Huang
  */
package com.dili.cms.provider;

import com.dili.cms.sdk.glossary.AnnunciateSendState;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
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
  * 发送状态提供者
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/25 Henry.Huang Initial version.
  * </pre>
  */
@Component
@Scope("prototype")
public class AnnunciateSendStateProvider implements ValueProvider {
    private static final List<ValuePair<?>> BUFFER = new ArrayList<>();

    static {
        BUFFER.addAll(Stream.of(AnnunciateSendState.values())
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