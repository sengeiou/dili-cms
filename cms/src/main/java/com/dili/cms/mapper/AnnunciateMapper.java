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

    /**
      * 根据id更新已读数量
      * @param annunciateDto:
      * @return：int
      * @author：Henry.Huang
      * @date：2021/1/21 17:38
      */
    int updateReadCountById(AnnunciateDto annunciateDto);

    /**
     * 批量更新信息通告已读数量
     * @param annunciates:
     * @author：Henry.Huang
     * @date：2021/1/22 9:33
     */
    void bachUpdateReadCountById(List<Annunciate> annunciates);

    /**
      * 根据用户id获取信息列表，过滤掉已删除的
      * @param annunciateDto:
      * @return：java.util.List<com.dili.cms.sdk.domain.Annunciate>
      * @author：Henry.Huang
      * @date：2021/1/22 15:58
      */
    List<AnnunciateVo> getListByUserId(AnnunciateDto annunciateDto);

    /**
      * 将条件内的所有消息改为非置顶 条件：发布时间未结束 发布状态为已发布 市场id
      * @param noStickAnnunciate:
      * @return：void
      * @author：Henry.Huang
      * @date：2021/1/23 17:45
      */
    void updateAllNoStick(Annunciate noStickAnnunciate);

    /**
      * 根据客户id查询置顶三条消息列表(不包括富文本消息内容，以节约带宽)
      * @param targetId:  
      * @return：java.util.List<com.dili.cms.sdk.dto.AnnunciateVo> 
      * @author：Henry.Huang 
      * @date：2021/1/27 16:54 
      */
    List<AnnunciateVo> getStickListByTargetId(Long targetId);
}