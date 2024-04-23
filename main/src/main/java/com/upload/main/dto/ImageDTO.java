package com.upload.main.dto;


public class ImageDTO {
    private Long id;
    private String name;
    private byte[] file;

    
    public ImageDTO(Long id, String name, byte[] file) {
        this.id = id;
        this.name = name;
        this.file = file;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }
    public void setFile(byte[] file) {
        this.file = file;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    
}
