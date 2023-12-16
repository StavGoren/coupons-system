package com.stav.server.controllers;

import com.stav.server.dto.CustomerDTO;
import com.stav.server.entities.Customer;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import com.stav.server.logic.CustomersLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private CustomersLogic customersLogic;


    @Autowired
    public CustomersController(CustomersLogic customersLogic) {
        this.customersLogic = customersLogic;
    }


    @PostMapping
    public void createCustomer(@RequestBody Customer customer) throws ServerException {
        customer.getUser().setUserType(UserType.Customer);
        System.out.println(customer);
        customersLogic.createCustomer(customer);
    }

    @PutMapping
    public void updateCustomer(@RequestBody Customer customer, @RequestHeader String authorization) throws ServerException {
        customer.getUser().setUserType(UserType.Customer);
        customersLogic.updateCustomer(customer, authorization);
    }

    @GetMapping("{customerId}")
    public CustomerDTO getCustomer(@PathVariable("customerId") long id, @RequestHeader String authorization) throws ServerException{
        return customersLogic.getCustomer(id, authorization);
    }

    @GetMapping("/byPage")
    public List<CustomerDTO> getCustomersByPage(@RequestParam("pageNumber") int pageNumber, @RequestHeader String authorization) throws ServerException{
        return customersLogic.getCustomersByPage(pageNumber, authorization);
    }


    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") int id, @RequestHeader String authorization) throws ServerException{
        customersLogic.removeCustomer(id, authorization);
    }
}
