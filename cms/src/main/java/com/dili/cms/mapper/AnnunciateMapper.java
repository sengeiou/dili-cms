/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateMapper.java created on 2021/1/21 9:27 by Henry.Huang
  */
package com.dili.cms.mapper;


import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.ss.base.MyMapper;

import java.util.List;

/**
  * <pre>
  * Description
  * 信息通告mapper
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/21 Henry.Huang Initial version.
  * </pre>
  */
public interface AnnunciateMapper extends MyMapper<Annunciate> {

    /**
      * 信息通告分页
      * @param annunciateDto:
      * @return：java.util.List<com.dili.cms.dto.AnnunciateVo>
      * @author：Henry.Huang
      * @date：2021/1/21 9:30
      */
    List<AnnunciateVo> listByQueryParams(AnnunciateDto annunciateDto);

}