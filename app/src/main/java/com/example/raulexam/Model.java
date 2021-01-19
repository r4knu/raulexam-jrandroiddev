package com.example.raulexam;

public class Model {
    String id,image,prodName,prodUnit,prodPrice,prodDate,prodQty;

    public Model(String id, String image, String prodName, String prodUnit, String prodPrice, String prodDate, String prodQty) {

        this.image = image;
        this.id = id;
        this.prodName = prodName;
        this.prodUnit = prodUnit;
        this.prodPrice = prodPrice;
        this.prodDate = prodDate;
        this.prodQty = prodQty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdDate() {
        return prodDate;
    }

    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public String getProdQty() {
        return prodQty;
    }

    public void setProdQty(String prodQty) {
        this.prodQty = prodQty;
    }
}
