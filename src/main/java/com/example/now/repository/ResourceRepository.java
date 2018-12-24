package com.example.now.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.now.entity.Resource;
import java.util.List;
public interface ResourceRepository extends JpaRepository<Resource,Integer>{
    Resource findById(int id);
    List<Resource> findByPhotoAlbumId(int id);
}
