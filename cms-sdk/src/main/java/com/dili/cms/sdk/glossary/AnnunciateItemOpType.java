/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateTargetType.java created on 2021/1/20 17:03 by Henry.Huang
  */
package com.dili.cms.sdk.glossary;

/**
  * <pre>
  * Description
  * 操作通告项类型
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/20 Henry.Huang Initial version.
  * </pre>
  */
public enum AnnunciateItemOpType {
    /**
     * 操作通告项类型
     */
    OP_READ("改为已读", 1),
    OP_DEL("改为已删除", 2);

    private String name;
    private Integer value;

    private AnnunciateItemOpType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
