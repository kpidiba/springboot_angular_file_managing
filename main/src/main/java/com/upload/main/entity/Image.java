package com.upload.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String size;

    public Image(){
        super();
    }

    public Image(Long id, String name, String size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Image(String name, long size) {
        this.name = name;
        this.size = formatSize(size);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String formatSize(long sizeInBytes) {
        final long kiloBytes = 1024;
        final long megaBytes = kiloBytes * 1024;

        if (sizeInBytes > megaBytes) {
            return String.format("%.2f MB", (float) sizeInBytes / megaBytes);
        } else if (sizeInBytes > kiloBytes) {
            return String.format("%.2f KB", (float) sizeInBytes / kiloBytes);
        } else {
            return String.format("%d B", sizeInBytes);
        }
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", name=" + name + ", size=" + size + "]";
    }

}
