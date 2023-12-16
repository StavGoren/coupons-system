package com.stav.server.logic;

import com.stav.server.consts.Constants;
import com.stav.server.dal.ICustomersDal;
import com.stav.server.dto.CustomerDTO;
import com.stav.server.entities.Customer;
import com.stav.server.enums.ErrorType;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import com.stav.server.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stav.server.utils.JWTUtils.*;


@Service
public class CustomersLogic {
    private ICustomersDal customersDal;

    @Autowired
    private UsersLogic usersLogic;

    @Autowired
    public CustomersLogic(ICustomersDal customersDal) {
        this.customersDal = customersDal;
    }

    public void createCustomer(Customer customer) throws ServerException {
        validateCustomer(customer);
        try {
            customersDal.save(customer);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to create an account", e);
        }
    }

    public void updateCustomer(Customer customer, String authorization) throws ServerException {
        Long registeredUserId = getIdByToken(authorization);
        validatePermissionByUserType(UserType.Customer, authorization);

        if (registeredUserId != customer.getUser().getId()) {
            throw new ServerException(ErrorType.NOT_LOGGED_IN, " user ID didn't match for 'updateCustomer()'");
        }
        validateCustomer(customer);
        customer.setId(registeredUserId);
        try {
            customersDal.save(customer);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'updateCustomer()'", e);
        }
    }

    public CustomerDTO getCustomer(long id, String authorization) throws ServerException {
        long registeredCustomerId = JWTUtils.getIdByToken(authorization);

        if (id != registeredCustomerId) {
            validateUserPermission(id, authorization);
        }
        try {
            Customer customer = customersDal.findById(id).get();
            return extractCustomerDTO(customer);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to retrieve customer with ID " + id, e);
        }
    }


    public List<CustomerDTO> getCustomersByPage(int pageNumber, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);

        try {
            return customersDal.findAllCustomersByPage(pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to retrieve customers with 'getCustomersByPage()'", e);
        }
    }

    public void removeCustomer(long id, String authorization) throws ServerException {
        validateUserPermission(id, authorization);
        try {
            customersDal.deleteById(id);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'removeCustomer()'", e);
        }
    }

    Customer getCustomerEntity(long id) throws ServerException {
        try {
            return customersDal.findById(id).get();
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCustomerEntity()'", e);
        }
    }


    /*********************
     * ValidationsUtils and extract
     *********************/

    private CustomerDTO extractCustomerDTO(Customer customer) throws ServerException {
        long id = customer.getId();
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String phoneNumber = customer.getPhoneNumber();
        String userName = customer.getUser().getUserName();
        String address = customer.getAddress();

        CustomerDTO customerDTO = new CustomerDTO(id, firstName, lastName, phoneNumber, userName, address);
        return customerDTO;
    }


    private void validateCustomer(Customer customer) throws ServerException {
        usersLogic.validateUser(customer.getUser());
        validateName(customer.getFirstName());
        validateName(customer.getLastName());
        validatePhoneNumber(customer.getPhoneNumber());
        validateAddress(customer.getAddress());
    }

    private void validateAddress(String address) throws ServerException {
        if (address.isBlank()) {
            throw new ServerException(ErrorType.EMPTY_FIELD, " address is blank");
        }
        if (address.length() < 2) {
            throw new ServerException(ErrorType.INVALID_ADDRESS_LENGTH, " address too short");
        }
    }

    private void validatePhoneNumber(String phoneNumber) throws ServerException {


        if (!phoneNumber.matches(".*[0-9]-.*")) {
            throw new ServerException(ErrorType.INVALID_CHARACTER_IN_PHONE_NUMBER, " phone number doesn't match required regex");
        }
        if (phoneNumber.contains(" ")) {
            throw new ServerException(ErrorType.CONTAINS_WHITE_SPACE, " user entered whitespace in phone number");
        }
        if (phoneNumber.length() < 9) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER_LENGTH, " phone number too short");
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
