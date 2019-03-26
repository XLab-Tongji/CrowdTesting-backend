package com.example.now.service.Iml;
import com.example.now.entity.*;
import com.example.now.repository.*;
import com.example.now.util.ImageUtil;
import com.example.now.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.now.service.QuestionService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
@Service
public class PhotoServiceIml implements PhotoService {
    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Value("${img.location}")
    private String location;
    @Override
    public List<PhotoAlbumDetail> findPhotos(int requesterId){
        List<PhotoAlbumDetail> photoAlbumDetails=new ArrayList<PhotoAlbumDetail>();
        List<PhotoAlbum> photoAlbums=photoAlbumRepository.findByRequesterId(requesterId);
        PhotoAlbumDetail photoAlbumDetail;
        for(PhotoAlbum photoAlbum:photoAlbums){
            photoAlbumDetail=new PhotoAlbumDetail(photoAlbum);
            photoAlbumDetail.setResources(resourceRepository.findByPhotoAlbumId(photoAlbum.getId()));
            photoAlbumDetails.add(photoAlbumDetail);
        }
        return photoAlbumDetails;
    }
    @Override
    public int addPhotoAlbum(int requesterId,String name){
        String path=location+'/'+String.valueOf(requesterId)+'/'+name;
        PhotoAlbum photoAlbum=new PhotoAlbum(requesterId,name);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return photoAlbumRepository.saveAndFlush(photoAlbum).getId();
    }
    @Override
    public int changePhotoAlbumName(int id,String name){
        PhotoAlbum photoAlbum=photoAlbumRepository.findById(id);
        String path=location+'/'+String.valueOf(photoAlbum.getRequesterId());
        File file = new File(path+'/'+photoAlbum.getName());
        file.renameTo(new File(path+'/'+name));
        photoAlbum.setName(name);
        photoAlbumRepository.saveAndFlush(photoAlbum);
        return id;
    }
    @Override
    public String addPhoto(MultipartFile multipartFile, int photoAlbumId) throws IOException {
        PhotoAlbum photoAlbum=photoAlbumRepository.findById(photoAlbumId);
        String path=location+'/'+String.valueOf(photoAlbum.getRequesterId())+'/'+photoAlbum.getName();
        String fileName=ImageUtil.saveImg(multipartFile,path);
        Resource resource=new Resource("http://139.199.183.133:8081/"+String.valueOf(photoAlbum.getRequesterId())+'/'+photoAlbum.getName()+'/'+fileName,"picture",photoAlbumId);
        resourceRepository.saveAndFlush(resource).getId();
        return fileName;
    }
    @Override
    public int deletePhoto(int id){
        Resource resource=resourceRepository.findById(id);
        File file = new File(resource.getLink());
        if (file.exists()) {
            if (file.isFile())
                file.delete();
        }
        resourceRepository.delete(resource);
        resourceRepository.flush();
        return resource.getId();
    }
}
