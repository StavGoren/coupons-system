package com.stav.server.enums;

public enum ErrorType {
	GENERAL_ERROR(600, "Something went wrong", true),
	INVALID_EMAIL_PATTERN(601, "Make sure you entered a correct email pattern", false),
    CONTAINS_WHITE_SPACE(602, "This field can't contain whitespace", false),
    NAME_EXISTS (603, "That data already exists", false),
    INVALID_USERNAME_LENGTH (604, "Username may contain 6-20 characters", false),
	INVALID_PASSWORD(605, "Password must contain 8-45 characters of numbers and letters. Symbols are optional", false),
    NAME_NOT_EXIST(606, "Name doesn't exist", false),
    INVALID_CHARACTER_IN_PHONE_NUMBER(607, "Phone number may only contain digits", false),
    INVALID_DESCRIPTION_LENGTH (608, "Description may contain 10-250 characters", false),
    EXPIRED_COUPON (609, "This coupon has expired", false),
    INVALID_COUPON_CHARACTERS(610, "Coupon number can only contain numbers and uppercase letters", false),
    DATA_NOT_FOUND(611, "Data not found", false),
    EMPTY_FIELD(612, "This field can't be empty", false),
    INVALID_NUMBER(613, "Number can be 1 or greater", false),
    INVALID_NAME_LENGTH(615, "Name may contain 1-40 characters", false),
    INVALID_PHONE_NUMBER_LENGTH(616, "Phone number may contain at least 9 digits including area code", false),
    INVALID_COUPON_LENGTH(617, "This field may contain 3-30 characters", false),
    INVALID_PRICE(618, "Price must be 1 or greater", false),
    INVALID_USER_TYPE(619, "You cannot perform this action", false),
    INVALID_ADDRESS_LENGTH(620, "Address length too short", false),
    LOGIN_FAILED(621, "Incorrect username or password", false),
    INVALID_DATE(622, "Date value must be today or later", false),
    ACTION_NOT_ALLOWED(623, "You are not allowed to perform this action!", false),
    INVALID_PASSWORD_CHARACTERS(624, "Password may only contain the following symbols: !, @, #, $, %, %, &, _, -, +, =", false),
    INVALID_END_DATE(625, "End date must be later than start date", false),
    OUT_OF_STOCK(626, "Coupon is out of stock", false),
    NOT_LOGGED_IN(627, "Please login", false);

    private int internalErrorNumber;
    private String errorMessage;
    private boolean showStackTrace;

    ErrorType(int internalErrorNumber, String errorMessage, boolean showStackTrace){
        this(internalErrorNumber, errorMessage);
        this.showStackTrace = showStackTrace;
    }

    ErrorType(int internalErrorNumber, String errorMessage){
        this.internalErrorNumber = internalErrorNumber;
        this.errorMessage = errorMessage;
    }

    public int getInternalErrorNumber() {
        return internalErrorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isShowStackTrace() {
        return showStackTrace;
    }


}
