/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateDto created on 2021/1/20 14:23 by Henry.Huang
 */
package com.dili.cms.dto;

import com.dili.cms.domain.Annunciate;
import java.time.LocalDateTime;

/**
 * <pre> 
 * Description 
 * 信息通告dto
 *
 * @author Henry.Huang
 * @since 1.0
 *
 * Change History 
 * Date Modifier DESC. 
 * 2021/1/20 Henry.Huang Initial version. 
 * </pre> 
 */
public interface AnnunciateDto extends Annunciate {

    /**
     * 通告创建时间范围
     * @return
     */
    LocalDateTime getCreateTimeStart();

    void setCreateTimeStart(LocalDateTime createTimeStart);

    LocalDateTime getCreateTimeEnd();

    void setCreateTimeEnd(LocalDateTime createTimeEnd);

    /**
     * 发送对象
     * @return
     */
    Integer getTargetType();

    void setTargetType(Integer targetType);
}