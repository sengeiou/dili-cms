/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateService.java created on 2021/1/21 13:39 by Henry.Huang
  */
package com.dili.cms.service;


import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.ss.base.BaseService;
import com.dili.ss.domain.PageOutput;

import java.util.List;

/**
  * <pre>
  * Description
  * 信息通告service
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/21 Henry.Huang Initial version.
  * </pre>
  */
public interface AnnunciateService extends BaseService<Annunciate, Long> {

    /**
      * 信息通告分页
      * @param annunciateDto:
      * @return：com.dili.ss.domain.BaseOutput<java.util.List<com.dili.cms.dto.AnnunciateVo>>
      * @author：Henry.Huang
      * @date：2021/1/21 9:32
      */
    PageOutput<List<AnnunciateVo>> listByQueryParams(AnnunciateDto annunciateDto);
}