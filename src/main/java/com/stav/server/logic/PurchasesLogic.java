package com.stav.server.logic;

import com.stav.server.consts.Constants;
import com.stav.server.dal.IPurchasesDal;
import com.stav.server.dto.PurchaseDTO;
import com.stav.server.entities.Coupon;
import com.stav.server.entities.Customer;
import com.stav.server.entities.Purchase;
import com.stav.server.entities.User;
import com.stav.server.enums.ErrorType;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.stav.server.utils.JWTUtils.*;

@Service
public class PurchasesLogic {
    private IPurchasesDal purchasesDal;

    @Autowired
    private CouponsLogic couponsLogic;

    @Autowired
    private CustomersLogic customersLogic;

    @Autowired
    private UsersLogic usersLogic;

    @Autowired
    public PurchasesLogic(IPurchasesDal purchasesDal) {
        this.purchasesDal = purchasesDal;
    }

    /**********************
     * Public methods
     **********************/

    // Might need to add login permission for the purpose of the project
    @Transactional(rollbackOn = {Exception.class})
    public void createPurchase(Purchase purchase, String authorization) throws ServerException {
        Long userId = getIdByToken(authorization);

        //Maybe add customer authorization
        Customer customer = customersLogic.getCustomerEntity(userId);
        Coupon coupon = couponsLogic.getCouponEntity(purchase.getCoupon().getId());
        int currentUnitsInStock = coupon.getUnitsInStock();

        validatePurchase(purchase, coupon);

        purchase.setDate(getCuurentDate());
        purchase.setCustomer(customer);
        purchase.getCoupon().setPriceInNis(coupon.getPriceInNis());


        try {
            purchasesDal.save(purchase);
            coupon.setUnitsInStock(currentUnitsInStock - purchase.getAmount());
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, ", an error occurred while trying to invoke 'createPurchase()'", e);
        }
    }

    public PurchaseDTO getPurchase(long purchaseId, String authorization) throws ServerException {
        long registeredId = getIdByToken(authorization);
        User registeredUser = usersLogic.getUserEntity(registeredId);
        PurchaseDTO purchase = purchasesDal.findPurchaseById(purchaseId);

        if (registeredUser.getUserType() != UserType.Admin) {
            if (registeredUser.getUserType() == UserType.Company) {
                if (registeredUser.getCompany().getId() != purchase.getCompanyId()) {
                    throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " invalid company ID for retrieving purchase data");
                }
            } else if (registeredId != purchase.getCustomerId()) {
                throw new ServerException(ErrorType.NOT_LOGGED_IN, " invalid customer ID for retrieving purchase data");
            }
        }
        try {
            return purchase;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while trying to invoke 'getPurchase()'", e);
        }
    }

    public List<PurchaseDTO> getPurchasesByPage(int pageNumber, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return purchasesDal.findPurchasesByPage(pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while trying to invoke 'getPurchasesByPage()'", e);
        }
    }

    public List<PurchaseDTO> getPurchasesByCategory(long categoryId, int pageNumber, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return purchasesDal.findPurchasesByCategory(categoryId, pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to retrieve coupons while trying to invoke 'getPurchasesByCategory()'", e);
        }
    }

//    public CustomerDTO getSinglePurchaseByCustomer(long customerId, String authorization) throws ServerException {
//        validateUserPermission(customerId, authorization);
//        try{
//            return purchasesDal.findSinglePurchaseByCustomer(customerId);
//        } catch (Exception e) {
//            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to retrieve customer's purchase while using 'getSinglePurchaseByCustomer()'", e);
//        }
//    }

    //TODO Add admin permission
    public List<PurchaseDTO> getPurchasesOfCustomerByPage(long customerId, int pageNumber, String authorization) throws ServerException {
        validateUserPermission(customerId, authorization);
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return purchasesDal.findPurchasesListByCustomer(customerId, pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to retrieve customer's purchases while using 'getPurchasesListByCustomer()'", e);
        }
    }

    public List<PurchaseDTO> getAllCustomerPurchases(long customerId, String authorization) throws ServerException {
        System.out.println("Thy user: " + getUserFromToken(authorization));
        try {
            return purchasesDal.findAllCustomerPurchases(customerId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to retrieve customer's purchases while using 'getPurchasesListByCustomer()'", e);
        }
    }

    public void removePurchase(long id, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        try {
            purchasesDal.deleteById(id);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while trying to invoke 'removePurchase()'", e);
        }
    }

    /*********************
     * Validation
     **********************/

//    private void validateUserDetails
    private void validatePurchase(Purchase purchase, Coupon coupon) throws ServerException {
        validateAmountOfUnitsInStock(purchase, coupon);
        validateExpiredCoupon(coupon.getEndDate());
//        validatePriceInPurchase(purchase, coupon);

    }

    private void validateExpiredCoupon(Date couponEndDate) throws ServerException {
        LocalDate currentTime = LocalDate.now();
        Date todayDate = Date.from(currentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (todayDate.after(couponEndDate)) {
            throw new ServerException(ErrorType.EXPIRED_COUPON, " customer tried to buy an expired coupon");
        }
    }

    private void validateAmountOfUnitsInStock(Purchase purchase, Coupon coupon) throws ServerException {
        int amountOfCouponsPurchased = purchase.getAmount();
        int amountOfCouponsAvailable = couponsLogic.getAmountOfUnitsInStock(coupon.getId());

        if (amountOfCouponsPurchased < 0) {
            throw new ServerException(ErrorType.OUT_OF_STOCK, " invalid amount of coupons");
        }
        if (amountOfCouponsPurchased > amountOfCouponsAvailable) {
            throw new ServerException(ErrorType.OUT_OF_STOCK, " amount of wanted coupons is greater than the amount available");
        }

    }

    private Date getCuurentDate() throws ServerException {
        return Calendar.getInstance(Calendar.getInstance().getTimeZone()).getTime();
//        Date date = new Date();
//        long dateInMillis = date.getTime();
//        Date todayDate = new Date(dateInMillis);
//        return todayDate;
    }

//    private void validatePriceInPurchase(Purchase purchase, CouponDTO coupon) throws ServerException {
//        float purchasedCouponPrice = purchase.getCoupon().getPriceInNis();
//        float couponPrice = coupon.getPriceInNis();
//        if (couponPrice != purchasedCouponPrice) {
//            //Make sure customer didn't try to change the price. If they do, their ID will be printed to the console
//            throw new ServerException(ErrorType.GENERAL_ERROR, " purchased coupon price doesn't match the original price " + purchase.getCustomer().getUser().getId());
//        }
//    }
}