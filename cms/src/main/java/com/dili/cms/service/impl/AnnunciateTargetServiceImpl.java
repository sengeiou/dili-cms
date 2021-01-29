/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateTargetServiceImpl.java created on 2021/1/21 16:01 by Henry.Huang
  */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.AnnunciateTargetMapper;
import com.dili.cms.sdk.domain.AnnunciateTarget;
import com.dili.cms.service.AnnunciateTargetService;
import com.dili.ss.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
  * <pre>
  * Description
  * 信息通告目标mapper
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/21 Henry.Huang Initial version.
  * </pre>
  */
@Service
public class AnnunciateTargetServiceImpl extends BaseServiceImpl<AnnunciateTarget, Long> implements AnnunciateTargetService {

    public AnnunciateTargetMapper getActualDao() {
        return (AnnunciateTargetMapper)getDao();
    }
}