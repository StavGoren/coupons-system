package com.stav.server.dto;

import java.util.Date;

public class CouponDTO {
    private long id;
    private String name;
    private float priceInNis;
    private String description;
    private Date startDate;
    private Date endDate;
    private int unitsInStock;
    private String couponCode;
    private String categoryName;
    private long categoryId;
    private long companyId;
    private String imageSrc;



    public CouponDTO(long id, String name, float priceInNis, Date startDate, Date endDate, int unitsInStock, long companyId, String imageSrc) {
        this.id = id;
        this.name = name;
        this.priceInNis = priceInNis;
        this.startDate = startDate;
        this.endDate = endDate;
        this.unitsInStock = unitsInStock;
        this.companyId = companyId;
        this.imageSrc = imageSrc;
    }

    public CouponDTO(long id, String name, float priceInNis, String description, Date startDate, Date endDate,
                     String couponCode, String categoryName, long categoryId, String imageSrc) {
        this.id = id;
        this.name = name;
        this.priceInNis = priceInNis;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.couponCode = couponCode;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.imageSrc = imageSrc;
    }

    public CouponDTO(long id, String name, float priceInNis, String description, Date startDate, Date endDate,
                     int unitsInStock, String couponCode, String categoryName, long categoryId, long companyId, String imageSrc) {

        this(id, name, priceInNis, description, startDate, endDate, couponCode, categoryName, categoryId,  imageSrc);
        this.unitsInStock = unitsInStock;
        this.categoryName = categoryName;
        this.companyId = companyId;
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

    public float getPriceInNis() {
        return priceInNis;
    }

    public void setPriceInNis(float priceInNis) {
        this.priceInNis = priceInNis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "CouponDTO{" +
                "couponId=" + id +
                ", couponName='" + name + '\'' +
                ", priceInNis=" + priceInNis +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", unitsInStock=" + unitsInStock +
                ", couponCode='" + couponCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryId=" + categoryId +
                ", companyId=" + companyId +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }
}
