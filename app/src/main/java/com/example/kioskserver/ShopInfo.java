package com.example.kioskserver;

public class ShopInfo {
    private String uid;
    private int shop_id;
    private String shop_name;
    private String shop_tel;
    private String business_hours;
    private String personal_day;
    private String shop_address;


    public ShopInfo() {
    }
    public ShopInfo(String uid, int shop_id, String shop_name, String shop_tel, String business_hours, String personal_day, String introduction, String shop_address) {
        this.uid = uid;
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_tel = shop_tel;
        this.business_hours = business_hours;
        this.personal_day = personal_day;
        this.introduction = introduction;
        this.shop_address = shop_address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getShopId() {
        return shop_id;
    }

    public void setShopId(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShopName() {
        return shop_name;
    }

    public void setShopName(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShopTel() {
        return shop_tel;
    }

    public void setShopTel(String shop_tel) {
        this.shop_tel = shop_tel;
    }

    public String getBusinessHours() {
        return business_hours;
    }

    public void setBusinessHours(String business_hours) {
        this.business_hours = business_hours;
    }

    public String getPersonalDay() {
        return personal_day;
    }

    public void setPersonalDay(String personal_day) {
        this.personal_day = personal_day;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    private String introduction;


}

