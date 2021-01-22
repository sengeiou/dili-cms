/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileType.java created on 2021/1/20 15:33 by Tab.Xie
 */
package com.dili.cms.sdk.domain;

import com.dili.ss.dto.IBaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description
 * TODO 文件类型表
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Table(name = "`file_type`")
public interface IFileType extends IBaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @FieldDef(label = "id")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getId();

    void setId(Long id);

    @Column(name = "`parent_id`")
    @FieldDef(label = "父节点id：0则是父节点")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getParentId();

    void setParentId(Long parentId);

    @Column(name = "`name`")
    @FieldDef(label = "类型名称", maxLength = 255)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getName();

    void setName(String name);

    @Column(name = "`node_file_count`")
    @FieldDef(label = "节点文件数量")
    @EditMode(editor = FieldEditor.Number, required = false)
    Integer getNodeFileCount();

    void setNodeFileCount(Integer nodeFileCount);

    @Column(name = "`remark`")
    @FieldDef(label = "备注", maxLength = 255)
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
    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    @Column(name = "`update_time`")
    @FieldDef(label = "修改时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    LocalDateTime getUpdateTime();

    void setUpdateTime(LocalDateTime updateTime);

    @Column(name = "`version`")
    @FieldDef(label = "版本号")
    @EditMode(editor = FieldEditor.Number, required = false)
    Integer getVersion();

    void setVersion(Integer version);
}