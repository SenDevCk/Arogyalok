package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lab_name")
    @Expose
    private String labName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("tech_name")
    @Expose
    private String techName;
    @SerializedName("tech_mobile")
    @Expose
    private String techMobile;
    @SerializedName("tech_degree")
    @Expose
    private String techDegree;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("time_from")
    @Expose
    private String timeFrom;
    @SerializedName("time_to")
    @Expose
    private String timeTo;
    @SerializedName("count_id")
    @Expose
    private String countId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    private String cityName;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("work_day")
    @Expose
    private String workDay;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("cuid")
    @Expose
    private String cuid;
    @SerializedName("muid")
    @Expose
    private String muid;
    @SerializedName("ctime")
    @Expose
    private String ctime;
    @SerializedName("mtime")
    @Expose
    private String mtime;
    @SerializedName("status")
    @Expose
    private String status;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LabModel withId(String id) {
        this.id = id;
        return this;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public LabModel withLabName(String labName) {
        this.labName = labName;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LabModel withImage(String image) {
        this.image = image;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LabModel withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public LabModel withTechName(String techName) {
        this.techName = techName;
        return this;
    }

    public String getTechMobile() {
        return techMobile;
    }

    public void setTechMobile(String techMobile) {
        this.techMobile = techMobile;
    }

    public LabModel withTechMobile(String techMobile) {
        this.techMobile = techMobile;
        return this;
    }

    public String getTechDegree() {
        return techDegree;
    }

    public void setTechDegree(String techDegree) {
        this.techDegree = techDegree;
    }

    public LabModel withTechDegree(String techDegree) {
        this.techDegree = techDegree;
        return this;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LabModel withBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LabModel withEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LabModel withTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public LabModel withTimeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public String getCountId() {
        return countId;
    }

    public void setCountId(String countId) {
        this.countId = countId;
    }

    public LabModel withCountId(String countId) {
        this.countId = countId;
        return this;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public LabModel withStateId(String stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public LabModel withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public LabModel withFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public LabModel withWorkDay(String workDay) {
        this.workDay = workDay;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LabModel withDetails(String details) {
        this.details = details;
        return this;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public LabModel withCuid(String cuid) {
        this.cuid = cuid;
        return this;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public LabModel withMuid(String muid) {
        this.muid = muid;
        return this;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public LabModel withCtime(String ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public LabModel withMtime(String mtime) {
        this.mtime = mtime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LabModel withStatus(String status) {
        this.status = status;
        return this;
    }

}