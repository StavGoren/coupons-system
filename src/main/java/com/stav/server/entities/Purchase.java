package com.stav.server.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchases")
public class    Purchase {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "AMOUNT", nullable = false)
    private int amount;

    @Column(name = "DATE", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "COUPON_ID")
    private Coupon coupon;

    public Purchase() {

    }

    public Purchase(long id, int amount, Date date, Customer customer, Coupon coupon) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.customer = customer;
        this.coupon = coupon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", customer=" + customer +
                ", coupon=" + coupon +
                '}';
    }
}