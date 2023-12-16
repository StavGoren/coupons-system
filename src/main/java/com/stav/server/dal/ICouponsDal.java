package com.stav.server.dal;

import com.stav.server.dto.CouponDTO;
import com.stav.server.entities.Coupon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Repository
public interface ICouponsDal extends CrudRepository<Coupon, Long> {

    @Query("SELECT NEW com.stav.server.dto.CouponDTO (coup.id, coup.name, coup.priceInNis, coup.description, " +
            "coup.startDate, coup.endDate, coup.unitsInStock, coup.couponCode, cat.name, cat.id, comp.id, coup.imageSrc) " +
            "FROM Coupon coup LEFT JOIN Category cat ON coup.category.id = cat.id " +
            "LEFT JOIN Company comp ON coup.company.id = comp.id " +
            "WHERE coup.id= :id")
    CouponDTO findCoupon(@Param("id") long id);


    @Query("SELECT new com.stav.server.dto.CouponDTO (coup.id, coup.name, coup.priceInNis, coup.description, " +
            "coup.startDate, coup.endDate, coup.couponCode, cat.name, cat.id, coup.imageSrc)" +
            " FROM Coupon coup " +
            " JOIN Category cat ON coup.category.id = cat.id " +
            " WHERE cat.id= :categoryId")
    List<CouponDTO> findCouponsByCategory(@Param("categoryId") long categoryId, Pageable pageable);

//    @Query("SELECT new com.stav.server.dto.CouponDTO (coup.id, coup.name, coup.priceInNis, coup.description, " +
//            "coup.startDate, coup.endDate, coup.couponCode, cat.name, cat.id, coup.imageSrc)" +
//            " FROM Coupon coup " +
//            " JOIN Category cat ON coup.category.id = cat.id " +
//            " WHERE coup.company.id= :companyId")
//    List<CouponDTO> findCouponsByCompany(@Param("companyId") long companyId,  Pageable pageable);

    @Query("SELECT new com.stav.server.dto.CouponDTO (coup.id, coup.name, coup.priceInNis, coup.description, " +
            "coup.startDate, coup.endDate, coup.couponCode, cat.name, cat.id, coup.imageSrc)" +
            " FROM Coupon coup " +
            " JOIN Category cat ON coup.category.id = cat.id " +
            " WHERE coup.company.id= :companyId")
    List<CouponDTO> findCouponsByCompany(@Param("companyId") long companyId);

    @Query("SELECT new com.stav.server.dto.CouponDTO (coup.id, coup.name, coup.priceInNis, coup.description, " +
            "coup.startDate, coup.endDate, coup.unitsInStock, coup.couponCode, cat.name, cat.id, comp.id, coup.imageSrc) " +
            "FROM Coupon coup LEFT JOIN Category cat ON coup.category.id = cat.id " +
            "LEFT JOIN Company comp ON coup.company.id = comp.id")
    List<CouponDTO> findCouponsListByPage(Pageable page);

    @Query("SELECT new com.stav.server.dto.CouponDTO (coup.id, coup.name, coup.priceInNis, coup.description, " +
            "coup.startDate, coup.endDate, coup.unitsInStock, coup.couponCode, cat.name, cat.id, comp.id, coup.imageSrc) " +
            "FROM Coupon coup LEFT JOIN Category cat ON coup.category.id = cat.id " +
            "LEFT JOIN Company comp ON coup.company.id = comp.id")
    List<CouponDTO> findAllCoupons();

    @Query(value = "SELECT new com.stav.server.dto.CouponDTO (coup.id, coup.name, coup.priceInNis, coup.description, coup.startDate, coup.endDate," +
            "coup.couponCode, cat.name, cat.id, coup.imageSrc) " +
            "FROM Coupon coup JOIN Category cat ON coup.category.id = cat.id " +
            "WHERE coup.priceInNis>= :minPrice AND coup.priceInNis<= :maxPrice")
    List<CouponDTO> findCouponsByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

    @Query("SELECT unitsInStock FROM Coupon WHERE id= :couponId")
    int findAmountOfUnitsInStock(@Param("couponId") long couponId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Coupon coup WHERE coup.endDate< :currentDate")
    void deleteExpiredCoupon(@Param("currentDate") Date currentDate);

    // Deleting coupons of removed category
//    @Modifying
//    @Query("DELETE FROM Coupon coup WHERE coup.category.id= :categoryId")
//    void deleteCouponsByCategoryId(@Param("categoryId") long categoryId);



    //Category cat ON coup.category.id = cat.id " +
    //            " WHERE coup.category.id= :categoryId"
}
