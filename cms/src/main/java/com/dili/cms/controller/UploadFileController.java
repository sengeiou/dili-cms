/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * UploadFileController.java created on 2021/1/12 14:08 by Henry.Huang
 */
package com.dili.cms.controller;

import com.alibaba.fastjson.JSON;
import com.dili.ss.domain.BaseOutput;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <pre>
 * Description
 * TODO file description here 上传controller
 *
 * @author Henry.Huang
 * @since 1.0
 *
 * Change History
 * Date Modifier DESC.
 * 2021/1/12 Henry.Huang Initial version.
 * </pre>
 */
@Controller
@RequestMapping("/uploadFile")
public class UploadFileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);


    @Value("${upload.config.baseUrl}")
    private String baseUrl;
    /**
     * 图片暂存路径
     */
    @Value("${upload.config.accessToken}")
    private String accessToken;

    /**
     * TODO 上传文件
     *
     * @param file:
     * @return：com.dili.ss.domain.BaseOutput
     * @author：Henry.Huang
     * @date：2021/1/12 14:10
     */
    @RequestMapping(value = "/doUpload.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public BaseOutput doUpload(@RequestParam("file") MultipartFile file) {
        try {
            BaseOutput resultUrl = doUploadFileToDFS(file);
            if (logger.isInfoEnabled()) {
                logger.info("====>>>upload result:" + JSON.toJSONString(resultUrl));
            }
            if (resultUrl.isSuccess()) {
                resultUrl.setData("https://dfs.diligrp.com/file/download/" + resultUrl.getData());
                return resultUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=====>>>upload error" + e.getMessage());
            return BaseOutput.failure();
        }
        return BaseOutput.failure();
    }

    /**
     * TODO 执行上传到dfs服务器
     *
     * @param file:
     * @return：java.lang.String
     * @author：Henry.Huang
     * @date：2021/1/12 14:10
     */
    private BaseOutput doUploadFileToDFS(MultipartFile file) throws Exception {
        String uploadPath = baseUrl + "/file/upload";
        String fileName = file.getOriginalFilename();
        //暂存
        File targetFile = new File(fileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        //如果备份报错需要改成copyInputStreamToFile;
        FileSystemResource resource = new FileSystemResource(targetFile);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("accessToken", accessToken);
        param.add("file", resource);
        //上传
        RestTemplate restTemplate = new RestTemplate();
        if (logger.isInfoEnabled()) {
            logger.info("===========>>>>RestTemplate Url:" + uploadPath + "|uploadMap:" + param);
        }
        BaseOutput res = restTemplate.postForObject(uploadPath, param, BaseOutput.class);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        return res;
    }
}