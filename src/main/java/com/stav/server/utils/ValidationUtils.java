//package com.stav.server.utils;
//
//import com.stav.server.exceptions.ServerException;
//import com.stav.server.enums.ErrorType;
//import com.stav.server.enums.UserType;
//import io.jsonwebtoken.Claims;
//
//public class ValidationUtils {
//
//    public static void validateNameLength(String name) throws ServerException {
//        if (name.isBlank()) {
//            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too short");
//        }
//        if (name.length() > 40) {
//            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too long");
//        }
//    }
//
//    public static void validateAddress(String address) throws ServerException {
//        if(address.isBlank()) {
//            throw new ServerException(ErrorType.EMPTY_FIELD, " address is blank");
//        }
//        if (address.length() < 2) {
//            throw new ServerException(ErrorType.INVALID_ADDRESS_LENGTH, " address has to contain at least 2 characters");
//        }
//    }
//
//    public static void validateUserPermission(UserType userType, String authorization) throws ServerException {
//        Claims claims = JWTUtils.decodeJWT(authorization);
//        String strUserType = claims.getIssuer();
//        if(!String.valueOf(userType).equals(strUserType)){
//            throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " not authorised");
//        }
//    }
//
//    public static void validatePhoneNumber(String phoneNumber) throws ServerException {
//
////        if(phoneNumber.isBlank()){
////            throw new ServerException(ErrorType.EMPTY_FIELD, " this filed must contain a phone number");
////        }
//        if (phoneNumber.length() < 9) {
//            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER_LENGTH, " please make sure you entered a valid phone number");
//        }
//        if(phoneNumber.contains(" ")) {
//            throw new ServerException(ErrorType.CONTAINS_WHITE_SPACE, " please check the number");
//        }
//        if(!phoneNumber.matches(".*[0-9]-.*")){
//            throw new ServerException(ErrorType.INVALID_CHARACTER_IN_PHONE_NUMBER, " make sure you made a valid entry");
//        }
//    }
//
//    public static Long validateToken(String Authorization) throws ServerException {
//        try {
//            return JWTUtils.validateToken(Authorization);
//        } catch (Exception e) {
//            throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, "Authentication Error , please login", e);
//        }
//    }
//
//
//}