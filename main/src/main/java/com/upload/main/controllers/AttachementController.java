package com.upload.main.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.upload.main.dto.AttachementResponse;
import com.upload.main.entity.Attachement;
import com.upload.main.services.attachements.AttachementService;

@RestController
public class AttachementController {
    private AttachementService attachementService;

    public AttachementController(AttachementService attachementService) {
        this.attachementService = attachementService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AttachementResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        Attachement attachement = null;
        String downloadURL = "";
        attachement = attachementService.saveAttachement(file);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(attachement.getId())
                .toUriString();
        return new AttachementResponse(attachement.getFileName(), downloadURL, file.getContentType(), file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachement attachement = null;
        attachement = attachementService.getAttachement(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachement.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachement.getFileName() + "\"")
                .body(new ByteArrayResource(attachement.getData()));
    }
}
