/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateService.java created on 2021/1/21 13:39 by Henry.Huang
  */
package com.dili.cms.service;


import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateQueryDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.ss.base.BaseService;
import com.dili.ss.domain.BaseOutput;
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

    /**
      * 根据 annunciateId更新通告已读数量
      * @param annunciateDto:
      * @return：int
      * @author：Henry.Huang
      * @date：2021/1/21 17:37
      */
    int updateReadCountById(AnnunciateDto annunciateDto);

    /**
     * 批量更新信息通告已读数量
     * @param annunciates:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Henry.Huang
     * @date：2021/1/22 9:33
     */
    BaseOutput bachUpdateReadCountById(List<Annunciate> annunciates);

    /**
     * 根据用户id查询消息列表(不包括富文本消息内容，以节约带宽) 分页
     * @param annunciateQueryDto:
     * @return：com.dili.ss.domain.PageOutput<List<Annunciate>>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    PageOutput<List<AnnunciateVo>> getListByUserId(AnnunciateQueryDto annunciateQueryDto);

    /**
      * 新增Annunciate
      * @param annunciateDto:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/22 17:27
      */
    BaseOutput insertAnnunciate(AnnunciateDto annunciateDto);

    /**
      * 信息通告修改
      * @param annunciateDto:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/23 15:46
      */
    BaseOutput updateAnnunciate(AnnunciateDto annunciateDto);

    /**
      * 信息通告删除
      * @param id:  
      * @return：com.dili.ss.domain.BaseOutput 
      * @author：Henry.Huang 
      * @date：2021/1/23 16:47
      */
    BaseOutput deleteAnnunciate(Long id);

    /**
     * 信息通告置顶
     * @param id:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Henry.Huang
     * @date：2021/1/23 16:47
     */
    BaseOutput stick(Long id);

    /**
     * 信息通告撤销
     * @param annunciate:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Henry.Huang
     * @date：2021/1/23 16:47
     */
    BaseOutput revoke(Annunciate annunciate);

    /**
      * 根据客户id查询置顶三条消息列表(不包括富文本消息内容，以节约带宽)
      * @param targetId:
      * @return：com.dili.ss.domain.BaseOutput<java.util.List<com.dili.cms.sdk.dto.AnnunciateVo>>
      * @author：Henry.Huang
      * @date：2021/1/27 16:50
      */
    BaseOutput<List<AnnunciateVo>> getStickListByTargetId(Long targetId);

    /**
      * 读，需要传4个值terminal id readState targetId
      * @param annunciateDto:
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/27 17:39
      */
    BaseOutput<Annunciate> readByAnnunciateDto(AnnunciateDto annunciateDto);

    /**
      * 扫描通告，每整点扫描一次，将开始发布的消息进行发布，将结束的消息进行结束
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/28 10:42
      */
    BaseOutput scanningAnnunciate();
}