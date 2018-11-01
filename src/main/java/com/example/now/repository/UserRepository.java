package com.example.now.repository;
import com.example.now.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByUsername(String Username);
}