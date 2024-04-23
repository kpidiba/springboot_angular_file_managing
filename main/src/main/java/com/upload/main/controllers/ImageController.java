package com.upload.main.controllers;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upload.main.dto.ImageDTO;
import com.upload.main.entity.Image;
import com.upload.main.response.UploadApiResponse;
import com.upload.main.services.attachements.ImageService;
import com.upload.main.utils.FileUploadUtils;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.File;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin(origins = "*")
public class ImageController {
    private ImageService imageService;
    private FileUploadUtils fileUploadUtils;

    public ImageController(ImageService service, FileUploadUtils fileUploadUtils) {
        this.fileUploadUtils = fileUploadUtils;
        this.imageService = service;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ImageDTO>> getIMages() {
        return ResponseEntity.ok(this.imageService.getAll());
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<UploadApiResponse> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String name = this.fileUploadUtils.uploadImage(file);
        this.imageService.saveImage(new Image(name, file.getSize()));
        return ResponseEntity.ok(new UploadApiResponse("upload success", name));
    }

    @GetMapping("/download/{imageName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageName) throws IOException {
        String UPLOAD_DIR = new File("src/main/resources/static/").getAbsolutePath();
        Path imagePath = Paths.get(UPLOAD_DIR, "images", imageName);

        // Load the image file as a resource
        Resource resource = new FileSystemResource(imagePath);

        // Set the Content-Disposition header to enable file download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + imageName);

        // Return the image file as a response
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Image> deleteImage(@PathVariable("id") Long id) {
        Image image = this.imageService.findImageById(id);

        if (image == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        this.fileUploadUtils.deleteImage(image.getName());
        this.imageService.deleteImage(image);

        return ResponseEntity.ok(image);
    }

    // NOTE:MULTI PARAMETERS
    @PostMapping(value = "/upload/api/param")
    public ResponseEntity<UploadApiResponse> uploadFileParam(@RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("file") MultipartFile file) throws Exception {
        // NOTE: UPLOAD FILE INFORMATIONS
        System.out.println("name: " + file.getName());
        System.out.println("size: " + file.getSize());
        System.out.println("Original File name: " + file.getOriginalFilename());
        System.out.println("content Type: " + file.getContentType());
        return ResponseEntity.ok(new UploadApiResponse("upload success", ""));
    }

}
