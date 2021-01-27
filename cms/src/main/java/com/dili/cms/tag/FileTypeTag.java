/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeTag created on 2021/1/27 11:49 by Tab.Xie
 */
package com.dili.cms.tag;

import com.dili.cms.mapper.FileTypeMapper;
import com.dili.cms.sdk.domain.IFileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * Description
 * TODO 文件类型tag
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
public class FileTypeTag extends BaseTag {
    private static final Logger log = LoggerFactory.getLogger(FileAuthTypeTag.class);

    @Autowired
    private FileTypeMapper fileTypeMapper;

    @Override
    public void render() {
        Map<String, Object> argsMap = (Map) this.args[1];
        Object value = argsMap.get(DEFAULT_VAlUE_FIELD);
        if (Objects.nonNull(value)) {
            try {
                IFileType fileType = fileTypeMapper.selectByPrimaryKey(value);
                if (null != fileType) {
                    ctx.byteWriter.writeString(fileType.getName());
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}