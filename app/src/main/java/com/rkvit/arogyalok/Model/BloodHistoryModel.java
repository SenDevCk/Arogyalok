package com.rkvit.arogyalok.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodHistoryModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("donorid")
    @Expose
    private String donorid;
    @SerializedName("pat_name")
    @Expose
    private String patName;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("pat_blood")
    @Expose
    private String patBlood;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("alatitute")
    @Expose
    private String alatitute;
    @SerializedName("alongitute")
    @Expose
    private String alongitute;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("ctime")
    @Expose
    private String ctime;
    @SerializedName("request_date")
    @Expose
    private String requestDate;
    @SerializedName("cuid")
    @Expose
    private String cuid;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDonorid() {
        return donorid;
    }

    public void setDonorid(String donorid) {
        this.donorid = donorid;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getPatBlood() {
        return patBlood;
    }

    public void setPatBlood(String patBlood) {
        this.patBlood = patBlood;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAlatitute() {
        return alatitute;
    }

    public void setAlatitute(String alatitute) {
        this.alatitute = alatitute;
    }

    public String getAlongitute() {
        return alongitute;
    }

    public void setAlongitute(String alongitute) {
        this.alongitute = alongitute;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}