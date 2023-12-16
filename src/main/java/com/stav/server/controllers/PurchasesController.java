package com.stav.server.controllers;

import com.stav.server.dto.PurchaseDTO;
import com.stav.server.entities.Purchase;
import com.stav.server.exceptions.ServerException;
import com.stav.server.logic.CouponsLogic;
import com.stav.server.logic.PurchasesLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private PurchasesLogic purchasesLogic;
    private CouponsLogic couponsLogic;

    @Autowired
    public PurchasesController(PurchasesLogic purchasesLogic, CouponsLogic couponsLogic) {
        this.purchasesLogic = purchasesLogic;
        this.couponsLogic = couponsLogic;
    }

    @PostMapping
    public void createPurchase(@RequestBody Purchase purchase, @RequestHeader String authorization) throws ServerException {
        purchasesLogic.createPurchase(purchase, authorization);
    }

    @GetMapping("{purchaseId}")
    public PurchaseDTO getPurchaseById(@PathVariable("purchaseId") int id, @RequestHeader String authorization) throws ServerException{
        return purchasesLogic.getPurchase(id, authorization);
    }

    @GetMapping("/byPage")
    public List<PurchaseDTO> getPurchasesByPage(@RequestParam("pageNumber") int pageNumber, @RequestHeader String authorization) throws ServerException{
        return purchasesLogic.getPurchasesByPage(pageNumber, authorization);
    }

    @GetMapping("/byCategory")
    List<PurchaseDTO> getPurchasesByCategory(@RequestParam("categoryId") long categoryId, int pageNumber, @RequestHeader String authorization) throws ServerException {
        return purchasesLogic.getPurchasesByCategory(categoryId, pageNumber, authorization);
    }


    @GetMapping("/byCustomer")
    public List<PurchaseDTO> getPurchasesOfCustomerByPage(@RequestParam("customerId") long customerId, int pageNumber, @RequestHeader String authorization) throws ServerException {
        return purchasesLogic.getPurchasesOfCustomerByPage(customerId, pageNumber, authorization);
    }

    @GetMapping("/byCustomerId")
    public List<PurchaseDTO> getAllCustomerPurchases(@RequestParam("customerId") long customerId, @RequestHeader String authorization) throws ServerException {
        return purchasesLogic.getAllCustomerPurchases(customerId, authorization);
    }

    @DeleteMapping("{purchaseId}")
    public void deletePurchase(@PathVariable("purchaseId") long id, @RequestHeader String authorization) throws ServerException{
        purchasesLogic.removePurchase(id, authorization);
    }
}
