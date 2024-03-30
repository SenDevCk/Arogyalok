package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchemeHistoryModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sc_id")
    @Expose
    private String scId;
    @SerializedName("lab_id")
    @Expose
    private String labId;
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
    @SerializedName("booking_no")
    @Expose
    private String bookingNo;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("Validity")
    @Expose
    private String validity;
    @SerializedName("cuid")
    @Expose
    private String cuid;
    @SerializedName("ctime")
    @Expose
    private String ctime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
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

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

}