/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateTargetType.java created on 2021/1/20 17:03 by Henry.Huang
  */
package com.dili.cms.sdk.glossary;

/**
  * <pre>
  * Description
  * 信息通告发布类型
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/20 Henry.Huang Initial version.
  * </pre>
  */
public enum AnnunciatePublishType {
    /**
     * 信息通告对象
     */
    SYSTEM_USER("立即发布", 1),
    CUSTOMER("定时发布", 2);

    private String name;
    private Integer value;

    private AnnunciatePublishType(String name, Integer value) {
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
