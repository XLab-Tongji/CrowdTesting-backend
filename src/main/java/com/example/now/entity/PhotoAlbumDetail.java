package com.example.now.entity;
import java.util.List;
public class PhotoAlbumDetail {
    private PhotoAlbum photoAlbum;
    private List<Resource> resources;

    public PhotoAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    public void setPhotoAlbum(PhotoAlbum photoAlbum) {
        this.photoAlbum = photoAlbum;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public PhotoAlbumDetail() {

    }

    public PhotoAlbumDetail(PhotoAlbum photoAlbum) {
        this.photoAlbum = photoAlbum;
    }
}
