/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileItem.java created on 2021/1/20 15:33 by Tab.Xie
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
 * TODO 文件信息表
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Table(name = "`file_item`")
public interface IFileItem extends IBaseDomain {
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

    @Column(name = "`file_name`")
    @FieldDef(label = "文件名称", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getFileName();

    void setFileName(String fileName);

    @Column(name = "`file_url`")
    @FieldDef(label = "文件地址", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getFileUrl();

    void setFileUrl(String fileUrl);

    @Column(name = "`file_type`")
    @FieldDef(label = "文件类型后缀", maxLength = 35)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getFileType();

    void setFileType(String fileType);

    @Column(name = "`file_size`")
    @FieldDef(label = "文件大小")
    @EditMode(editor = FieldEditor.Number, required = false)
    Integer getFileSize();

    void setFileSize(Integer fileSize);
}