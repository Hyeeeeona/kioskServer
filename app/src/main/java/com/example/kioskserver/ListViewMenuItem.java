package com.example.kioskserver;

public class ListViewMenuItem {
    private String titleStr;
    private String descStr;
    private String priceStr;


    public void setTitle(String title) {
        titleStr = title;
    }
    public void setDesc(String desc) {
        descStr = desc;
    }
    public void setPrice(String price) {
        priceStr = price;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public String getDesc() {
        return this.descStr;
    }
    public String getPrice() {
        return this.priceStr;
    }
}
