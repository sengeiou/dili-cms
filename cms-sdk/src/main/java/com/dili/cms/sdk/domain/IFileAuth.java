/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileAuth.java created on 2021/1/20 15:33 by Tab.Xie
 */
package com.dili.cms.sdk.domain;

import com.dili.ss.dto.IBaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;

import javax.persistence.*;

/**
 * <pre>
 * Description
 * TODO 文件权限表
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Table(name = "`file_auth`")
public interface IFileAuth extends IBaseDomain {
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

    @Column(name = "`auth_value`")
    @FieldDef(label = "权限id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getAuthValue();

    void setAuthValue(Long authValue);

    @Column(name = "`auth_text`")
    @FieldDef(label = "权限的文本值")
    @EditMode(editor = FieldEditor.Number, required = false)
    String getAuthText();

    void setAuthText(String authText);

    @Column(name = "`auth_type`")
    @FieldDef(label = "权限类型(部门，市场)")
    @EditMode(editor = FieldEditor.Text, required = false)
    Integer getAuthType();

    void setAuthType(Integer authType);
}