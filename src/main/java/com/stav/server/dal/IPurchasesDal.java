package com.stav.server.dal;

import com.stav.server.dto.CustomerDTO;
import com.stav.server.dto.PurchaseDTO;
import com.stav.server.entities.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPurchasesDal extends CrudRepository<Purchase, Long> {

    @Query("SELECT NEW com.stav.server.dto.PurchaseDTO (p.id, p.amount, p.date, p.customer.id, p.coupon.id, p.coupon.category.id, p.coupon.company.id) " +
            "FROM Purchase p WHERE p.id= :purchaseId")
    PurchaseDTO findPurchaseById(@Param("purchaseId") long purchaseId);


    @Query("SELECT NEW com.stav.server.dto.PurchaseDTO (p.id, p.amount, p.date, p.customer.id, p.coupon.id, p.coupon.category.id, p.coupon.company.id) " +
            "FROM Purchase p")
    List<PurchaseDTO> findPurchasesByPage(Pageable pageable);


    @Query("SELECT NEW com.stav.server.dto.PurchaseDTO (p.id, p.amount, p.date, p.customer.id, p.coupon.id, p.coupon.category.id, p.coupon.company.id) " +
            "FROM Purchase p " +
            "WHERE p.coupon.category.id= :categoryId")

    List<PurchaseDTO> findPurchasesByCategory(@Param("categoryId") long categoryId, Pageable pageable);

//    @Query("SELECT NEW com.stav.server.dto.PurchaseDTO (p.id, p.amount, p.date, p.customer.id, p.coupon.id, p.coupon.category.id, p.coupon.company.id) " +
//            "FROM Purchase p " +
//            "WHERE p.customer.id= :customerId")
//    CustomerDTO findSinglePurchaseByCustomer(@Param("customerId") long customerId);

    @Query("SELECT NEW com.stav.server.dto.PurchaseDTO (p.id, p.amount, p.date, p.customer.id, p.coupon.id, p.coupon.imageSrc, p.coupon.name, p.coupon.category.id, p.coupon.company.id) " +
            "FROM Purchase p " +
            "WHERE p.customer.id= :customerId")
    List<PurchaseDTO> findPurchasesListByCustomer(@Param("customerId") long customerId, Pageable pagination);

    @Query("SELECT NEW com.stav.server.dto.PurchaseDTO (p.id, p.amount, p.date, p.customer.id, p.coupon.id, p.coupon.imageSrc, p.coupon.name, p.coupon.category.id, p.coupon.company.id) " +
            "FROM Purchase p " +
            "WHERE p.customer.id= :customerId")
    List<PurchaseDTO> findAllCustomerPurchases(@Param("customerId") long customerId);

//    @Query("SELECT NEW com.stav.server.dto.PurchaseDTO (p.id, p.amount, p.date, p.customer.id, p.coupon.id, p.coupon.imageSrc, p.coupon.name, p.coupon.category.id, p.coupon.company.id) " +
//            "FROM Purchase p " +
//            "WHERE p.customer.id= :customerId " +
//            "AND limit limit")
//    List<PurchaseDTO> findPurchasesListByCustomer(@Param("customerId") long customerId, @Param("limit") long limit);
}
