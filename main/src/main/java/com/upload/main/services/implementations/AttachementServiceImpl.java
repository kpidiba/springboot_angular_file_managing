package com.upload.main.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.upload.main.entity.Attachement;
import com.upload.main.repository.AttachementRepository;
import com.upload.main.services.attachements.AttachementService;

@Service
public class AttachementServiceImpl implements AttachementService {

    private AttachementRepository repository;

    private AttachementServiceImpl(AttachementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Attachement saveAttachement(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // NOTE: MAKE A CONTROL HERE
        try {
            if (fileName.contains("..")) {
                throw new Exception("FileName contains invalid path sequence" + fileName);
            }
            Attachement attachement =  new Attachement(fileName, file.getContentType(), file.getBytes());
            return repository.save(attachement);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not Save File" + fileName);
        }
    }

    @Override
    public Attachement getAttachement(String fileId) throws Exception {
        return repository.findById(fileId).orElseThrow(()-> new Exception("File not Found with Id: "+fileId));
    }

}