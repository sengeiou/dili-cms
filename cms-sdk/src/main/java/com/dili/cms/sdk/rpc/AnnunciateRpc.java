/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateRpc.java created on 2021/1/22 16:37 by Henry.Huang
  */
package com.dili.cms.sdk.rpc;

import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.ss.domain.BaseOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(value = "/api/annunciate/readByAnnunciateId", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput readByAnnunciateId(@RequestBody Long annunciateId);

    /**
     * 根据用户id标记该用户所有消息已读
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @RequestMapping(value = "/api/annunciate/readByUserId", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput readByUserId(@RequestBody Long userId);

    /**
     * 根据annunciate_id标记该消息删除
     * @param annunciateId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @RequestMapping(value = "/api/annunciate/deleteByAnnunciateId", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput deleteByAnnunciateId(@RequestBody Long annunciateId);

    /**
     * 根据用户id标记该用户所有消息已删除
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @RequestMapping(value = "/api/annunciate/deleteByUserId", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput deleteByUserId(@RequestBody Long userId);

    /**
     * 根据信息通告项id标记该消息已读
     * @param annunciateItemId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @RequestMapping(value = "/api/annunciate/readByAnnunciateItemId", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput readByAnnunciateItemId(@RequestBody Long annunciateItemId);

    /**
     * 根据信息通告项id标记该消息为已删除
     * @param annunciateItemId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @RequestMapping(value = "/api/annunciate/deleteByAnnunciateItemId", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput deleteByAnnunciateItemId(@RequestBody Long annunciateItemId);

    /**
     * 根据用户id查询消息列表(不包括富文本消息内容，以节约带宽)
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<List<Annunciate>>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @RequestMapping(value = "/api/annunciate/getListByUserId", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput<List<AnnunciateVo>> getListByUserId(@RequestBody Long userId);

    /**
     * 根据annunciate_id查询富文本消息内容
     * @param id:
     * @return：com.dili.ss.domain.BaseOutput<java.land.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @RequestMapping(value = "/api/annunciate/getContentById", method = {RequestMethod.POST,RequestMethod.GET})
    public BaseOutput<String> getContentById(@RequestBody Long id);

}
