package com.example.now.controller;
import com.example.now.entity.PhotoAlbumDetail;
import com.example.now.entity.ResultMap;
import com.example.now.service.*;
import com.example.now.util.TokenUtils;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

import com.example.now.exception.BusinessException;
import com.example.now.repository.QuestionRepository;
import javax.servlet.http.HttpServletRequest;
import com.example.now.service.QuestionService;

@RestController
@RequestMapping("/image")
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
    @Autowired
    private PhotoService photoService;
    @RequestMapping(value = "/add-album", method = RequestMethod.POST)
    public ResultMap addAlbum(String name){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=requesterService.findRequesterByUsername(username).getRequesterId();
        int albumId=photoService.addPhotoAlbum(userid,name);
        return new ResultMap().success("201").data("albumId",albumId);
    }
    @RequestMapping(value = "/change-album-name", method = RequestMethod.PUT)
    public ResultMap changeAlbumName(int id,String name){
        photoService.changePhotoAlbumName(id,name);
        return new ResultMap().success("201").data("albumId",id);
    }
    @RequestMapping(value = "/findImages", method = RequestMethod.GET)
    public ResultMap findImages(){
        String authToken = request.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        int userid=requesterService.findRequesterByUsername(username).getRequesterId();
        List<PhotoAlbumDetail> photoAlbumDetails=photoService.findPhotos(userid);
        if(photoAlbumDetails.isEmpty())
            return new ResultMap().success("204").message("nothing to show");
        else
            return new ResultMap().success("200").data("images",photoAlbumDetails);
    }
    @RequestMapping(value = "/add-image", method = RequestMethod.POST)
    public ResultMap addImage(MultipartFile multipartFile,int photoAlbumId) throws IOException{
        try{
            String fileName=photoService.addPhoto(multipartFile,photoAlbumId);
            return new ResultMap().success("201").data("fileName",fileName);
        }catch (IOException e){
            throw new BusinessException("403","保存图片出错");
        }
    }
    @RequestMapping(value = "/delete-image", method = RequestMethod.PUT)
    public ResultMap deleteImage(int id){
        photoService.deletePhoto(id);
        return new ResultMap().success("201").message("删除图片成功");
    }
}
