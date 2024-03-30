package com.rkvit.arogyalok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedCatModel {

  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("brandid")
  @Expose
  private String brandid;
  @SerializedName("cat_id")
  @Expose
  private String catId;
  @SerializedName("subcat_id")
  @Expose
  private String subcatId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("mar_id")
  @Expose
  private String marId;
  @SerializedName("pa_id")
  @Expose
  private String paId;
  @SerializedName("refund_policy")
  @Expose
  private String refundPolicy;
  @SerializedName("prescription")
  @Expose
  private String prescription;
  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("country_orgin")
  @Expose
  private String countryOrgin;
  @SerializedName("mrp")
  @Expose
  private String mrp;
  @SerializedName("sale_mrp")
  @Expose
  private String saleMrp;
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

  public MedCatModel withId(String id) {
    this.id = id;
    return this;
  }

  public String getBrandid() {
    return brandid;
  }

  public void setBrandid(String brandid) {
    this.brandid = brandid;
  }

  public MedCatModel withBrandid(String brandid) {
    this.brandid = brandid;
    return this;
  }

  public String getCatId() {
    return catId;
  }

  public void setCatId(String catId) {
    this.catId = catId;
  }

  public MedCatModel withCatId(String catId) {
    this.catId = catId;
    return this;
  }

  public String getSubcatId() {
    return subcatId;
  }

  public void setSubcatId(String subcatId) {
    this.subcatId = subcatId;
  }

  public MedCatModel withSubcatId(String subcatId) {
    this.subcatId = subcatId;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MedCatModel withName(String name) {
    this.name = name;
    return this;
  }

  public String getMarId() {
    return marId;
  }

  public void setMarId(String marId) {
    this.marId = marId;
  }

  public MedCatModel withMarId(String marId) {
    this.marId = marId;
    return this;
  }

  public String getPaId() {
    return paId;
  }

  public void setPaId(String paId) {
    this.paId = paId;
  }

  public MedCatModel withPaId(String paId) {
    this.paId = paId;
    return this;
  }

  public String getRefundPolicy() {
    return refundPolicy;
  }

  public void setRefundPolicy(String refundPolicy) {
    this.refundPolicy = refundPolicy;
  }

  public MedCatModel withRefundPolicy(String refundPolicy) {
    this.refundPolicy = refundPolicy;
    return this;
  }

  public String getPrescription() {
    return prescription;
  }

  public void setPrescription(String prescription) {
    this.prescription = prescription;
  }

  public MedCatModel withPrescription(String prescription) {
    this.prescription = prescription;
    return this;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public MedCatModel withImage(String image) {
    this.image = image;
    return this;
  }

  public String getCountryOrgin() {
    return countryOrgin;
  }

  public void setCountryOrgin(String countryOrgin) {
    this.countryOrgin = countryOrgin;
  }

  public MedCatModel withCountryOrgin(String countryOrgin) {
    this.countryOrgin = countryOrgin;
    return this;
  }

  public String getMrp() {
    return mrp;
  }

  public void setMrp(String mrp) {
    this.mrp = mrp;
  }

  public MedCatModel withMrp(String mrp) {
    this.mrp = mrp;
    return this;
  }

  public String getSaleMrp() {
    return saleMrp;
  }

  public void setSaleMrp(String saleMrp) {
    this.saleMrp = saleMrp;
  }

  public MedCatModel withSaleMrp(String saleMrp) {
    this.saleMrp = saleMrp;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MedCatModel withDescription(String description) {
    this.description = description;
    return this;
  }

  public String getCuid() {
    return cuid;
  }

  public void setCuid(String cuid) {
    this.cuid = cuid;
  }

  public MedCatModel withCuid(String cuid) {
    this.cuid = cuid;
    return this;
  }

  public String getMuid() {
    return muid;
  }

  public void setMuid(String muid) {
    this.muid = muid;
  }

  public MedCatModel withMuid(String muid) {
    this.muid = muid;
    return this;
  }

  public String getCtime() {
    return ctime;
  }

  public void setCtime(String ctime) {
    this.ctime = ctime;
  }

  public MedCatModel withCtime(String ctime) {
    this.ctime = ctime;
    return this;
  }

  public String getMtime() {
    return mtime;
  }

  public void setMtime(String mtime) {
    this.mtime = mtime;
  }

  public MedCatModel withMtime(String mtime) {
    this.mtime = mtime;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public MedCatModel withStatus(String status) {
    this.status = status;
    return this;
  }

}