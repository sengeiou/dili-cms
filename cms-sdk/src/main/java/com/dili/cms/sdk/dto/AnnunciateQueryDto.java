/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateQueryDto created on 2021/1/29 10:41 by Henry.Huang
 */
package com.dili.cms.sdk.dto;

import com.dili.ss.dto.IBaseDomain;

/**
 * <pre> 
 * Description 
 * 用于简单查询参数
 *
 * @author Henry.Huang
 * @since 1.0
 *
 * Change History 
 * Date Modifier DESC. 
 * 2021/1/29 Henry.Huang Initial version. 
 * </pre> 
 */
public interface AnnunciateQueryDto extends IBaseDomain {

    /**
     * 通告项客户或用户id
     * @return
     */
    Long getTargetId();

    void setTargetId(Long targetId);
}