/**
  * Copyright (C) DiliGroup. All Rights Reserved. 
  *
  * AnnunciateItemService.java created on 2021/1/21 16:01 by Henry.Huang 
  */
package com.dili.cms.service;

import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.ss.base.BaseService;
import com.dili.ss.domain.BaseOutput;

import java.util.List;

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
public interface AnnunciateItemService extends BaseService<AnnunciateItem, Long> {

    /**
      * 根据annunciate_id标记该消息已读
      * @param annunciateId:
      * @param updateType:更新方式 1更新为已读 2更新为已删除
      * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
      * @author：Henry.Huang
      * @date：2021/1/21 16:43
      */
    BaseOutput readByAnnunciateId(Long annunciateId,Integer updateType);

    /**
      * 根据用户id标记该用户所有消息已读
      * @param userId:
      * @param updateType:更新方式 1更新为已读 2更新为已删除
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/22 8:51
      */
    BaseOutput readByUserId(Long userId,Integer updateType);

    /**
      * 根据信息通告项id标记该消息已读
      * @param annunciateItemId:
      * @param updateType:  更新方式 1更新为已读 2更新为已删除
      * @return：com.dili.ss.domain.BaseOutput
      * @author：Henry.Huang
      * @date：2021/1/22 11:39
      */
    BaseOutput readByAnnunciateItemId(Long annunciateItemId, Integer updateType);
}