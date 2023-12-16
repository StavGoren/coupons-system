package com.stav.server.logic;

import com.stav.server.consts.Constants;
import com.stav.server.dal.ICompaniesDal;
import com.stav.server.dto.CompanyDTO;
import com.stav.server.entities.Company;
import com.stav.server.entities.User;
import com.stav.server.enums.ErrorType;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.stav.server.utils.JWTUtils.getIdByToken;
import static com.stav.server.utils.JWTUtils.validatePermissionByUserType;

@Service
public class CompaniesLogic {
    private ICompaniesDal companiesDal;
    @Autowired
    UsersLogic usersLogic;

    @Autowired
    public CompaniesLogic(ICompaniesDal companiesDal) {
        this.companiesDal = companiesDal;
    }


    /*******************************
     Public methods and actions
     *******************************/

    public void createCompany(Company company, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        validateCompany(company);

        try {
            companiesDal.save(company);
        } catch (
                Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'createCompany()'", e);
        }
    }

    public void updateCompany(Company company, String authorization) throws ServerException {
        Long userId = getIdByToken(authorization);
        User user = usersLogic.getUserEntity(userId);

        if(user.getUserType().equals(UserType.Customer)) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE, " user of type customer tried to use 'updateCompany()'");
        }

        if(user.getUserType().equals(UserType.Company)){
            if(user.getCompany().getId() != company.getId()){
                throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " invalid company ID for 'updateCompany()'");
            }
        }

        validateCompany(company);
        try {
            companiesDal.save(company);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'updateCompany()'", e);
        }
    }

    public CompanyDTO getCompany(long id) throws ServerException {
        try {
            return companiesDal.findCompanyById(id);
        }catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCompany()'", e);
        }
    }

    public List<CompanyDTO> getCompaniesByPage(int pageNumber) throws ServerException {
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try{
            return companiesDal.findCompaniesByPage(pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCompaniesByPage()'", e);
        }
    }

    public List<CompanyDTO> getAllCompanies() throws ServerException{
        try {
            return companiesDal.findAllCompanies();
        } catch (Exception e){
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getAllCompanies()'", e);
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteCompany(int id, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        try {
            companiesDal.deleteById((long) id);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'deleteCompany()'", e);
        }
    }


    /********************
     ValidationsUtils
     *********************/

    private void validateCompany(Company company) throws ServerException {
        validateName(company.getName());
        validatePhoneNumber(company.getPhoneNumber());
        validateAddress(company.getAddress());
    }

    private void validateAddress(String address) throws ServerException {
        if(address.isBlank()) {
            throw new ServerException(ErrorType.EMPTY_FIELD, " address is blank");
        }
        if (address.length() < 2) {
            throw new ServerException(ErrorType.INVALID_ADDRESS_LENGTH, " address too short");
        }
    }

    private void validatePhoneNumber(String phoneNumber) throws ServerException {

        if (phoneNumber.length() < 9) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER_LENGTH, " phone number too short");
        }
        if(phoneNumber.contains(" ")) {
            throw new ServerException(ErrorType.CONTAINS_WHITE_SPACE, " user entered whitespace in phone number");
        }
        if(!phoneNumber.matches(".*[0-9]-.*")){
            throw new ServerException(ErrorType.INVALID_CHARACTER_IN_PHONE_NUMBER, " phone number doesn't match required regex");
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