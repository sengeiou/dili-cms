/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateDto created on 2021/1/20 14:23 by Henry.Huang
 */
package com.dili.cms.sdk.dto;

import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.domain.AnnunciateItem;
import com.dili.cms.sdk.domain.AnnunciateTarget;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre> 
 * Description 
 * 信息通告dto
 *
 * @author Henry.Huang
 * @since 1.0
 *
 * Change History 
 * Date Modifier DESC. 
 * 2021/1/20 Henry.Huang Initial version. 
 * </pre> 
 */
public interface AnnunciateDto extends Annunciate {

    /**
     * 通告创建时间范围
     * @return
     */
    LocalDateTime getCreateTimeStart();

    void setCreateTimeStart(LocalDateTime createTimeStart);

    LocalDateTime getCreateTimeEnd();

    void setCreateTimeEnd(LocalDateTime createTimeEnd);

    /**
     * 发送对象
     * @return
     */
    Integer getTargetType();

    void setTargetType(Integer targetType);

    /**
     * 通告项数据List
     * @return
     */
    List<AnnunciateItem> getAnnunciateItems();

    void  setAnnunciateItems(List<AnnunciateItem> annunciateItems);

    /**
     * 通告目标List
     * @return
     */
    List<AnnunciateTarget> getAnnunciateTargets();

    void setAnnunciateTargets(List<AnnunciateTarget> annunciateTargets);

    /**
     * 更新状态 1为未发布更新 2位已撤销更新
     * @return
     */
    Integer getUpateType();

    void setUpateType(Integer upateType);

    /**
     * 通告项客户或用户id
     * @return
     */
    Long getTargetId();

    void setTargetId(Long targetId);

    /**
     * 查看终端
     * @return
     */
    Integer getTerminal();

    void setTerminal(Integer terminal);

    /**
     * 读取状态
     * @return：java.lang.Integer
     * @author：Henry.Huang
     * @date：2021/1/22 16:03
     */
    Integer getReadState();

    void setReadState(Integer readState);
}