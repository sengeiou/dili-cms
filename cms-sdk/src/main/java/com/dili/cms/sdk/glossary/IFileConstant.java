/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * IFileConstant.java created on 2021/1/21 16:49 by Tab.Xie
 */
package com.dili.cms.sdk.glossary;

/**
 * <pre>
 * Description
 * TODO 文件的一些常量
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/21  Tab.Xie  Initial version.
 * </pre>
 */
public enum IFileConstant {
    /**
     * 文件有权限
     */
    AUTH("有权限", 1),
    /**
     * 文件没有权限
     */
    NO_AUTH("没有权限", 0),
    /**
     * 文件可以下载
     */
    DOWNLOAD("可以下载", 1),
    /**
     * 文件不可以下载
     */
    NO_DOWNLOAD("不可以下载", 0),
    ;

    private String name;
    private Integer value;

    private IFileConstant(String name, Integer value) {
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
