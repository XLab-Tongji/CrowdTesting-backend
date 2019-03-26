package com.example.now.entity;
import javax.persistence.*;

@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Integer id;
    @Column(name = "link")
    private String link;
    @Column(name = "type")
    private String type;
    @Column(name = "photo_album_id")
    private int photoAlbumId;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPhotoAlbumId() {
        return photoAlbumId;
    }

    public void setPhotoAlbumId(int photoAlbumId) {
        this.photoAlbumId = photoAlbumId;
    }

    public Resource(){

    }

    public Resource(String link, String type, int photoAlbumId) {
        this.link = link;
        this.type = type;
        this.photoAlbumId = photoAlbumId;
    }
}
