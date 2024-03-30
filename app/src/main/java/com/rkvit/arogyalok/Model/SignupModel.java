package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;


public class SignupModel {

    @Expose
    private String message;
    @Expose
    private Boolean status;
    @Expose
    private int otp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
