package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocHistoryModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("doc_id")
    @Expose
    private String docId;
    @SerializedName("pat_name")
    @Expose
    private String patName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("sec_date")
    @Expose
    private String secDate;
    @SerializedName("sec_time")
    @Expose
    private String secTime;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("pat_condition")
    @Expose
    private String patCondition;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("cuid")
    @Expose
    private String cuid;
    @SerializedName("booking_no")
    @Expose
    private String bookingNo;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;
    @SerializedName("ctime")
    @Expose
    private String ctime;
    @SerializedName("mtime")
    @Expose
    private String mtime;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocHistoryModel withId(String id) {
        this.id = id;
        return this;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public DocHistoryModel withDocId(String docId) {
        this.docId = docId;
        return this;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public DocHistoryModel withPatName(String patName) {
        this.patName = patName;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public DocHistoryModel withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DocHistoryModel withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public DocHistoryModel withPrescription(String prescription) {
        this.prescription = prescription;
        return this;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public DocHistoryModel withBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public DocHistoryModel withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getSecDate() {
        return secDate;
    }

    public void setSecDate(String secDate) {
        this.secDate = secDate;
    }

    public DocHistoryModel withSecDate(String secDate) {
        this.secDate = secDate;
        return this;
    }

    public String getSecTime() {
        return secTime;
    }

    public void setSecTime(String secTime) {
        this.secTime = secTime;
    }

    public DocHistoryModel withSecTime(String secTime) {
        this.secTime = secTime;
        return this;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public DocHistoryModel withPinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public String getPatCondition() {
        return patCondition;
    }

    public void setPatCondition(String patCondition) {
        this.patCondition = patCondition;
    }

    public DocHistoryModel withPatCondition(String patCondition) {
        this.patCondition = patCondition;
        return this;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public DocHistoryModel withFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public DocHistoryModel withCuid(String cuid) {
        this.cuid = cuid;
        return this;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public DocHistoryModel withBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
        return this;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public DocHistoryModel withPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
        return this;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public DocHistoryModel withCtime(String ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public DocHistoryModel withMtime(String mtime) {
        this.mtime = mtime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DocHistoryModel withStatus(String status) {
        this.status = status;
        return this;
    }

}