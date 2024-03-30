package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocSchdModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("doc_id")
    @Expose
    private String docId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("slot1")
    @Expose
    private String slot1;
    @SerializedName("mtimefrom")
    @Expose
    private String mtimefrom;
    @SerializedName("mtimeto")
    @Expose
    private String mtimeto;
    @SerializedName("mtime_interval")
    @Expose
    private String mtimeInterval;
    @SerializedName("mcheckpatient")
    @Expose
    private String mcheckpatient;
    @SerializedName("slot2")
    @Expose
    private String slot2;
    @SerializedName("atimefrom")
    @Expose
    private String atimefrom;
    @SerializedName("atimeto")
    @Expose
    private String atimeto;
    @SerializedName("atime_interval")
    @Expose
    private String atimeInterval;
    @SerializedName("acheckpatient")
    @Expose
    private String acheckpatient;
    @SerializedName("slot3")
    @Expose
    private String slot3;
    @SerializedName("etimefrom")
    @Expose
    private String etimefrom;
    @SerializedName("etimeto")
    @Expose
    private String etimeto;
    @SerializedName("etime_interval")
    @Expose
    private String etimeInterval;
    @SerializedName("echeckpatient")
    @Expose
    private String echeckpatient;
    @SerializedName("total_patients")
    @Expose
    private String totalPatients;
    @SerializedName("time_notes")
    @Expose
    private String timeNotes;
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSlot1() {
        return slot1;
    }

    public void setSlot1(String slot1) {
        this.slot1 = slot1;
    }

    public String getMtimefrom() {
        return mtimefrom;
    }

    public void setMtimefrom(String mtimefrom) {
        this.mtimefrom = mtimefrom;
    }

    public String getMtimeto() {
        return mtimeto;
    }

    public void setMtimeto(String mtimeto) {
        this.mtimeto = mtimeto;
    }

    public String getMtimeInterval() {
        return mtimeInterval;
    }

    public void setMtimeInterval(String mtimeInterval) {
        this.mtimeInterval = mtimeInterval;
    }

    public String getMcheckpatient() {
        return mcheckpatient;
    }

    public void setMcheckpatient(String mcheckpatient) {
        this.mcheckpatient = mcheckpatient;
    }

    public String getSlot2() {
        return slot2;
    }

    public void setSlot2(String slot2) {
        this.slot2 = slot2;
    }

    public String getAtimefrom() {
        return atimefrom;
    }

    public void setAtimefrom(String atimefrom) {
        this.atimefrom = atimefrom;
    }

    public String getAtimeto() {
        return atimeto;
    }

    public void setAtimeto(String atimeto) {
        this.atimeto = atimeto;
    }

    public String getAtimeInterval() {
        return atimeInterval;
    }

    public void setAtimeInterval(String atimeInterval) {
        this.atimeInterval = atimeInterval;
    }

    public String getAcheckpatient() {
        return acheckpatient;
    }

    public void setAcheckpatient(String acheckpatient) {
        this.acheckpatient = acheckpatient;
    }

    public String getSlot3() {
        return slot3;
    }

    public void setSlot3(String slot3) {
        this.slot3 = slot3;
    }

    public String getEtimefrom() {
        return etimefrom;
    }

    public void setEtimefrom(String etimefrom) {
        this.etimefrom = etimefrom;
    }

    public String getEtimeto() {
        return etimeto;
    }

    public void setEtimeto(String etimeto) {
        this.etimeto = etimeto;
    }

    public String getEtimeInterval() {
        return etimeInterval;
    }

    public void setEtimeInterval(String etimeInterval) {
        this.etimeInterval = etimeInterval;
    }

    public String getEcheckpatient() {
        return echeckpatient;
    }

    public void setEcheckpatient(String echeckpatient) {
        this.echeckpatient = echeckpatient;
    }

    public String getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(String totalPatients) {
        this.totalPatients = totalPatients;
    }

    public String getTimeNotes() {
        return timeNotes;
    }

    public void setTimeNotes(String timeNotes) {
        this.timeNotes = timeNotes;
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