package com.stav.server.controllers;


import com.stav.server.dto.CouponDTO;
import com.stav.server.entities.Coupon;
import com.stav.server.exceptions.ServerException;
import com.stav.server.logic.CouponsLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private CouponsLogic couponsLogic;

    @Autowired
    public CouponsController(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }


    @PostMapping
    public void createCoupon(@RequestHeader String authorization, @RequestBody Coupon coupon) throws ServerException {
        couponsLogic.createCoupon(authorization, coupon);
    }

    @PutMapping
    public void updateCoupon(@RequestHeader String authorization, @RequestBody Coupon coupon) throws ServerException {

        couponsLogic.updateCoupon(coupon, authorization);
    }

    @GetMapping("{couponId}")
    public CouponDTO getCoupon(@PathVariable("couponId") long id) throws ServerException {
        return couponsLogic.getCoupon(id);
    }

    @GetMapping("/byCategoryId")
    public List<CouponDTO> getCouponsByCategory(@RequestParam("categoryId") long categoryId, int pageNumber) throws ServerException {
        return couponsLogic.getCouponsByCategory(categoryId, pageNumber);
    }

    @GetMapping("/byPrice")
    public List<CouponDTO> getCouponsByPriceRange(@RequestParam("minPrice") int minPrice, @RequestParam("maxPrice") int maxPrice, int pageNumber) throws ServerException{
        return couponsLogic.getCouponsByPriceRange(minPrice, maxPrice, pageNumber);
    }

//    @GetMapping("/byCompany")
//    public List<CouponDTO> getCouponsByCompany(@RequestParam("companyId") long companyId, int pageNumber) throws ServerException {
//        return couponsLogic.getCouponsByCompany(companyId, pageNumber);
//    }

    @GetMapping("/byCompany")
    public List<CouponDTO> getCouponsByCompany(@RequestParam("companyId") long companyId) throws ServerException {
        return couponsLogic.getCouponsByCompany(companyId);
    }

    @GetMapping("/byPage")
    public List<CouponDTO> getAllCouponsByPage(@RequestParam("pageNumber") int pageNumber) throws ServerException{
        return couponsLogic.getAllCouponsByPage(pageNumber);
    }

    @GetMapping
    public List<CouponDTO> getAllCoupons() throws ServerException{
        return couponsLogic.getAllCoupons();
    }

    @DeleteMapping("{couponId}")
    public void deleteCoupon(@PathVariable("couponId") long id, @RequestHeader String authorization) throws ServerException {
        couponsLogic.deleteCoupon(id, authorization);
    }


}
