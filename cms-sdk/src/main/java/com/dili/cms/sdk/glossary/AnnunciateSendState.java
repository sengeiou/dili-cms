/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateTargetType.java created on 2021/1/20 17:03 by Henry.Huang
  */
package com.dili.cms.sdk.glossary;

/**
  * <pre>
  * Description
  * 信息通告发送状态
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/20 Henry.Huang Initial version.
  * </pre>
  */
public enum AnnunciateSendState {
    /**
     * 信息通告发送状态
     */
    CREATED("未发布", 1),
    PUBLISH("已发布", 2),
    REVOKE("已撤发", 3),
    END("已结束", 4);

    private String name;
    private Integer value;

    private AnnunciateSendState(String name, Integer value) {
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
