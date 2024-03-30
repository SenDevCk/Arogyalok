package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedHistoryModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cust_name")
    @Expose
    private String custName;
    @SerializedName("mob_no")
    @Expose
    private String mobNo;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("alatitute")
    @Expose
    private String alatitute;
    @SerializedName("alongitute")
    @Expose
    private String alongitute;
    @SerializedName("fulladdress")
    @Expose
    private String fulladdress;
    @SerializedName("pro_id")
    @Expose
    private String proId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("extra")
    @Expose
    private String extra;
    @SerializedName("cuid")
    @Expose
    private String cuid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("booking_no")
    @Expose
    private String bookingNo;
    @SerializedName("ctime")
    @Expose
    private String ctime;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;
    @SerializedName("totalmed_bill")
    @Expose
    private String totalmedBill;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MedHistoryModel withId(String id) {
        this.id = id;
        return this;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public MedHistoryModel withCustName(String custName) {
        this.custName = custName;
        return this;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public MedHistoryModel withMobNo(String mobNo) {
        this.mobNo = mobNo;
        return this;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public MedHistoryModel withStateId(String stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public MedHistoryModel withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public MedHistoryModel withPinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public MedHistoryModel withBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public MedHistoryModel withPrescription(String prescription) {
        this.prescription = prescription;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MedHistoryModel withLocation(String location) {
        this.location = location;
        return this;
    }

    public String getAlatitute() {
        return alatitute;
    }

    public void setAlatitute(String alatitute) {
        this.alatitute = alatitute;
    }

    public MedHistoryModel withAlatitute(String alatitute) {
        this.alatitute = alatitute;
        return this;
    }

    public String getAlongitute() {
        return alongitute;
    }

    public void setAlongitute(String alongitute) {
        this.alongitute = alongitute;
    }

    public MedHistoryModel withAlongitute(String alongitute) {
        this.alongitute = alongitute;
        return this;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public MedHistoryModel withFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
        return this;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public MedHistoryModel withProId(String proId) {
        this.proId = proId;
        return this;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public MedHistoryModel withQty(String qty) {
        this.qty = qty;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public MedHistoryModel withExtra(String extra) {
        this.extra = extra;
        return this;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public MedHistoryModel withCuid(String cuid) {
        this.cuid = cuid;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MedHistoryModel withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public MedHistoryModel withBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
        return this;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public MedHistoryModel withCtime(String ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public MedHistoryModel withBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MedHistoryModel withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public MedHistoryModel withPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
        return this;
    }

    public String getTotalmedBill() {
        return totalmedBill;
    }

    public void setTotalmedBill(String totalmedBill) {
        this.totalmedBill = totalmedBill;
    }

    public MedHistoryModel withTotalmedBill(String totalmedBill) {
        this.totalmedBill = totalmedBill;
        return this;
    }

}