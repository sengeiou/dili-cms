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
     * @param targetId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/readByTargetId")
    public BaseOutput readByTargetId(@RequestBody Long targetId) {
        try{
            return annunciateItemService.readByUserId(targetId,AnnunciateItemOpType.OP_READ.getValue());
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
     * @param targetId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/deleteByTargetId")
    public BaseOutput deleteByTargetId(@RequestBody Long targetId) {
        try{
            return annunciateItemService.readByUserId(targetId,AnnunciateItemOpType.OP_DEL.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据信息通告id和targetId标记该消息已读
     * @param annunciateId:
     * @param targetId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/readByIdAndTargetId")
    public BaseOutput readByIdAndTargetId(@RequestParam Long annunciateId, @RequestParam Long targetId) {
        try{
            return annunciateItemService.readByAnnunciateItemId(annunciateId, targetId, AnnunciateItemOpType.OP_READ.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据信息通告id和targetId标记该消息删除
     * @param annunciateId:
     * @param targetId:
     * @return：com.dili.ss.domain.BaseOutput<java.lang.String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/deleteByIdAndTargetId")
    public BaseOutput deleteByIdAndTargetId(@RequestParam Long annunciateId, @RequestParam Long targetId) {
        try{
            return annunciateItemService.readByAnnunciateItemId(annunciateId, targetId, AnnunciateItemOpType.OP_DEL.getValue());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据用户id查询消息列表(不包括富文本消息内容，以节约带宽)
     * @param targetId:
     * @return：com.dili.ss.domain.BaseOutput<List<Annunciate>>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/getListByTargetId")
    public BaseOutput<List<AnnunciateVo>> getListByTargetId(@RequestBody Long targetId) {
        try{
            return annunciateService.getListByUserId(targetId);
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