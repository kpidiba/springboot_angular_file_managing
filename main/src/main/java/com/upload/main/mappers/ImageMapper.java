package com.upload.main.mappers;

import com.upload.main.dto.ImageDTO;
import com.upload.main.entity.Image;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;

public class ImageMapper {
    private static final String UPLOAD_DIR = new File("src/main/resources/static/").getAbsolutePath();

    public static ImageDTO maptoDto(Image image) {
        String name = image.getName();
        return new ImageDTO(image.getId(),name, getImageData(name));
    }

    public static byte[] getImageData(String name) {
        Path imagePath = Paths.get(UPLOAD_DIR, "images", name);
        try {
            return Files.readAllBytes(imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
