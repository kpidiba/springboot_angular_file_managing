package com.upload.main.services.attachements;

import java.util.List;

import com.upload.main.dto.ImageDTO;
import com.upload.main.entity.Image;

public interface ImageService {

    Image saveImage(Image image);

    void deleteImage(Image image);

    List<ImageDTO> getAll();

    Image findImageByName(String name);

    Image findImageById(Long id);

}
