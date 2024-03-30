package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabHistoryModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lab_id")
    @Expose
    private String labId;
    @SerializedName("booking_no")
    @Expose
    private String bookingNo;
    @SerializedName("pat_name")
    @Expose
    private String patName;
    @SerializedName("mob_no")
    @Expose
    private String mobNo;
    @SerializedName("pat_blood")
    @Expose
    private String patBlood;
    @SerializedName("dop")
    @Expose
    private String dop;
    @SerializedName("test_id")
    @Expose
    private String testId;
    @SerializedName("precription")
    @Expose
    private String precription;
    @SerializedName("recom_by")
    @Expose
    private String recomBy;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("cuid")
    @Expose
    private String cuid;
    @SerializedName("mtime")
    @Expose
    private String mtime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LabHistoryModel withId(String id) {
        this.id = id;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LabHistoryModel withLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public LabHistoryModel withBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
        return this;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public LabHistoryModel withPatName(String patName) {
        this.patName = patName;
        return this;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public LabHistoryModel withMobNo(String mobNo) {
        this.mobNo = mobNo;
        return this;
    }

    public String getPatBlood() {
        return patBlood;
    }

    public void setPatBlood(String patBlood) {
        this.patBlood = patBlood;
    }

    public LabHistoryModel withPatBlood(String patBlood) {
        this.patBlood = patBlood;
        return this;
    }

    public String getDop() {
        return dop;
    }

    public void setDop(String dop) {
        this.dop = dop;
    }

    public LabHistoryModel withDop(String dop) {
        this.dop = dop;
        return this;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public LabHistoryModel withTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public String getPrecription() {
        return precription;
    }

    public void setPrecription(String precription) {
        this.precription = precription;
    }

    public LabHistoryModel withPrecription(String precription) {
        this.precription = precription;
        return this;
    }

    public String getRecomBy() {
        return recomBy;
    }

    public void setRecomBy(String recomBy) {
        this.recomBy = recomBy;
    }

    public LabHistoryModel withRecomBy(String recomBy) {
        this.recomBy = recomBy;
        return this;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public LabHistoryModel withFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public LabHistoryModel withCuid(String cuid) {
        this.cuid = cuid;
        return this;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public LabHistoryModel withMtime(String mtime) {
        this.mtime = mtime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LabHistoryModel withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LabHistoryModel withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public LabHistoryModel withPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
        return this;
    }

}