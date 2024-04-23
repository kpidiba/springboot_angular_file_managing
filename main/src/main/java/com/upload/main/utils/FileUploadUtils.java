package com.upload.main.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtils {
    private static final String UPLOAD_DIR = new File("src/main/resources/static/").getAbsolutePath();
    private static final String IMAGES_SUBFOLDER = "images";
    private static final String PDFS_SUBFOLDER = "pdfs";

    public FileUploadUtils() {
        createSubfolderIfNotExists(IMAGES_SUBFOLDER);
        createSubfolderIfNotExists(PDFS_SUBFOLDER);
    }

    public String uploadImage(MultipartFile imageFile) {
        return uploadFile(imageFile, IMAGES_SUBFOLDER);
    }

    public String deleteImage(String name){
        return this.deleteFile(name, IMAGES_SUBFOLDER);
    }

    public String deletePdf(String name){
        return this.deleteFile(name, PDFS_SUBFOLDER);
    }

    public String uploadPdf(MultipartFile pdfFile) {
        return uploadFile(pdfFile, PDFS_SUBFOLDER);
    }

    public String downloadFile(String name) {
        // Implement file download logic here
        return null;
    }

    private void createSubfolderIfNotExists(String subfolderName) {
        Path subfolderPath = Paths.get(UPLOAD_DIR, subfolderName);
        if (!Files.exists(subfolderPath)) {
            try {
                Files.createDirectories(subfolderPath);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
    }

    private String uploadFile(MultipartFile file, String subfolderName) {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        try {
            Path subfolderPath = Paths.get(UPLOAD_DIR, subfolderName);
            Path filePath = subfolderPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return null;
        }
        return fileName; // Return the file name or handle upload failure
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extension;
        return uniqueFileName;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

    private String deleteFile(String fileName, String subfolderName) {
        try {
            Path filePath = getFilePath(fileName, subfolderName);
            Files.delete(filePath);
            return "File deleted successfully: " + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return "Failed to delete file: " + fileName;
        }
    }

    private Path getFilePath(String fileName, String subfolderName) {
        Path subfolderPath = Paths.get(UPLOAD_DIR, subfolderName);
        return subfolderPath.resolve(fileName);
    }
}
