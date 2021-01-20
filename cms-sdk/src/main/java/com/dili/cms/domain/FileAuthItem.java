/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileAuthItem.java created on 2021/1/20 15:33 by Tab.Xie
 */
package com.dili.cms.domain;

import com.dili.ss.dto.IBaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;

import javax.persistence.*;

/**
 * <pre>
 * Description
 * TODO 文件权限子表
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Table(name = "`file_auth_item`")
public interface FileAuthItem extends IBaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @FieldDef(label = "id")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getId();

    void setId(Long id);

    @Column(name = "`file_id`")
    @FieldDef(label = "文件id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getFileId();

    void setFileId(Long fileId);

    @Column(name = "`auth_id`")
    @FieldDef(label = "权限id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getAuthId();

    void setAuthId(Long authId);

    @Column(name = "`user_id`")
    @FieldDef(label = "用户id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getUserId();

    void setUserId(Long userId);
}