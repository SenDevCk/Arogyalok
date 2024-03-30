package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestMasterModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("mand_test")
    @Expose
    private String mandTest;
    @SerializedName("test_report")
    @Expose
    private String testReport;
    @SerializedName("coverd_paramerter")
    @Expose
    private String coverdParamerter;
    @SerializedName("test_mode")
    @Expose
    private String testMode;
    @SerializedName("description")
    @Expose
    private String description;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMandTest() {
        return mandTest;
    }

    public void setMandTest(String mandTest) {
        this.mandTest = mandTest;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public String getCoverdParamerter() {
        return coverdParamerter;
    }

    public void setCoverdParamerter(String coverdParamerter) {
        this.coverdParamerter = coverdParamerter;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
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

}