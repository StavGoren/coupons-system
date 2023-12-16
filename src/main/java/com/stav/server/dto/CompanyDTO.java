package com.stav.server.dto;

public class CompanyDTO {

    private long id;
    private String name;
    private String phoneNumber;
    private String address;
    private String imageSrc;


    public CompanyDTO(long id, String name, String imageSrc) {
        this.id = id;
        this.name = name;
        this.imageSrc = imageSrc;
    }

    public CompanyDTO(long id, String name, String phoneNumber, String address, String imageSrc) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", companyName='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }
}
