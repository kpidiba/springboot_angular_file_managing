package com.upload.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachementResponse {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
