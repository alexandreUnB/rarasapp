package com.rarasnet.rnp.shared.usuario.controllers.network.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Farina on 14/7/2015.
 */
public class UserRequest {

    @SerializedName("lg") private String userLogin;
    @SerializedName("pw") private String userPassword;

    public String getUserLogin() {
        return userLogin;
    }

    public UserRequest(String userLogin, String userPassword) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
