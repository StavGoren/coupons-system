package com.stav.server.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stav.server.consts.Constants;
import com.stav.server.dal.IUsersDal;
import com.stav.server.dto.SuccessfulLoginDetails;
import com.stav.server.dto.UserDTO;
import com.stav.server.dto.UserLoginDetails;
import com.stav.server.entities.User;
import com.stav.server.enums.ErrorType;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import com.stav.server.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.stav.server.utils.JWTUtils.*;


@Service
public class UsersLogic {
    private IUsersDal usersDal;

    @Autowired
    private CompaniesLogic companiesLogic;

    @Autowired
    public UsersLogic(IUsersDal usersDal) {
        this.usersDal = usersDal;
    }


    public void createUser(User user) throws ServerException {
        validateUser(user);

        try {
            usersDal.save(user);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'createUser()'", e);
        }
    }

    public void updateUser(User user, String authorization) throws ServerException {
        // Login filter already checks if the user is logged in
        validateUser(user);
        try {
            usersDal.save(user);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to update details", e);
        }
    }

    public UserDTO getUser(long id, String authorization) throws ServerException {
        long registeredUserId = JWTUtils.getIdByToken(authorization);

        if (registeredUserId != id) {
            validatePermissionByUserType(UserType.Admin, authorization);
        }

        try {
            User user = usersDal.findById(id).get();
            return extractUserDto(user);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while trying to invoke 'getUser()'", e);
        }

    }

    public List<UserDTO> getUsersByPage(int pageNumber, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return usersDal.findUsersByPage(pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while trying to use 'getUsersByPage()'", e);
        }
    }


    public List<UserDTO> getUsersByCompanyId(long companyId, int pageNumber, String authorization) throws ServerException {
        Long userId = getIdByToken(authorization);
        User user = usersDal.findById(userId).get();

        if (user.getUserType().equals(UserType.Company)) {
            if (companyId != user.getCompany().getId()) {
                throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " unauthorized");
            }
        }
        if (user.getUserType().equals(UserType.Customer)) {
            throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " unauthorized");
        }

        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return usersDal.findUsersByCompanyId(companyId, pagination);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while trying to use 'getUsersByCompanyId()'", e);
        }
    }

    public void removeUser(long id, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);

        try {
            usersDal.deleteById(id);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to delete user", e);
        }
    }


    public String login(UserLoginDetails userLoginDetails) throws ServerException, JsonProcessingException {
        SuccessfulLoginDetails successfulLoginDetails = usersDal.login(userLoginDetails.getUserName(), userLoginDetails.getPassword());
        if (successfulLoginDetails == null) {
            throw new ServerException(ErrorType.LOGIN_FAILED, " user entered wrong details during 'login()");
        }
        String token = JWTUtils.createJWT(successfulLoginDetails);
        return token;
    }

    User getUserEntity(long id) throws ServerException {
        try {
            return usersDal.findById(id).get();
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to retrieve Entity of user", e);
        }
    }

    /**************************
     * Private/protected methods & validations
     ***************************/

    private UserDTO extractUserDto(User user) throws ServerException {
        long id = user.getId();
        String userName = user.getUserName();
        UserType userType = user.getUserType();
        String companyName = null;
        if (user.getCompany() != null) {
            companyName = user.getCompany().getName();
        }

        UserDTO userDTO = new UserDTO(id, userName, userType, companyName);
        return userDTO;
    }

    void validateUser(User user) throws ServerException {
        validateUserName(user.getUserName());
        validatePassword(user.getPassword());
    }

    private void validatePassword(String password) throws ServerException {
        if (password.isBlank()) {
            throw new ServerException(ErrorType.EMPTY_FIELD, " user didn't enter a password");
        }
        if (password.contains(" ")) {
            throw new ServerException(ErrorType.CONTAINS_WHITE_SPACE, " user entered whitespace");
        }
        if (password.contains("{") || password.contains("}") ||

                password.contains("(") || password.contains(")") ||

                password.contains("[") || password.contains("]") ||

                password.contains("'") || password.contains(",") ||

                password.contains(".") || password.contains("/") ||

                password.contains("\\")) {
            throw new ServerException(ErrorType.INVALID_PASSWORD_CHARACTERS, " please enter valid characters");
        }
        if (password.length() < 8) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, " user's password too short");
        }
        if (password.length() > 45) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, " user's password too long");
        }
        if (!password.matches(".*[a-zA-Z]+.*")) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, " at least one letter is required");
        }
        if (!password.matches(".*[1-9]+.*")) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, " at least one digit is required");
        }
    }

//    private boolean isPasswordLikeUserName(String password, String userName){
//
//    }

    private void validateUserName(String email) throws ServerException {
        validateNameLength(email);

        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ServerException(ErrorType.INVALID_EMAIL_PATTERN, " please check your entry");
        }
    }

    private void validateNameLength(String name) throws ServerException {
        if (name.isBlank()) {
            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too short");
        }
        if (name.length() > 40) {
            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too long");
        }
    }
}
