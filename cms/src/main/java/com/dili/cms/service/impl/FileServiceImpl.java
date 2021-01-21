/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileServiceImpl.java created on 2021/1/20 15:34 by Tab.Xie
 */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.*;
import com.dili.cms.sdk.domain.IFile;
import com.dili.cms.sdk.domain.IFileType;
import com.dili.cms.sdk.dto.IFileDto;
import com.dili.cms.service.FileService;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.exception.AppException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
public class FileServiceImpl extends BaseServiceImpl<IFile, Long> implements FileService {
    @Autowired
    private FileItemMapper fileItemMapper;
    @Autowired
    private FileTypeMapper fileTypeMapper;
    @Autowired
    private FileAuthMapper fileAuthMapper;
    @Autowired
    private FileAuthItemMapper fileAuthItemMapper;

    public FileMapper getActualDao() {
        return (FileMapper) getDao();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput create(IFileDto fileDto) {
        fileDto.setCreateTime(LocalDateTime.now());
        fileDto.setUpdateTime(LocalDateTime.now());
        fileDto.setIsDataAuth(0);
        //先插入文件数据得到文件的id
        int fileResult = insertSelective(fileDto);
        if (fileResult <= 0) {
            throw new AppException("文件新增失败");
        }
        //新增文件信息
        fileDto.getFileItemList().forEach(fileItem -> fileItem.setFileId(fileDto.getId()));
        fileItemMapper.insertList(fileDto.getFileItemList());
        //增加文件类型的节点文件数量（需要一直往上查找得到链路id）
        Set<Long> linkNodeIds = findFileTypeLinkNode(fileDto.getTypeId());
        fileTypeMapper.updateNodeCountBatch(linkNodeIds);
        //判断有没有权限限制
        if (CollectionUtils.isNotEmpty(fileDto.getAuthList())) {
            fileDto.setIsDataAuth(1);
            //填入文件的id
            fileDto.getAuthList().forEach(auth -> auth.setFileId(fileDto.getId()));
        }
        //新增文件权限
        fileAuthMapper.insertList(fileDto.getAuthList());
        //得到精确到人的权限，再新增一次具体权限
        return BaseOutput.success();
    }


    /**
     * 根据一个节点id往上查找所有链路相关的节点id直到顶级节点
     *
     * @param nodeId
     * @return
     */
    public Set<Long> findFileTypeLinkNode(Long nodeId) {
        IFileType fileType = fileTypeMapper.selectByPrimaryKey(nodeId);
        Set<Long> linkNodeIds = new HashSet<>();
        //判断是不是顶级节点
        if (fileType.getParentId() != 0) {
            //一直向上查找直到找到顶级节点为止
            IFileType parentFileType = fileType;
            while (parentFileType.getParentId() != 0 && !Objects.isNull(parentFileType)) {
                linkNodeIds.add(parentFileType.getParentId());
                parentFileType = fileTypeMapper.selectByPrimaryKey(parentFileType.getParentId());
            }
        }
        linkNodeIds.add(nodeId);
        return linkNodeIds;
    }
}