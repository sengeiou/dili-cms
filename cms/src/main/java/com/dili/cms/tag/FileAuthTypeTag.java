/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileAuthTypeTag created on 2021/1/27 10:37 by Tab.Xie
 */
package com.dili.cms.tag;

import com.dili.cms.sdk.glossary.FileAuthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * Description
 * TODO 文件权限类型tag
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/27  Tab.Xie  Initial version.
 * </pre>
 */
@Component
public class FileAuthTypeTag extends BaseTag {
    private static final Logger log = LoggerFactory.getLogger(FileAuthTypeTag.class);

    @Override
    public void render() {
        Map<String, Object> argsMap = (Map) this.args[1];
        Object value = argsMap.get(DEFAULT_VAlUE_FIELD);
        if (Objects.nonNull(value)) {
            try {
                ctx.byteWriter.writeString(FileAuthType.getByValue((Integer) value).getName());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}