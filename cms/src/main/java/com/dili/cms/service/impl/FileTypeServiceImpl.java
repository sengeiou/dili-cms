/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileTypeServiceImpl.java created on 2021/1/20 15:34 by Tab.Xie
 */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.FileTypeMapper;
import com.dili.cms.sdk.domain.IFileType;
import com.dili.cms.service.FileTypeService;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.exception.AppException;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <pre>
 * Description
 * TODO 文件类型管理
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
@Service
public class FileTypeServiceImpl extends BaseServiceImpl<IFileType, Long> implements FileTypeService {
    public static final Long DEFAULT_PARENT_ID = 1L;
    public static final Integer DEFAULT_NODE_COUNT = 0;
    public static final Integer DEFAULT_VERSION = 0;

    public FileTypeMapper getActualDao() {
        return (FileTypeMapper) getDao();
    }

    @Override
    public BaseOutput saveOrUpdateFileType(IFileType iFileType) {
        if (iFileType.getId() == null) {
            this.addFileType(iFileType);
            return BaseOutput.success("新增文档分类成功");
        } else {
            this.updateFileType(iFileType);
            return BaseOutput.success("编辑文档分类成功");
        }
    }

    /**
     * TODO 新增文档分类
     *
     * @param iFileType:
     * @return：
     * @author：Ron.Peng
     * @date：2021/1/25 20:42
     */
    @Transactional(rollbackFor = Exception.class)
    public void addFileType(IFileType iFileType) {
        //UserTicket userTicket = SessionContext.getSessionContext().getUserTicket();
        iFileType.setParentId(DEFAULT_PARENT_ID);
        iFileType.setNodeFileCount(DEFAULT_NODE_COUNT);
        /*iFileType.setFirmCode(userTicket.getFirmCode());
        iFileType.setCreatorId(userTicket.getId());*/
        iFileType.setFirmCode("group");
        iFileType.setCreatorId(1L);
        iFileType.setCreateTime(LocalDateTime.now());
        iFileType.setUpdateTime(LocalDateTime.now());
        iFileType.setVersion(DEFAULT_VERSION);
        int insertSelective = this.insertSelective(iFileType);
        if (insertSelective <= 0) {
            throw new AppException("新增文档分类失败！");
        }
    }

    /**
     * TODO 编辑文档分类
     *
     * @param iFileType:
     * @return：
     * @author：Ron.Peng
     * @date：2021/1/25 20:45
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateFileType(IFileType iFileType) {
        IFileType iFileTypeByQuery = this.get(iFileType.getId());
        if (!Objects.nonNull(iFileTypeByQuery)) {
            throw new AppException("文档类型不存在");
        }
        //乐观锁条件
        Example fileTypeExample = getFileTypeByVersion(iFileType.getId(), iFileType.getVersion());
        IFileType updateFileType = DTOUtils.newInstance(IFileType.class);
        //更新文档分类
        updateFileType.setId(iFileType.getId());
        updateFileType.setName(iFileType.getName());
        updateFileType.setUpdateTime(LocalDateTime.now());
        updateFileType.setVersion(iFileTypeByQuery.getVersion()+1);
        int updateOrderResult = this.getActualDao().updateByExampleSelective(updateFileType, fileTypeExample);
        if (updateOrderResult <= 0) {
            throw new AppException("更新文档类型失败，当前文档类型或已被编辑");
        }
    }

    /**
     * TODO 根据id和version设置example
     *
     * @param id:
     * @param version:
     * @return：tk.mybatis.mapper.entity.Example
     * @author：Ron.Peng
     * @date：2021/1/25 20:48
     */
    private Example getFileTypeByVersion(Object id, Object version) {
        Example example = new Example(IFileType.class);
        example.createCriteria()
                .andEqualTo("id", id)
                .andEqualTo("version", version);
        return example;
    }
}