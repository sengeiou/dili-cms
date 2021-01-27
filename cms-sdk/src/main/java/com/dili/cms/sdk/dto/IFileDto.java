/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * IFileDto created on 2021/1/21 15:26 by Tab.Xie
 */
package com.dili.cms.sdk.dto;

import com.dili.cms.sdk.domain.IFile;
import com.dili.cms.sdk.domain.IFileAuth;
import com.dili.cms.sdk.domain.IFileItem;
import com.dili.cms.sdk.validator.ConstantValidator;

import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
    List<IFileAuth> getFileAuthList();

    void setFileAuthList(List<IFileAuth> fileAuthList);

    @Transient
    @Size(min = 1, message = "文件不能为空", groups = {ConstantValidator.Insert.class, ConstantValidator.Update.class})
    List<IFileItem> getFileItemList();

    void setFileItemList(List<IFileItem> fileItemList);

    @Transient
    String getLikeFileName();

    void setLikeFileName(String likeFileName);

    @Transient
    Long getDepartmentId();

    void setDepartmentId(Long departmentId);

    @Transient
    Long getPersonId();

    void setPersonId(Long personId);

    @Transient
    LocalDateTime getCreateTimeStart();

    void setCreateTimeStart(LocalDateTime createTimeStart);

    @Transient
    LocalDateTime getCreateTimeEnd();

    void setCreateTimeEnd(LocalDateTime createTimeEnd);
}