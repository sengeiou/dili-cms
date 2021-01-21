/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateItemServiceImpl.java created on 2021/1/21 16:01 by Henry.Huang 
  */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.AnnunciateItemMapper;
import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.cms.service.AnnunciateItemService;
import com.dili.ss.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
  * <pre> 
  * Description 
  * TODO file description here 
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
public class AnnunciateItemServiceImpl extends BaseServiceImpl<AnnunciateItem, Long> implements AnnunciateItemService {

    public AnnunciateItemMapper getActualDao() {
        return (AnnunciateItemMapper)getDao();
    }
}