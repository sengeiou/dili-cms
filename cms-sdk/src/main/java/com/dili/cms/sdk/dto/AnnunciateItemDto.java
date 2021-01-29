/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateItemDto.java created on 2021/1/28 18:05 by Henry.Huang
  */
package com.dili.cms.sdk.dto;

import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.ss.domain.annotation.Operator;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
  * <pre> 
  * Description 
  * 信息通告项dto
  *
  * @author Henry.Huang 
  * @since 1.0 
  *
  * Change History 
  * Date Modifier DESC. 
  * 2021/1/28 Henry.Huang Initial version. 
  * </pre> 
  */
public interface AnnunciateItemDto extends AnnunciateItem {

    @Column(name = "`read_time`")
    @Operator(Operator.GREAT_EQUAL_THAN)
    LocalDateTime getReadTimeStart();

    void setReadTimeStart(LocalDateTime readTimeStart);

    @Column(name = "`read_time`")
    @Operator(Operator.LITTLE_EQUAL_THAN)
    LocalDateTime getReadTimeEnd();

    void setReadTimeEnd(LocalDateTime readTimeEnd);
}
