package com.stav.server.dto;

public class CategoryDTO {
    private long id;
    private String name;
    private String imageSrc;


    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO(long id, String name, String imageSrc) {
        this.id = id;
        this.name = name;
        this.imageSrc = imageSrc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }
}
