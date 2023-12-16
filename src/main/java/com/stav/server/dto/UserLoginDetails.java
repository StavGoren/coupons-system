package com.stav.server.dto;

public class UserLoginDetails {
    private String userName;
    private String password;

    public UserLoginDetails() {
    }

    public UserLoginDetails(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginDetails [userName=" + userName + ", password=" + password + "]";
    }
}
