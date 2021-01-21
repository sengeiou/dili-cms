/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * IFileDto created on 2021/1/21 15:26 by Tab.Xie
 */
package com.dili.cms.sdk.dto;

import com.dili.cms.sdk.domain.IFile;
import com.dili.cms.sdk.domain.IFileAuth;
import com.dili.cms.sdk.domain.IFileItem;

import javax.persistence.Transient;
import java.util.List;

/**
 * <pre>
 * Description
 * TODO 文件Dto
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/21  Tab.Xie  Initial version.
 * </pre>
 */
public interface IFileDto extends IFile {

    @Transient
    List<IFileAuth> getAuthList();

    void setAuthList(List<IFileAuth> authList);

    @Transient
    List<IFileItem> getFileItemList();

    void setFileItemList(List<IFileItem> fileItemList);
}