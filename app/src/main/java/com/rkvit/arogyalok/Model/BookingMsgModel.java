package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BookingMsgModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("booking_no")
    @Expose
    private String bookingNo;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("otp")
    @Expose
    private String otp;

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

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
