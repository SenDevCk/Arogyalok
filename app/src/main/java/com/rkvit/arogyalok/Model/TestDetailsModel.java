package com.rkvit.arogyalok.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestDetailsModel {

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

    private String testFees;

    public String getTestFees() {
        return testFees;
    }

    public void setTestFees(String testFees) {
        this.testFees = testFees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestDetailsModel withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestDetailsModel withName(String name) {
        this.name = name;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TestDetailsModel withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getMandTest() {
        return mandTest;
    }

    public void setMandTest(String mandTest) {
        this.mandTest = mandTest;
    }

    public TestDetailsModel withMandTest(String mandTest) {
        this.mandTest = mandTest;
        return this;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public TestDetailsModel withTestReport(String testReport) {
        this.testReport = testReport;
        return this;
    }

    public String getCoverdParamerter() {
        return coverdParamerter;
    }

    public void setCoverdParamerter(String coverdParamerter) {
        this.coverdParamerter = coverdParamerter;
    }

    public TestDetailsModel withCoverdParamerter(String coverdParamerter) {
        this.coverdParamerter = coverdParamerter;
        return this;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public TestDetailsModel withTestMode(String testMode) {
        this.testMode = testMode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestDetailsModel withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public TestDetailsModel withCuid(String cuid) {
        this.cuid = cuid;
        return this;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public TestDetailsModel withMuid(String muid) {
        this.muid = muid;
        return this;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public TestDetailsModel withCtime(String ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public TestDetailsModel withMtime(String mtime) {
        this.mtime = mtime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TestDetailsModel withStatus(String status) {
        this.status = status;
        return this;
    }

}