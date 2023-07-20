package com.example.project;

import java.sql.Date;

public class Drugs {
    int drugId,quantity;
    double price;
    String drugName,supplierName,ageGroup,prescription,description;
    Date expiryDate,productionDate,supplyDate;

    public Drugs( int quantity, double price, String drugName,
                 String supplierName, String ageGroup, String prescription,
                 String description, Date expiryDate, Date productionDate) {
        this.drugId = drugId;
        this.quantity = quantity;
        this.price = price;
        this.drugName = drugName;
        this.supplierName = supplierName;
        this.ageGroup = ageGroup;
        this.prescription = prescription;
        this.description = description;
        this.expiryDate = expiryDate;
        this.productionDate = productionDate;
        this.supplyDate = supplyDate;
    }

    public int getDrugId() {
        return drugId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getDescription() {
        return description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }
}
