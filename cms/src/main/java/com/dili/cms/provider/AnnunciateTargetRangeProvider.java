/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * PayStatusProvider.java created on 2021/1/20 17:21 by Henry.Huang
  */
package com.dili.cms.provider;

import com.dili.cms.sdk.glossary.AnnunciateTargetRange;
import com.dili.ss.metadata.FieldMeta;
import com.dili.ss.metadata.ValuePair;
import com.dili.ss.metadata.ValuePairImpl;
import com.dili.ss.metadata.ValueProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
  * <pre>
  * Description
  * 信息通告发送范围提供者
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/20 Henry.Huang Initial version.
  * </pre>
  */
@Component
@Scope("prototype")
public class AnnunciateTargetRangeProvider implements ValueProvider {

    private static final List<ValuePair<?>> BUFFER = new ArrayList<>();

    static {
        BUFFER.addAll(Stream.of(AnnunciateTargetRange.values())
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
        StringBuffer text=new StringBuffer();
        String splitStr=",";
        String objectStr=object.toString();
        String[] objectArr=objectStr.split(splitStr);
        if(objectArr==null || objectArr.length==0){
            return null;
        }
        Stream<String> stream = Arrays.stream(objectArr);
        List<String> list = stream.distinct().collect(Collectors.toList());
        for (String objectValue:list) {
            ValuePair<?> valuePair = BUFFER.stream().filter(val -> objectValue.equals(val.getValue())).findFirst().orElseGet(null);
            if(null != valuePair && !"".equals(valuePair.getText())){
                text.append(valuePair.getText()).append(splitStr);
            }
        }
        if(text.length()==0){
            return text.toString();
        }
        return text.substring(0,text.length()-1);
    }
}
