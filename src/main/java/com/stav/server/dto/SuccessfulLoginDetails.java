package com.stav.server.dto;

import com.stav.server.enums.UserType;

public class SuccessfulLoginDetails {
    private long userId;
    private String userName;
    private Long companyId;
    private UserType userType;

    public SuccessfulLoginDetails(long userId, String userName, Long companyId, UserType userType) {
        this.userId = userId;
        this.userName = userName;
        this.companyId = companyId;
        this.userType = userType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
