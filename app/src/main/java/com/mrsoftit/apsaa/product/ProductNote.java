package com.mrsoftit.apsaa.product;

public class ProductNote {
    String thisDucID;
    String name;
    double price;
    String discription;
    String search;

    String i1;
    String i2;
    String i3;
    String i4;
    String i15;

    public ProductNote(){}

    public ProductNote(String thisDucID, String name,
                       double price, String discription,
                       String search,
                       String i1, String i2, String i3, String i4, String i15) {
        this.thisDucID = thisDucID;
        this.name = name;
        this.price = price;
        this.discription = discription;
        this.search = search;
        this.i1 = i1;
        this.i2 = i2;
        this.i3 = i3;
        this.i4 = i4;
        this.i15 = i15;
    }

    public String getThisDucID() {
        return thisDucID;
    }

    public void setThisDucID(String thisDucID) {
        this.thisDucID = thisDucID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getI1() {
        return i1;
    }

    public void setI1(String i1) {
        this.i1 = i1;
    }

    public String getI2() {
        return i2;
    }

    public void setI2(String i2) {
        this.i2 = i2;
    }

    public String getI3() {
        return i3;
    }

    public void setI3(String i3) {
        this.i3 = i3;
    }

    public String getI4() {
        return i4;
    }

    public void setI4(String i4) {
        this.i4 = i4;
    }

    public String getI15() {
        return i15;
    }

    public void setI15(String i15) {
        this.i15 = i15;
    }
}
