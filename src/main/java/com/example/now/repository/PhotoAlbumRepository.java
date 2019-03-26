package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.PhotoAlbum;

import java.util.List;

public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum,Integer>{
    PhotoAlbum findById(int id);
    List<PhotoAlbum> findByRequesterId(int id);
}
