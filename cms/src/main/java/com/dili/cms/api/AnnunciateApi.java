/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateApi created on 2021/1/21 16:28 by Henry.Huang
 */
package com.dili.cms.api;

import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.cms.sdk.glossary.AnnunciateItemOpType;
import com.dili.cms.service.AnnunciateItemService;
import com.dili.cms.service.AnnunciateService;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre> 
 * Description 
 * 信息通告API
 *
 * @author Henry.Huang
 * @since 1.0
 *
 * Change History 
 * Date Modifier DESC. 
 * 2021/1/21 Henry.Huang Initial version. 
 * </pre> 
 */
@RestController
@RequestMapping("/api/annunciate")
public class AnnunciateApi {

    @Autowired
    AnnunciateItemService annunciateItemService;

    @Autowired
    AnnunciateService annunciateService;

    /**
      * 根据annunciate_id标记该消息已读
      * @param annunciateId:
      * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
      * @author：Henry.Huang
      * @date：2021/1/21 16:38
      */
    @PostMapping(value = "/readByAnnunciateId")
    public BaseOutput readByAnnunciateId(@RequestBody Long annunciateId) {
        try{
            return annunciateItemService.readByAnnunciateId(annunciateId, AnnunciateItemOpType.OP_READ.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据用户id标记该用户所有消息已读
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/readByUserId")
    public BaseOutput readByUserId(@RequestBody Long userId) {
        try{
            return annunciateItemService.readByUserId(userId,AnnunciateItemOpType.OP_READ.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据annunciate_id标记该消息删除
     * @param annunciateId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/deleteByAnnunciateId")
    public BaseOutput deleteByAnnunciateId(@RequestBody Long annunciateId) {
        try{
            return annunciateItemService.readByAnnunciateId(annunciateId,AnnunciateItemOpType.OP_DEL.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据用户id标记该用户所有消息已删除
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/deleteByUserId")
    public BaseOutput deleteByUserId(@RequestBody Long userId) {
        try{
            return annunciateItemService.readByUserId(userId,AnnunciateItemOpType.OP_DEL.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据信息通告项id标记该消息已读
     * @param annunciateItemId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/readByAnnunciateItemId")
    public BaseOutput readByAnnunciateItemId(@RequestBody Long annunciateItemId) {
        try{
            return annunciateItemService.readByAnnunciateItemId(annunciateItemId, AnnunciateItemOpType.OP_READ.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据信息通告项id标记该消息为已删除
     * @param annunciateItemId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/deleteByAnnunciateItemId")
    public BaseOutput deleteByAnnunciateItemId(@RequestBody Long annunciateItemId) {
        try{
            return annunciateItemService.readByAnnunciateItemId(annunciateItemId, AnnunciateItemOpType.OP_DEL.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据用户id查询消息列表(不包括富文本消息内容，以节约带宽)
     * @param userId:
     * @return：com.dili.ss.domain.BaseOutput<List<Annunciate>>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/getListByUserId")
    public BaseOutput<List<AnnunciateVo>> getListByUserId(@RequestBody Long userId) {
        try{
            return annunciateService.getListByUserId(userId);
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据annunciate_id查询富文本消息内容
     * @param id:
     * @return：com.dili.ss.domain.BaseOutput<java.land.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/getContentById")
    public BaseOutput<String> getContentById(@RequestBody Long id) {
        try{
            Annunciate annunciate=annunciateService.get(id);
            if(annunciate==null){
                return BaseOutput.failure("查询失败!");
            }
            return BaseOutput.successData(annunciate.getContent());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }
}