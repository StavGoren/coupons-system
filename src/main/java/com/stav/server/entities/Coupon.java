package com.stav.server.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "PRICE_IN_NIS", nullable = false)
    private float priceInNis;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "COUPON_CODE", unique = true, nullable = false)
    private String couponCode;

    @Column(name = "UNITS_IN_STOCK", nullable = false)
    private int unitsInStock;

    @Column(name = "IMAGESRC")
    private String imageSrc;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "USER_CREATED_COUPON", nullable = false)
    private User userCreatedCoupon;


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<Purchase> purchasesList;

    public Coupon(){
    }

    public Coupon(long id, String name, float priceInNis, String description, Date startDate, Date endDate, String couponCode, int unitsInStock,
                  String imageSrc, Category category, Company company, User userCreatedCoupon) {
        this.id = id;
        this.name = name;
        this.priceInNis = priceInNis;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.couponCode = couponCode;
        this.unitsInStock = unitsInStock;
        this.imageSrc = imageSrc;
        this.category = category;
        this.company = company;
        this.userCreatedCoupon = userCreatedCoupon;
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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUserCreatedCoupon() {
        return userCreatedCoupon;
    }

    public void setUserCreatedCoupon(User userCreatedCoupon) {
        this.userCreatedCoupon = userCreatedCoupon;
    }

//    public List<Purchase> getPurchasesList() {
//        return purchasesList;
//    }

//    public void setPurchasesList(List<Purchase> purchasesList) {
//        this.purchasesList = purchasesList;
//    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceInNis=" + priceInNis +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", couponCode='" + couponCode + '\'' +
                ", unitsInStock=" + unitsInStock +
                ", imageSrc='" + imageSrc + '\'' +
                ", category=" + category +
                ", company=" + company +
                ", user=" + userCreatedCoupon +
//                ", purchasesList=" + purchasesList +
                '}';
    }
}

