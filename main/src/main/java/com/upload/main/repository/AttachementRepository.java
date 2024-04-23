package com.upload.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upload.main.entity.Attachement;

@Repository
public interface AttachementRepository  extends JpaRepository<Attachement,String>{
    
}
