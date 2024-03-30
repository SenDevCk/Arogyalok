package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;


public class LoginModel {

    @Expose
    private String email;
    @Expose
    private String id;
    @Expose
    private String mobile;
    @Expose
    private String name;
    @Expose
    private String token;
    @Expose
    private String is_verify;
    @Expose
    private String state_id;
    @Expose
    private String pin_code;
    @Expose
    private String city_id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(String is_verify) {
        this.is_verify = is_verify;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
