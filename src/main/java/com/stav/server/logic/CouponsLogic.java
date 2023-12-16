package com.stav.server.logic;


import com.stav.server.consts.Constants;
import com.stav.server.dal.ICouponsDal;
import com.stav.server.dto.CouponDTO;
import com.stav.server.entities.Coupon;
import com.stav.server.entities.User;
import com.stav.server.enums.ErrorType;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.stav.server.utils.JWTUtils.*;


@Service
public class CouponsLogic {
    private ICouponsDal couponsDal;

    @Autowired
    private CategoriesLogic categoriesLogic;

    @Autowired
    private CompaniesLogic companiesLogic;

    @Autowired
    private UsersLogic usersLogic;

    @Autowired
    public CouponsLogic(ICouponsDal couponsDal) {
        this.couponsDal = couponsDal;
    }

    /*******************************
     Public methods and actions
     ******************************/


    public void createCoupon(String authorization, Coupon coupon) throws ServerException {

        Long userId = getIdByToken(authorization);
        User user = usersLogic.getUserEntity(userId);
        validatePermissionByUserType(UserType.Company, authorization);

        validateCoupon(coupon);
        coupon.setUserCreatedCoupon(user);
        coupon.setCompany(user.getCompany());
        String couponCodeToUpperCase = coupon.getCouponCode().toUpperCase();
        coupon.setCouponCode(couponCodeToUpperCase);
        System.out.println("new coupon: " + coupon);

        try {
            couponsDal.save(coupon);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing createCoupon()", e);
        }
    }

    public void updateCoupon(Coupon coupon, String authorization) throws ServerException {
        Long userId = getIdByToken(authorization);
        User user = usersLogic.getUserEntity(userId);
        Coupon coupon1 = couponsDal.findById(coupon.getId()).get();
        Long companyIdOfCoupon = coupon1.getCompany().getId();

        if (user.getUserType().equals(UserType.Customer)) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, " user of type '" + user.getUserType() + "' is  not authorized");
        }

        if (user.getUserType().equals(UserType.Company)) {
            if (user.getCompany().getId() != companyIdOfCoupon) {
                throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " invalid company ID for 'updateCoupon()'");
            }
        }

        validateCoupon(coupon);
        coupon.setCompany(coupon1.getCompany());
        coupon.setUserCreatedCoupon(coupon1.getUserCreatedCoupon());
        String couponCodeToUpperCase = coupon.getCouponCode().toUpperCase();
        coupon.setCouponCode(couponCodeToUpperCase);

        try {
            couponsDal.save(coupon);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing updateCoupon()", e);
        }
    }

    public CouponDTO getCoupon(long id) throws ServerException {
        try {
            return couponsDal.findCoupon(id);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing getCoupon()", e);
        }
    }

    public List<CouponDTO> getAllCouponsByPage(int pageNumber) throws ServerException {
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return couponsDal.findCouponsListByPage(pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " error occurred while using 'getAllCouponsByPage()'", e);
        }
    }

    public List<CouponDTO> getAllCoupons() throws ServerException {
        try {
            return couponsDal.findAllCoupons();
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " error occurred while using 'getAllCoupons()'", e);
        }
    }

    public List<CouponDTO> getCouponsByCategory(long categoryId, int pageNumber) throws ServerException {
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return couponsDal.findCouponsByCategory(categoryId, pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCouponsByCategory()'", e);
        }
    }

    public List<CouponDTO> getCouponsByCompany(long companyId) throws ServerException {
        try {
            return couponsDal.findCouponsByCompany(companyId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCouponsByCategory()'", e);
        }
    }

    public List<CouponDTO> getCouponsByPriceRange(int minPrice, int maxPrice, int pageNumber) throws ServerException {
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return couponsDal.findCouponsByPriceRange(minPrice, maxPrice, pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCouponsByPriceRange()'", e);
        }
    }

    public void deleteCoupon(long id, String authorization) throws ServerException {
        Long userId = getIdByToken(authorization);
        User user = usersLogic.getUserEntity(userId);
        Coupon coupon = couponsDal.findById(id).get();

        if (user.getUserType().equals(UserType.Customer)) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, " user of type '" + user.getUserType() + "' is  not authorized");
        }

        if (user.getUserType().equals(UserType.Company)) {
            if (user.getCompany().getId() != coupon.getCompany().getId()) {
                throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " invalid company ID for 'deleteCoupon()'");
            }
        }
        try {
            couponsDal.deleteById(id);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'deleteCoupon()'", e);
        }
    }

    public void deleteExpiredCoupon() throws ServerException {
        Date todayDate = Calendar.getInstance(Calendar.getInstance().getTimeZone()).getTime();
        try {
//            couponsDal.deleteExpiredCoupon(todayDate);
            for (Coupon coupon : couponsDal.findAll()) {
                if (coupon.getEndDate().equals(todayDate) ||
                        todayDate.after(coupon.getEndDate())) {
                    couponsDal.deleteById(coupon.getId());
                }
            }
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'deleteExpiredCoupon()'", e);
        }
    }


    int getAmountOfUnitsInStock(long couponId) throws ServerException {
        try {
            return couponsDal.findAmountOfUnitsInStock(couponId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getAmountOfUnitsInStock()'", e);
        }
    }

    Coupon getCouponEntity(long id) throws ServerException {
        try {
            return couponsDal.findById(id).get();
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCouponEntity()'", e);
        }
    }


    /*********************************
     * Private methods (validations)
     *********************************/

    private void validateCoupon(Coupon coupon) throws ServerException {
        validateName(coupon.getName());
        validatePrice(coupon.getPriceInNis());
        validateDescription(coupon.getDescription());
        validateDates(coupon.getStartDate(), coupon.getEndDate());
        validateCouponCode(coupon.getCouponCode());
    }


    private void validateCouponCode(String couponNumber) throws ServerException {
        for (int i = 0; i < couponNumber.length(); i++) {
            if (!Character.isLetterOrDigit(couponNumber.charAt(i))) {
                throw new ServerException(ErrorType.INVALID_COUPON_CHARACTERS, " user entered an invalid character in coupon code");
            }
        }
    }

    private void validateDates(Date startDate, Date endDate) throws ServerException {
        LocalDate currentTime = LocalDate.now();
        Date todayDate = java.util.Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (startDate.before(todayDate) || endDate.before(todayDate)) {
            throw new ServerException(ErrorType.INVALID_DATE, " user entered a date from the past");
        }
        if (endDate.before(startDate)) {
            throw new ServerException(ErrorType.INVALID_END_DATE, " dates selected are in the wrong order");
        }
    }


    private void validateDescription(String description) throws ServerException {
        if (description.length() < 10) {
            throw new ServerException(ErrorType.INVALID_DESCRIPTION_LENGTH, " description too short");
        }
        if (description.length() > 400) {
            throw new ServerException(ErrorType.INVALID_DESCRIPTION_LENGTH, " description is longer than required length");
        }
    }

    private void validatePrice(float price) throws ServerException {
        if (price < 1) {
            throw new ServerException(ErrorType.INVALID_PRICE, " price is lower than 1");
        }
    }

    private void validateName(String name) throws ServerException {
        if (name.isBlank()) {
            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too short");
        }
        if (name.length() > 40) {
            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too long");
        }
    }

}