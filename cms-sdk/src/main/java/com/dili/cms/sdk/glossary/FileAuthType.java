/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileAuthType created on 2021/1/25 11:20 by Tab.Xie
 */
package com.dili.cms.sdk.glossary;

/**
 * <pre>
 * Description
 * TODO 文件权限类型枚举
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/25  Tab.Xie  Initial version.
 * </pre>
 */
public enum FileAuthType {
    /**
     * 没有权限
     */
    NOT_AUTH("没有权限", 0),
    /**
     * 部门
     */
    DEPARTMENT("部门", 10),
    /**
     * 市场
     */
    MARKET("市场", 20),
    ;

    private String name;
    private Integer value;

    private FileAuthType(String name, Integer value) {
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