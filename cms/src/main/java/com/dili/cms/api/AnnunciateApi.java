/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateApi created on 2021/1/21 16:28 by Henry.Huang
 */
package com.dili.cms.api;

import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.cms.sdk.glossary.AnnunciateItemOpType;
import com.dili.cms.service.AnnunciateItemService;
import com.dili.cms.service.AnnunciateService;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.domain.PageOutput;
import com.dili.ss.exception.AppException;
import com.dili.ss.metadata.ValueProviderUtils;
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
     * 根据用户id查询消息列表(不包括富文本消息内容，以节约带宽) 分页
     * @param annunciateDto:
     * @return：com.dili.ss.domain.BaseOutput<String>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/getListByTargetId")
    public String getListByTargetId(@RequestBody AnnunciateDto annunciateDto) {
        try{
            PageOutput<List<AnnunciateVo>> output =annunciateService.getListByUserId(annunciateDto);
            return new EasyuiPageOutput(output.getTotal(), ValueProviderUtils.buildDataByProvider(annunciateDto, output.getData())).toString();
        }catch (Exception e) {
            return e.getMessage();
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

    /**
     * 根据客户id查询置顶三条消息列表(不包括富文本消息内容，以节约带宽)
     * @param annunciateDto:
     * @return：com.dili.ss.domain.BaseOutput<List<AnnunciateVo>>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/getStickListByTargetId")
    public BaseOutput<List<AnnunciateVo>> getStickListByTargetId(@RequestBody AnnunciateDto annunciateDto) {
        try{
            return annunciateService.getStickListByTargetId(annunciateDto.getTargetId());
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 读，需要传4个值terminal id readState targetId
     * @param annunciateDto:
     * @return：com.dili.ss.domain.BaseOutput<Annunciate>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/readByAnnunciateDto")
    public BaseOutput<Annunciate> readByAnnunciateDto(@RequestBody AnnunciateDto annunciateDto) {
        try{
            return annunciateService.readByAnnunciateDto(annunciateDto);
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }

    /**
     * 根据targetId获取未读信息通告数
     * @param annunciateDto:
     * @return：com.dili.ss.domain.BaseOutput<Integer>
     * @author：Henry.Huang
     * @date：2021/1/21 16:38
     */
    @PostMapping(value = "/getNoReadCountByTargetId")
    public BaseOutput<Integer> getNoReadCountByTargetId(@RequestBody AnnunciateDto annunciateDto) {
        try{
            return annunciateItemService.getNoReadCountByTargetId(annunciateDto);
        }catch (AppException e) {
            return BaseOutput.failure(e.getMessage());
        }
    }



}