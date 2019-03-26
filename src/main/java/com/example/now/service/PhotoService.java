package com.example.now.service;

import com.example.now.entity.PhotoAlbumDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface PhotoService {
    int addPhotoAlbum(int requesterId,String name);
    int changePhotoAlbumName(int id,String name);
    String addPhoto(MultipartFile multipartFile, int photoAlbumId) throws IOException;
    int deletePhoto(int id);
    List<PhotoAlbumDetail> findPhotos(int requesterId);
}
