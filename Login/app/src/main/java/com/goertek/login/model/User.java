package com.goertek.login.model;

/**
 * Created by clara.tong on 2018/5/22.
 */

public class User implements IUser{
    private String userName;
    private String userPassword;

    public User(String userName,String userPassword) {
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

   /* @Override
    public int checkUserValidity(String userName,String userPassword) {
        int code = 0;
        if (userName.equals("tong")&& userPassword.equals("qwe123")){
            code = 1;
        }else {
            code = 0;
        }
        return code;
    }*/
}
