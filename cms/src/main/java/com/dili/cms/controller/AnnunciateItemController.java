/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * AnnunciateItemController created on 2021/1/29 14:26 by Ron.Peng
 */
package com.dili.cms.controller;

import com.dili.cms.sdk.dto.AnnunciateItemDto;
import com.dili.cms.service.AnnunciateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 * Description
 * TODO file description here
 *
 * @author Ron.Peng
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/29  Ron.Peng  Initial version.
 * </pre>
 */
@Controller
@RequestMapping("/annunciateItem")
public class AnnunciateItemController {
    @Autowired
    AnnunciateItemService annunciateItemService;

    /**
     * 分页查询AnnunciateItem，返回easyui分页信息
     *
     * @param annunciateItem
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/listPage.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String listPage(AnnunciateItemDto annunciateItem) throws Exception {
        return annunciateItemService.listEasyuiPageByExample(annunciateItem, true).toString();
    }
}
