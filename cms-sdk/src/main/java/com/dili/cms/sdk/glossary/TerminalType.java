package com.dili.cms.sdk.glossary;

/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * TerminalType created on 2021/1/26 16:17 by Ron.Peng
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
