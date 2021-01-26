package com.dili.cms.sdk.dto;

import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.ss.domain.annotation.Operator;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateTargetDto created on 2021/1/26 20:11 by Ron.Peng
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
