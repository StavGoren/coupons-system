package com.stav.server.dto;

import com.stav.server.enums.UserType;

public class UserDTO {

    private long id;
    private String userName;
    private UserType userType;
    private String companyName;

    public UserDTO() {
    }
    public UserDTO(long id, String userName, UserType userType, String companyName) {
        this.id = id;
        this.userName = userName;
        this.userType = userType;
        this.companyName = companyName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

//    public long getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(long companyId) {
//        this.companyId = companyId;
//    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName + '\'' +
                ", userType=" + userType +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
