/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * component created on 2021/1/26 8:54 by Tab.Xie
 */
package com.dili.cms.controller;

import com.dili.ss.domain.BaseOutput;
import com.dili.uap.sdk.domain.User;
import com.dili.uap.sdk.domain.dto.DepartmentDto;
import com.dili.uap.sdk.rpc.DepartmentRpc;
import com.dili.uap.sdk.rpc.UserRpc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 * Description
 * TODO 组件controller
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/26  Tab.Xie  Initial version.
 * </pre>
 */
@Controller
@RequestMapping("/component")
public class ComponentController extends BaseController {
    @Resource
    private UserRpc userRpc;
    @Resource
    private DepartmentRpc departmentRpc;

    /**
     * TODO 当前登录用户市场下的所有用户列表
     *
     * @param user:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Tab.Xie
     * @date：2021/1/26 9:04
     */
    @RequestMapping(value = "/userList.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput userList(User user) {
        user.setFirmCode(getFirmCode());
        return userRpc.list(user);
    }

    /**
     * TODO 用户选择界面
     *
     * @param ids:默认选择的id
     * @param modelMap:
     * @return：java.lang.String
     * @author：Tab.Xie
     * @date：2021/1/26 11:00
     */
    @RequestMapping(value = "/userSelect.html", method = RequestMethod.GET)
    public String userSelect(@RequestParam("ids") List<Long> ids, ModelMap modelMap) {
        modelMap.put("ids", ids);
        return "component/userSelect";
    }


    /**
     * TODO 前登录用户市场下的所有部门列表
     *
     * @param departmentDto:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Tab.Xie
     * @date：2021/1/26 9:06
     */
    @RequestMapping(value = "/departmentList.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput departmentList(DepartmentDto departmentDto) {
        departmentDto.setFirmId(getFirmId());
        return departmentRpc.listByExample(departmentDto);
    }
}