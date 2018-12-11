package com.example.now.controller;
import com.example.now.entity.ResultMap;
import com.example.now.repository.RequesterRepository;
import com.example.now.service.*;
import com.example.now.util.MD5Util;
import com.example.now.entity.TokenDetail;
import com.example.now.util.ImageUtil;
import com.example.now.util.TokenUtils;
import com.qiniu.http.Response;
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
import java.util.UUID;

import com.example.now.exception.BusinessException;
import com.example.now.repository.QuestionRepository;
import javax.servlet.http.HttpServletRequest;
import com.example.now.service.QuestionService;

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
    @Autowired
    private QuestionRepository questionRepository;
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.path}")
    private String path;
    @Value("${img.location}")
    private String location;
    @Autowired
    private IQiniuUploadFileService iQiniuUploadFileService;
    @RequestMapping(value = "/put-image", method = RequestMethod.POST)
    public ResultMap putImage(MultipartFile multipartFile,int questionId,String type) throws IOException{
        int taskId=questionRepository.findById(questionId).getTask_id();
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=requesterService.findRequesterByUsername(username).getRequesterId();
        String filePath = String.valueOf(userid)+File.separator+String.valueOf(taskId);
        String file_name= UUID.randomUUID().toString() + ".png";
        try{
            FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
            Response response = iQiniuUploadFileService.uploadFile(fileInputStream,filePath+File.separator+file_name);
            if(response.bodyString()!=null){
                questionService.addResource(questionId,file_name,type);
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
