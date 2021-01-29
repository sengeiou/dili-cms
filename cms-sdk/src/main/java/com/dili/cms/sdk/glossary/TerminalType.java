/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * TerminalType.java created on 2021/1/28 18:07 by Henry.Huang 
  */
package com.dili.cms.sdk.glossary;

/**
  * <pre>
  * Description
  * 终端类型
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/28 Henry.Huang Initial version.
  * </pre>
  */
public enum TerminalType {
    /**
     * 信息通告项状态
     */
    WEB("WEB", 1),
    MINI_PROGRAMS("小程序", 2),
    ANDROID("安卓", 3),
    IOS("IOS", 4);

    private String name;
    private Integer value;

    private TerminalType(String name, Integer value) {
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
