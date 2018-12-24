package com.example.now.entity;
import javax.persistence.*;

@Entity
@Table(name = "Photo_album")
public class PhotoAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_album_id")
    private int id;
    @Column(name = "requester_id")
    private int requesterId;
    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoAlbum() {

    }

    public PhotoAlbum(int requesterId, String name) {
        this.requesterId = requesterId;
        this.name = name;
    }
}
