/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateVo created on 2021/1/20 16:17 by Henry.Huang
 */
package com.dili.cms.dto;

import com.dili.cms.domain.Annunciate;

/**
 * <pre> 
 * Description 
 * 信息通告VO
 *
 * @author Henry.Huang
 * @since 1.0
 *
 * Change History 
 * Date Modifier DESC. 
 * 2021/1/20 Henry.Huang Initial version. 
 * </pre> 
 */
public interface AnnunciateVo extends Annunciate {

    /**
      *发送对象
      * @return：java.lang.String
      * @author：Henry.Huang
      * @date：2021/1/20 16:49
      */
    String getTargetType();

    void setTargetType(String targetType);

    /**
      * 对象范围
      * @return：java.lang.String
      * @author：Henry.Huang
      * @date：2021/1/20 16:48
      */
    String getTargetRange();

    void setTargetRange(String targetRange);
}