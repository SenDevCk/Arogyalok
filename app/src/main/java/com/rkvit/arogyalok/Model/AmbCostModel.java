package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmbCostModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("agency_id")
    @Expose
    private String agencyId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("am_name")
    @Expose
    private String amName;
    @SerializedName("route_from")
    @Expose
    private String routeFrom;
    @SerializedName("route_to")
    @Expose
    private String routeTo;
    @SerializedName("total_charges")
    @Expose
    private String totalCharges;
    @SerializedName("local_price")
    @Expose
    private String localPrice;
    @SerializedName("price_km")
    @Expose
    private String priceKm;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("amb_cat")
    @Expose
    private String ambCat;
    @SerializedName("stand_charge")
    @Expose
    private String standCharge;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("count_id")
    @Expose
    private Object countId;
    @SerializedName("state_id")
    @Expose
    private Object stateId;
    @SerializedName("city_id")
    @Expose
    private Object cityId;
    @SerializedName("full_address")
    @Expose
    private Object fullAddress;
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
    @SerializedName("vech_no")
    @Expose
    private String vechNo;
    @SerializedName("vech_modal")
    @Expose
    private String vechModal;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("ambu_agency")
    @Expose
    private String ambuAgency;
    @SerializedName("other_charge")
    @Expose
    private String otherCharge;
    @SerializedName("details")
    @Expose
    private String details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAmName() {
        return amName;
    }

    public void setAmName(String amName) {
        this.amName = amName;
    }

    public String getRouteFrom() {
        return routeFrom;
    }

    public void setRouteFrom(String routeFrom) {
        this.routeFrom = routeFrom;
    }

    public String getRouteTo() {
        return routeTo;
    }

    public void setRouteTo(String routeTo) {
        this.routeTo = routeTo;
    }

    public String getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(String totalCharges) {
        this.totalCharges = totalCharges;
    }

    public String getLocalPrice() {
        return localPrice;
    }

    public void setLocalPrice(String localPrice) {
        this.localPrice = localPrice;
    }

    public String getPriceKm() {
        return priceKm;
    }

    public void setPriceKm(String priceKm) {
        this.priceKm = priceKm;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public String getAmbCat() {
        return ambCat;
    }

    public void setAmbCat(String ambCat) {
        this.ambCat = ambCat;
    }

    public String getStandCharge() {
        return standCharge;
    }

    public void setStandCharge(String standCharge) {
        this.standCharge = standCharge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getCountId() {
        return countId;
    }

    public void setCountId(Object countId) {
        this.countId = countId;
    }

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(Object cityId) {
        this.cityId = cityId;
    }

    public Object getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(Object fullAddress) {
        this.fullAddress = fullAddress;
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

    public String getVechNo() {
        return vechNo;
    }

    public void setVechNo(String vechNo) {
        this.vechNo = vechNo;
    }

    public String getVechModal() {
        return vechModal;
    }

    public void setVechModal(String vechModal) {
        this.vechModal = vechModal;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAmbuAgency() {
        return ambuAgency;
    }

    public void setAmbuAgency(String ambuAgency) {
        this.ambuAgency = ambuAgency;
    }

    public String getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(String otherCharge) {
        this.otherCharge = otherCharge;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}