/**
  * Copyright (C) DiliGroup. All Rights Reserved.
  *
  * AnnunciateServiceImpl.java created on 2021/1/21 13:33 by Henry.Huang
  */
package com.dili.cms.service.impl;

import com.dili.cms.mapper.AnnunciateMapper;
import com.dili.cms.sdk.domain.Annunciate;
import com.dili.cms.sdk.dto.AnnunciateDto;
import com.dili.cms.sdk.dto.AnnunciateVo;
import com.dili.cms.service.AnnunciateService;
import com.dili.ss.base.BaseServiceImpl;
import com.dili.ss.domain.PageOutput;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * <pre>
  * Description
  * 信息通告service
  *
  * @author Henry.Huang
  * @since 1.0
  *
  * Change History
  * Date Modifier DESC.
  * 2021/1/21 Henry.Huang Initial version.
  * </pre>
  */
@Service
public class AnnunciateServiceImpl extends BaseServiceImpl<Annunciate, Long> implements AnnunciateService {

    public AnnunciateMapper getActualDao() {
        return (AnnunciateMapper)getDao();
    }


    @Override
    public PageOutput<List<AnnunciateVo>> listByQueryParams(AnnunciateDto annunciateDto) {
        Integer page = annunciateDto.getPage();
        page = (page == null) ? Integer.valueOf(1) : page;
        if (annunciateDto.getRows() != null && annunciateDto.getRows() >= 1) {
            PageHelper.startPage(page, annunciateDto.getRows());
        }
        List<AnnunciateVo> list = getActualDao().listByQueryParams(annunciateDto);
        Long total = list instanceof Page ? ((Page) list).getTotal() : list.size();
        int totalPage = list instanceof Page ? ((Page) list).getPages() : 1;
        int pageNum = list instanceof Page ? ((Page) list).getPageNum() : 1;
        PageOutput<List<AnnunciateVo>> output = PageOutput.success();
        output.setData(list).setPageNum(pageNum).setTotal(total).setPageSize(annunciateDto.getPage()).setPages(totalPage);
        return output;
    }
}