/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateTargetType.java created on 2021/1/20 17:03 by Henry.Huang
 */
package com.dili.cms.sdk.glossary;

import java.util.Arrays;
import java.util.Optional;

/**
 * <pre>
 * Description
 * 信息通告对象
 *
 * @author Henry.Huang
 * @since 1.0
 *
 * Change History
 * Date Modifier DESC.
 * 2021/1/20 Henry.Huang Initial version.
 * </pre>
 */
public enum AnnunciateTargetType {
    /**
     * 信息通告对象
     */
    SYSTEM_USER("系统用户", 1),
    CUSTOMER("客户", 2);

    private String name;
    private Integer value;

    private AnnunciateTargetType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public static Optional<AnnunciateTargetType> getNameByValue(Integer value) {
        return Arrays.stream(AnnunciateTargetType.values()).filter(e -> e.getValue().equals(value)).findFirst();
    }
}
