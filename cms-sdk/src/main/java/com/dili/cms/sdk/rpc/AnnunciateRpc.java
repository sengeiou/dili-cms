/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateRpc.java created on 2021/1/22 16:37 by Henry.Huang
  */
package com.dili.cms.sdk.rpc;

import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.ss.domain.BaseOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
  * <pre>
  * Description
  * 信息通告rpc
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/22 Henry.Huang Initial version.
  * </pre>
  */
@FeignClient(name = "dili-cms", contextId = "annunciateRpc", url = "${annunciate.url:}")
public interface AnnunciateRpc {

    /**
     * 根据annunciate_id标记该消息已读
     * @param annunciateId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/readByAnnunciateId")
    BaseOutput readByAnnunciateId(@RequestBody Long annunciateId);

    /**
     * 根据用户id标记该用户所有消息已读
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/readByUserId")
    BaseOutput readByUserId(@RequestBody Long userId);

    /**
     * 根据annunciate_id标记该消息删除
     * @param annunciateId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/deleteByAnnunciateId")
    BaseOutput deleteByAnnunciateId(@RequestBody Long annunciateId);

    /**
     * 根据用户id标记该用户所有消息已删除
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/deleteByUserId")
    BaseOutput deleteByUserId(@RequestBody Long userId);

    /**
     * 根据信息通告项id标记该消息已读
     * @param annunciateItemId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/readByAnnunciateItemId")
    BaseOutput readByAnnunciateItemId(@RequestBody Long annunciateItemId);

    /**
     * 根据信息通告项id标记该消息为已删除
     * @param annunciateItemId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/deleteByAnnunciateItemId")
    BaseOutput deleteByAnnunciateItemId(@RequestBody Long annunciateItemId);

    /**
     * 根据用户id查询消息列表(不包括富文本消息内容，以节约带宽)
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<List<Annunciate>>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/getListByUserId")
    BaseOutput<List<AnnunciateVo>> getListByUserId(@RequestBody Long userId);

    /**
     * 根据annunciate_id查询富文本消息内容
     * @param id:
     * @return：com.dili.ss.domain.BaseOutput<java.land.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/api/annunciate/getContentById")
    BaseOutput<String> getContentById(@RequestBody Long id);

}
