/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateTarget.java created on 2021/1/21 14:44 by Henry.Huang
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
  * 通告目标
  *
  * @author Henry.Huang 
  * @since 1.0 
  *
  * Change History 
  * Date Modifier DESC. 
  * 2021/1/21 Henry.Huang Initial version. 
  * </pre> 
  */
@Table(name = "`annunciate_target`")
public interface AnnunciateTarget extends IBaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @FieldDef(label="主键")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getId();

    void setId(Long id);

    @Column(name = "`annunciate_id`")
    @FieldDef(label="通告id", maxLength = 4000)
    @EditMode(editor = FieldEditor.Text, required = false)
    String getAnnunciateId();

    void setAnnunciateId(String annunciateId);

    @Column(name = "`target_type`")
    @FieldDef(label="对象类型")
    @EditMode(editor = FieldEditor.Text, required = false)
    Integer getTargetType();

    void setTargetType(Integer targetType);

    @Column(name = "`target_range`")
    @FieldDef(label="对象范围, 0: 所有用户, 1:指定用户, 2:部门, 3:公司, 4: 司机, 5: 买家, 6: 经营户")
    @EditMode(editor = FieldEditor.Text, required = false)
    Integer getTargetRange();

    void setTargetRange(Integer targetRange);

    @Column(name = "`target_range_id`")
    @FieldDef(label="对象范围id，可能是岗位id、部门id、市场id，其它类型为空")
    @EditMode(editor = FieldEditor.Number, required = true)
    Long getTargetRangeId();

    void setTargetRangeId(Long targetRangeId);
}