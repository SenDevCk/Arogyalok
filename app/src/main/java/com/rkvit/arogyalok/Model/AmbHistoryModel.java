package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmbHistoryModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("amb_id")
    @Expose
    private String ambId;
    @SerializedName("booking_no")
    @Expose
    private String bookingNo;
    @SerializedName("agency_id")
    @Expose
    private String agencyId;
    @SerializedName("pat_name")
    @Expose
    private String patName;
    @SerializedName("mob_no")
    @Expose
    private String mobNo;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("pat_blood")
    @Expose
    private String patBlood;
    @SerializedName("patientcodition")
    @Expose
    private String patientcodition;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("arrival")
    @Expose
    private String arrival;
    @SerializedName("alatitute")
    @Expose
    private String alatitute;
    @SerializedName("alongitute")
    @Expose
    private String alongitute;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("mtime")
    @Expose
    private String mtime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cuid")
    @Expose
    private String cuid;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmbId() {
        return ambId;
    }

    public void setAmbId(String ambId) {
        this.ambId = ambId;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
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

    public String getPatientcodition() {
        return patientcodition;
    }

    public void setPatientcodition(String patientcodition) {
        this.patientcodition = patientcodition;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

}