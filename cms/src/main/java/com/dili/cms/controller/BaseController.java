/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * BaseController created on 2021/1/20 15:28 by Tab.Xie
 */
package com.dili.cms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dili.uap.sdk.domain.UserTicket;
import com.dili.uap.sdk.session.SessionContext;

/**
 * <pre>
 * Description
 * TODO 公共Controller
 *
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/20  Tab.Xie  Initial version.
 * </pre>
 */
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected UserTicket getCurrentUser() {
        return SessionContext.getSessionContext().getUserTicket();
    }


    protected Long getUserId() {
        return getCurrentUser().getId();
    }

    protected Long getDepartmentId() {
        return getCurrentUser().getDepartmentId();
    }

    protected Long getOrgId() {
        return getCurrentUser().getId();
    }

    protected String getUserName() {
        return getCurrentUser().getUserName();
    }

    protected String getRealName() {
        return getCurrentUser().getRealName();
    }

    protected Long getFirmId() {
        return getCurrentUser().getFirmId();
    }

    protected String getFirmCode() {
        return getCurrentUser().getFirmCode();
    }
}