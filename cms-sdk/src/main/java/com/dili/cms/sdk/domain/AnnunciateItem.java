/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateItem.java created on 2021/1/21 14:44 by Henry.Huang
  */
package com.dili.cms.sdk.domain;

import com.dili.cms.sdk.validator.ConstantValidator;
import com.dili.ss.dto.IBaseDomain;
import com.dili.ss.metadata.FieldEditor;
import com.dili.ss.metadata.annotation.EditMode;
import com.dili.ss.metadata.annotation.FieldDef;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
  * <pre>
  * Description
  * 通告项，通告对应的用户id或客户id的一对一关系
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/21 Henry.Huang Initial version.
  * </pre>
  */
@Table(name = "`annunciate_item`")
public interface AnnunciateItem extends IBaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @FieldDef(label="主键")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getId();

    void setId(Long id);

    @Column(name = "`annunciate_id`")
    @FieldDef(label="通告id")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getAnnunciateId();

    void setAnnunciateId(Long annunciateId);

    @Column(name = "`target_id`")
    @FieldDef(label="目标id")
    @EditMode(editor = FieldEditor.Number, required = false)
    Long getTargetId();

    void setTargetId(Long targetId);

    @Column(name = "`target_name`")
    @FieldDef(label = "目标名称", maxLength = 20)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getTargetName();

    void setTargetName(String targetName);

    @Column(name = "`read_state`")
    @FieldDef(label="读取状态")
    @EditMode(editor = FieldEditor.Text, required = false)
    Integer getReadState();

    void setReadState(Integer readState);

    @Column(name = "`send_time`")
    @FieldDef(label="发送时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    LocalDateTime getSendTime();

    void setSendTime(LocalDateTime sendTime);

    @Column(name = "`read_time`")
    @FieldDef(label="读消息时间")
    @EditMode(editor = FieldEditor.Datetime, required = false)
    LocalDateTime getReadTime();

    void setReadTime(LocalDateTime readTime);

    @Column(name = "`terminal`")
    @FieldDef(label="阅读终端")
    @EditMode(editor = FieldEditor.Text, required = false)
    Integer getTerminal();

    void setTerminal(Integer terminal);

    @Column(name = "`create_time`")
    @FieldDef(label="创建时间")
    @EditMode(editor = FieldEditor.Datetime, required = true)
    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);
}