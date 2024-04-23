package com.upload.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upload.main.entity.Image;

public interface ImageRepository extends JpaRepository<Image,Long>{
    Optional<Image> findByName(String name);
}
