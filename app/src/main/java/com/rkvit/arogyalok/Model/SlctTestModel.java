package com.rkvit.arogyalok.Model;

public class SlctTestModel {
  private String id;
  private String brandid;
  private String catId;
  private String subcatId;
  private String name;
  private String marId;
  private String paId;
  private String refundPolicy;
  private String prescription;
  private String image;
  private String countryOrgin;
  private String mrp;
  private String saleMrp;
  private String description;
  private String cuid;
  private String muid;
  private String ctime;
  private String mtime;
  private String status;
  private String catName;
  private String subCatName;
  private String quantity;


  public SlctTestModel(String id, String name, String image, String mrp, String qty) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.mrp = mrp;
    this.quantity = qty;
  }


//  db.insertRecord(new CartItemModel(list.get(itemPosition).getId(), list.get(itemPosition).getId(),
//                list.get(itemPosition).getId(),
//                list.get(itemPosition).getName(), list.get(itemPosition).getIcon(),
//                list.get(itemPosition).getTestFees(), 1));

  public SlctTestModel() {

  }

  public String getId() {
    return id;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBrandid() {
    return brandid;
  }

  public void setBrandid(String brandid) {
    this.brandid = brandid;
  }

  public String getCatId() {
    return catId;
  }

  public void setCatId(String catId) {
    this.catId = catId;
  }

  public String getSubcatId() {
    return subcatId;
  }

  public void setSubcatId(String subcatId) {
    this.subcatId = subcatId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMarId() {
    return marId;
  }

  public void setMarId(String marId) {
    this.marId = marId;
  }

  public String getPaId() {
    return paId;
  }

  public void setPaId(String paId) {
    this.paId = paId;
  }

  public String getRefundPolicy() {
    return refundPolicy;
  }

  public void setRefundPolicy(String refundPolicy) {
    this.refundPolicy = refundPolicy;
  }

  public String getPrescription() {
    return prescription;
  }

  public void setPrescription(String prescription) {
    this.prescription = prescription;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCountryOrgin() {
    return countryOrgin;
  }

  public void setCountryOrgin(String countryOrgin) {
    this.countryOrgin = countryOrgin;
  }

  public String getMrp() {
    return mrp;
  }

  public void setMrp(String mrp) {
    this.mrp = mrp;
  }

  public String getSaleMrp() {
    return saleMrp;
  }

  public void setSaleMrp(String saleMrp) {
    this.saleMrp = saleMrp;
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

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getSubCatName() {
    return subCatName;
  }

  public void setSubCatName(String subCatName) {
    this.subCatName = subCatName;
  }
}
