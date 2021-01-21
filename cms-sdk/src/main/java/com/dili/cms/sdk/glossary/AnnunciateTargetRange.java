/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateTargetRange.java created on 2021/1/20 17:05 by Henry.Huang
  */
package com.dili.cms.sdk.glossary;

/**
  * <pre>
  * Description
  * 信息通告发送范围
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/20 Henry.Huang Initial version.
  * </pre>
  */
public enum AnnunciateTargetRange {

    /**
     * 信息通告对象
     */
    ALL_USER("所有用户", 1),
    APPOINT_USER("指定用户", 2),
    DEPARTMENT("部门", 3),
    COMPANY("公司", 4),
    DRIVER("司机", 5),
    BUYER("买家", 6),
    SELLER  ("卖家", 7);

    private String name;
    private Integer value;

    private AnnunciateTargetRange(String name, Integer value) {
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
