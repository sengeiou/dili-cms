/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * Annunciate.java created on 2021/1/20 16:46 by Henry.Huang
  */
package com.dili.cms.domain;

import com.dili.ss.dto.IBaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
  * <pre>
  * Description
  * 信息通告
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/20 Henry.Huang Initial version.
  * </pre>
  */
@Table(name = "`annunciate`")
public interface Annunciate extends IBaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @FieldDef(label="主键")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getId();

    void setId(Long id);

    @Column(name = "`title`")
    @FieldDef(label="标题", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = true)
    String getTitle();

    void setTitle(String title);

    @Column(name = "`content`")
    @FieldDef(label="内容", maxLength = 4000)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getContent();

    void setContent(String content);

    @Column(name = "`send_state`")
    @FieldDef(label="发送状态")
    @EditMode(editor = FieldEditor.Text, required = true)
    Integer getSendState();

    void setSendState(Integer sendState);

    @Column(name = "`start_time`")
    @FieldDef(label="开始时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    LocalDateTime getStartTime();

    void setStartTime(LocalDateTime startTime);

    @Column(name = "`end_time`")
    @FieldDef(label="结束时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    LocalDateTime getEndTime();

    void setEndTime(LocalDateTime endTime);

    @Column(name = "`notes`")
    @FieldDef(label="备注", maxLength = 120)
    @EditMode(editor = FieldEditor.Text, required = true)
    String getNotes();

    void setNotes(String notes);

    @Column(name = "`creator_id`")
    @FieldDef(label="创建人id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getCreatorId();

    void setCreatorId(Long creatorId);

    @Column(name = "`create_time`")
    @FieldDef(label="创建时间")
    @EditMode(editor = FieldEditor.Datetime, required = true)
    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    @Column(name = "`modify_time`")
    @FieldDef(label="更新时间")
    @EditMode(editor = FieldEditor.Datetime, required = true)
    LocalDateTime getModifyTime();

    void setModifyTime(LocalDateTime modifyTime);

    @Column(name = "`modifier_id`")
    @FieldDef(label="更新人id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getModifierId();

    void setModifierId(Long modifierId);

    @Column(name = "`read_count`")
    @FieldDef(label="已读数, 需配置版本号更新")
    @EditMode(editor = FieldEditor.Number, required = false)
    Integer getReadCount();

    void setReadCount(Integer readCount);

    @Column(name = "`version`")
    @FieldDef(label="乐观锁版本号")
    @EditMode(editor = FieldEditor.Number, required = false)
    Integer getVersion();

    void setVersion(Integer version);
}