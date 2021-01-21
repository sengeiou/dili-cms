/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * File.java created on 2021/1/20 15:33 by Tab.Xie
 */
package com.dili.cms.sdk.domain;

import com.dili.ss.dto.IBaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;

import javax.persistence.*;
import java.util.Date;

/**
 * <pre>
 * Description
 * TODO 文件表
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Table(name = "`file`")
public interface IFile extends IBaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @FieldDef(label = "id")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getId();

    void setId(Long id);

    @Column(name = "`file_name`")
    @FieldDef(label = "文件名称", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getFileName();

    void setFileName(String fileName);

    @Column(name = "`type_id`")
    @FieldDef(label = "文件类型id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getTypeId();

    void setTypeId(Long typeId);

    @Column(name = "`cover_img`")
    @FieldDef(label = "封面图片url", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getCoverImg();

    void setCoverImg(String coverImg);

    @Column(name = "`is_data_auth`")
    @FieldDef(label = "是否有数据权限限制")
    @EditMode(editor = FieldEditor.Text, required = false)
    Byte getIsDataAuth();

    void setIsDataAuth(Byte isDataAuth);

    @Column(name = "`is_download`")
    @FieldDef(label = "是否可以下载")
    @EditMode(editor = FieldEditor.Text, required = false)
    Byte getIsDownload();

    void setIsDownload(Byte isDownload);

    @Column(name = "`remark`")
    @FieldDef(label = "备注说明", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getRemark();

    void setRemark(String remark);

    @Column(name = "`firm_code`")
    @FieldDef(label = "归属市场编码", maxLength = 50)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getFirmCode();

    void setFirmCode(String firmCode);

    @Column(name = "`creator_id`")
    @FieldDef(label = "创建人id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getCreatorId();

    void setCreatorId(Long creatorId);

    @Column(name = "`create_time`")
    @FieldDef(label = "创建时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    Date getCreateTime();

    void setCreateTime(Date createTime);

    @Column(name = "`update_time`")
    @FieldDef(label = "修改时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    Date getUpdateTime();

    void setUpdateTime(Date updateTime);

    @Column(name = "`version`")
    @FieldDef(label = "版本号")
    @EditMode(editor = FieldEditor.Number, required = false)
    Integer getVersion();

    void setVersion(Integer version);
}