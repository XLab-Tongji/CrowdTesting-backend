package com.example.now.controller;
import com.example.now.entity.ResultMap;
import com.example.now.repository.RequesterRepository;
import com.example.now.service.QuestionService;
import com.example.now.service.RequesterService;
import com.example.now.service.WorkerService;
import com.example.now.util.MD5Util;
import com.example.now.entity.TokenDetail;
import com.example.now.util.ImageUtil;
import com.example.now.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;
import java.io.*;
import com.example.now.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ImageController {
    @Value("${token.header}")
    private String tokenHeader;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RequesterService requesterService;
    @Autowired
    private HttpServletRequest request;
    @Value("${img.location}")
    private String location;
    @RequestMapping(value = "/put-image", method = RequestMethod.POST)
    public ResultMap putImage(MultipartFile multipartFile,int taskId){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=requesterService.findRequesterByUsername(username).getRequesterId();
        String filePath = location+File.separator+String.valueOf(userid)+File.separator+String.valueOf(taskId);
        try{
            String file_name = ImageUtil.saveImg(multipartFile, filePath);
            if(StringUtils.isNotBlank(file_name)){
                return new ResultMap().success().data("file_name",file_name);
            }
            else{
                return new ResultMap().fail("400").message("上传图片失败");
            }
        }catch (IOException e){
            throw new BusinessException("403","保存图片出错");
        }
    }
}
