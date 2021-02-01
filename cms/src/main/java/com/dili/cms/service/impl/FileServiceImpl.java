/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileServiceImpl.java created on 2021/1/20 15:34 by Tab.Xie
 */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.FileAuthMapper;
import com.dili.cms.mapper.FileItemMapper;
import com.dili.cms.mapper.FileMapper;
import com.dili.cms.mapper.FileTypeMapper;
import com.dili.cms.sdk.domain.IFile;
import com.dili.cms.sdk.domain.IFileAuth;
import com.dili.cms.sdk.domain.IFileItem;
import com.dili.cms.sdk.domain.IFileType;
import com.dili.cms.sdk.dto.IFileDto;
import com.dili.cms.sdk.glossary.FileAuthType;
import com.dili.cms.service.FileService;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.ss.exception.AppException;
import com.dili.ss.metadata.ValueProviderUtils;
import com.dili.ss.util.POJOUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    public FileMapper getActualDao() {
        return (FileMapper) getDao();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput create(IFileDto fileDto) {
        fileDto.setCreateTime(LocalDateTime.now());
        fileDto.setUpdateTime(LocalDateTime.now());
        fileDto.setVersion(0);
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
        fileTypeMapper.updateNodeCountBatch(linkNodeIds, 1);
        //判断有没有权限限制
        if (CollectionUtils.isNotEmpty(fileDto.getFileAuthList())) {
            //填入文件的id
            fileDto.getFileAuthList().forEach(auth -> auth.setFileId(fileDto.getId()));
            //新增文件权限
            fileAuthMapper.insertList(fileDto.getFileAuthList());
        }
        //得到精确到人的权限，再新增一次具体权限
        return BaseOutput.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput edit(IFileDto fileDto) {
        IFile file = get(fileDto.getId());
        if (Objects.isNull(file)) {
            throw new AppException("文件不存在!");
        }
        //获取一个带乐观锁条件的对象
        Example versionLockExample = buildVersionLockExample(fileDto.getVersion(), IFile.class);
        versionLockExample.and().andEqualTo("id", file.getId());
        fileDto.setVersion(file.getVersion() + 1);
        int update = getActualDao().updateByExample(fileDto, versionLockExample);
        if (update <= 0) {
            throw new AppException("编辑失败!");
        }
        //判断文件类型有没有改变
        if (!fileDto.getTypeId().equals(file.getTypeId())) {
            //先将原先的类型数量减一
            Set<Long> oldTypeIds = findFileTypeLinkNode(file.getTypeId());
            fileTypeMapper.updateNodeCountBatch(oldTypeIds, -1);
            //在将现在的文件类型加一
            Set<Long> newTypeIds = findFileTypeLinkNode(fileDto.getTypeId());
            fileTypeMapper.updateNodeCountBatch(newTypeIds, 1);
        }
        Example example = new Example(IFileItem.class);
        example.createCriteria().andEqualTo("fileId", fileDto.getId());
        //删除原先的文件
        fileItemMapper.deleteByExample(example);
        //如果原来有权限则要先删除原来的权限
        if (!file.getAuthTypeId().equals(FileAuthType.NOT_AUTH.getValue())) {
            fileAuthMapper.deleteByExample(example);
        }
        //新增文件信息
        fileDto.getFileItemList().forEach(fileItem -> fileItem.setFileId(fileDto.getId()));
        fileItemMapper.insertList(fileDto.getFileItemList());
        //新增文件权限
        if (CollectionUtils.isNotEmpty(fileDto.getFileAuthList())) {
            fileDto.getFileAuthList().forEach(auth -> auth.setFileId(fileDto.getId()));
            fileAuthMapper.insertList(fileDto.getFileAuthList());
        }
        return BaseOutput.success();
    }

    @Override
    public IFileDto getDetailById(Long id) {
        IFile file = get(id);
        if (Objects.isNull(file)) {
            throw new AppException("文件不存在!");
        }
        IFileDto fileDto = DTOUtils.newInstance(IFileDto.class);
        BeanUtils.copyProperties(file, fileDto);
        Example example = new Example(IFileItem.class);
        example.createCriteria().andEqualTo("fileId", id);
        List<IFileItem> fileItems = fileItemMapper.selectByExample(example);
        fileDto.setFileItemList(fileItems);

        List<IFileAuth> fileAuthList = fileAuthMapper.selectByExample(example);
        fileDto.setFileAuthList(fileAuthList);
        return fileDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseOutput deleteById(Long id) {
        IFile file = get(id);
        //删除文件信息
        int delete = delete(id);
        if (Objects.isNull(file) || delete <= 0) {
            throw new AppException("文件删除失败!");
        }
        Example example = new Example(IFileItem.class);
        example.createCriteria().andEqualTo("fileId", id);
        //删除下面的文件
        fileItemMapper.deleteByExample(example);
        //删除文件的权限数据
        fileAuthMapper.deleteByExample(example);
        //类型减一
        Set<Long> fileTypeIds = findFileTypeLinkNode(file.getTypeId());
        fileTypeMapper.updateNodeCountBatch(fileTypeIds, -1);
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
        if (Objects.nonNull(fileType) && fileType.getParentId() != 0) {
            //一直向上查找直到找到顶级节点为止
            IFileType parentFileType = fileType;
            while (Objects.nonNull(parentFileType) && parentFileType.getParentId() != 0) {
                linkNodeIds.add(parentFileType.getParentId());
                parentFileType = fileTypeMapper.selectByPrimaryKey(parentFileType.getParentId());
            }
        }
        linkNodeIds.add(nodeId);
        return linkNodeIds;
    }

    @Override
    public EasyuiPageOutput listPage(IFileDto iFileDto, Boolean useProvider) throws Exception {
        if (iFileDto.getRows() != null && iFileDto.getRows() >= 1) {
            PageHelper.startPage(iFileDto.getPage(), iFileDto.getRows());
        }
        if (StringUtils.isNotBlank(iFileDto.getSort())) {
            iFileDto.setSort(POJOUtils.humpToLineFast(iFileDto.getSort()));
        }
        List<IFileDto> iFileDtos = this.getActualDao().listPage(iFileDto);
        long total = iFileDtos instanceof Page ? ((Page) iFileDtos).getTotal() : (long) iFileDtos.size();
        List results = useProvider ? ValueProviderUtils.buildDataByProvider(iFileDto, iFileDtos) : iFileDtos;
        return new EasyuiPageOutput(total, results);
    }

    private Example buildVersionLockExample(Object version, Class c) {
        Example example = new Example(c);
        example.createCriteria()
                .andEqualTo("version", version);
        return example;
    }
}