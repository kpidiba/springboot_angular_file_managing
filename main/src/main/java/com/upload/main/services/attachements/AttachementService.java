package com.upload.main.services.attachements;

import org.springframework.web.multipart.MultipartFile;
import com.upload.main.entity.Attachement;

public interface AttachementService {
    Attachement saveAttachement(MultipartFile file) throws Exception;

    Attachement getAttachement(String fileId) throws Exception;
}
