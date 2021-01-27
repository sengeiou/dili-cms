/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * FileController created on 2021/1/21 15:24 by Tab.Xie
 */
package com.dili.cms.controller;

import com.alibaba.fastjson.JSON;
import com.dili.cms.commons.ValidationUtil;
import com.dili.cms.sdk.domain.IFileType;
import com.dili.cms.sdk.dto.IFileDto;
import com.dili.cms.sdk.validator.ConstantValidator;
import com.dili.cms.service.impl.FileServiceImpl;
import com.dili.cms.service.impl.FileTypeServiceImpl;
import com.dili.ss.domain.BaseOutput;
import com.dili.ss.domain.EasyuiPageOutput;
import com.dili.ss.dto.DTOUtils;
import com.dili.uap.sdk.domain.Department;
import com.dili.uap.sdk.domain.User;
import com.dili.uap.sdk.domain.dto.DepartmentDto;
import com.dili.uap.sdk.rpc.DepartmentRpc;
import com.dili.uap.sdk.rpc.UserRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Description
 * TODO 文件管理
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/21  Tab.Xie  Initial version.
 * </pre>
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private FileTypeServiceImpl fileTypeService;
    @Resource
    private UserRpc userRpc;
    @Resource
    private DepartmentRpc departmentRpc;

    /**
     * TODO 列表页面
     *
     * @return：java.lang.String
     * @author：Ron.Peng
     * @date：2021/1/23 14:14
     */
    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public String list() {
        return "file/list";
    }


    /**
     * TODO 新增页面
     *
     * @return：java.lang.String
     * @author：Tab.Xie
     * @date：2021/1/21 16:36
     */
    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public String add(Long id, ModelMap modelMap) {
        DepartmentDto departmentDto = DTOUtils.newInstance(DepartmentDto.class);
        departmentDto.setFirmId(getFirmId());
        BaseOutput<List<Department>> departmentList = departmentRpc.listByExample(departmentDto);
        modelMap.put("departmentList", JSON.toJSONString(departmentList.getData()));

        User user = DTOUtils.newInstance(User.class);
        user.setFirmCode(getFirmCode());
        BaseOutput<List<User>> userList = userRpc.list(user);
        modelMap.put("userList", userList.getData());

        List<IFileType> fileTypeList = fileTypeService.list(null);
        modelMap.put("fileTypeList", JSON.toJSONString(fileTypeList));

        //如果参数id不为空 则是编辑
        if (Objects.nonNull(id)) {
            IFileDto fileDto = fileService.getDetailById(id);
            modelMap.put("fileInfo", JSON.toJSONString(fileDto));
            modelMap.put("fileCount", fileDto.getFileItemList().size());
        }
        return "file/add";
    }

    /**
     * TODO 新增
     *
     * @param fileDto:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Tab.Xie
     * @date：2021/1/21 15:25
     */
    @RequestMapping(value = "insert.action", method = {RequestMethod.POST})
    @ResponseBody
    public BaseOutput insert(@RequestBody IFileDto fileDto) {
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(fileDto, ConstantValidator.Insert.class);
        if (validResult.hasErrors()) {
            return BaseOutput.failure(validResult.getErrors());
        }
        return fileService.create(fileDto);
    }

    /**
     * TODO 编辑
     *
     * @param fileDto:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Tab.Xie
     * @date：2021/1/21 15:25
     */
    @RequestMapping(value = "update.action", method = {RequestMethod.POST})
    @ResponseBody
    public BaseOutput update(@RequestBody IFileDto fileDto) {
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(fileDto, ConstantValidator.Update.class);
        if (validResult.hasErrors()) {
            return BaseOutput.failure(validResult.getErrors());
        }
        return fileService.edit(fileDto);
    }


    /**
     * TODO 文件详情
     *
     * @param modelMap:
     * @return：java.lang.String
     * @author：Tab.Xie
     * @date：2021/1/26 20:10
     */
    @RequestMapping(value = "view.html", method = RequestMethod.GET)
    public String view(Long id, ModelMap modelMap) {
        //如果参数id不为空 则是编辑
        if (Objects.nonNull(id)) {
            IFileDto fileDto = fileService.getDetailById(id);
            modelMap.put("fileInfo", fileDto);
        }
        return "file/view";
    }


    /**
     * TODO 删除文件
     *
     * @param ids:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Tab.Xie
     * @date：2021/1/27 14:51
     */
    @RequestMapping(value = "delete.action", method = RequestMethod.POST)
    @ResponseBody
    public BaseOutput delete(@RequestBody List<Long> ids) {
        return fileService.deleteByIds(ids);
    }

    /**
     * TODO 列表查询
     *
     * @param iFileDto:
     * @return：com.dili.ss.domain.EasyuiPageOutput
     * @author：Ron.Peng
     * @date：2021/1/23 15:24
     */
    @RequestMapping(value = "/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public EasyuiPageOutput listPage(IFileDto iFileDto) throws Exception {
        iFileDto.setDepartmentId(getDepartmentId());
        iFileDto.setPersonId(getUserId());
        return fileService.listPage(iFileDto, true);
    }
}