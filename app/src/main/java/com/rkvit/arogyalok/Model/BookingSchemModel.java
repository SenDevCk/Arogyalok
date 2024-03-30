package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingSchemModel {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("booking_no")
    @Expose
    private String bookingNo;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mob_no")
    @Expose
    private String mobNo;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("pat_blood")
    @Expose
    private String patBlood;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPatBlood() {
        return patBlood;
    }

    public void setPatBlood(String patBlood) {
        this.patBlood = patBlood;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}