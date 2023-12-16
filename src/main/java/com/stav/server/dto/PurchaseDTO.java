package com.stav.server.dto;

import java.util.Date;

public class PurchaseDTO {
    private long purchaseId;
    private int amount;
    private Date date;
    private long customerId;
    private long couponId;
    private String imageSrc;
    private String couponName;
    private long categoryId;
    private long companyId;

    public PurchaseDTO(long purchaseId, int amount, Date date, long customerId, long couponId, String imageSrc, String couponName, long categoryId, long companyId) {
        this.purchaseId = purchaseId;
        this.amount = amount;
        this.date = date;
        this.customerId = customerId;
        this.couponId = couponId;
        this.imageSrc = imageSrc;
        this.couponName = couponName;
        this.categoryId = categoryId;
        this.companyId = companyId;
    }

    public PurchaseDTO(long purchaseId, int amount, Date date, long customerId, long couponId, long categoryId, long companyId) {
        this.purchaseId = purchaseId;
        this.amount = amount;
        this.date = date;
        this.customerId = customerId;
        this.couponId = couponId;
        this.categoryId = categoryId;
        this.companyId = companyId;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
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

    @Override
    public String toString() {
        return "PurchaseDTO{" +
                "purchaseId=" + purchaseId +
                ", amount=" + amount +
                ", date=" + date +
                ", customerId=" + customerId +
                ", couponId=" + couponId +
                ", imageSrc='" + imageSrc + '\'' +
                ", couponName='" + couponName + '\'' +
                ", categoryId=" + categoryId +
                ", companyId=" + companyId +
                '}';
    }
}
