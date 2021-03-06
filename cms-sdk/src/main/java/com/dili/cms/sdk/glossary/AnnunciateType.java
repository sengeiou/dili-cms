/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateTargetType.java created on 2021/1/20 17:03 by Henry.Huang
  */
package com.dili.cms.sdk.glossary;

/**
  * <pre>
  * Description
  * 信息通告类型
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/20 Henry.Huang Initial version.
  * </pre>
  */
public enum AnnunciateType {
    /**
     * 信息通告类型
     */
    PLATFFORM("平台公告", 1),
    WAIT_DO("待办事宜", 2),
    BUSINESS("业务消息", 3);

    private String name;
    private Integer value;

    private AnnunciateType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static AnnunciateType getAnnunciateType(Integer code) {
        switch (code) {
            case 1: return AnnunciateType.PLATFFORM;
            case 2: return AnnunciateType.WAIT_DO;
            case 3: return AnnunciateType.BUSINESS;
            default:  return null;
        }
    }

    public static String getName(Integer code) {
        switch (code) {
            case 1: return AnnunciateType.PLATFFORM.getName();
            case 2: return AnnunciateType.WAIT_DO.getName();
            case 3: return AnnunciateType.BUSINESS.getName();
            default:  return null;
        }
    }
    
    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
