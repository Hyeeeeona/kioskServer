package com.example.kioskserver;

public class ShopMenu {
    private int id;
    private int shop_id;
    private String menu_name;
    private String menu_size;
    private int hotorcold;
    private int menu_price;

    public ShopMenu() {

    }

    public ShopMenu(int id, int shop_id, String menu_name, String menu_size, int hotorcold, int menu_price) {
        this.id = id;
        this.shop_id = shop_id;
        this.menu_name = menu_name;
        this.menu_size = menu_size;
        this.hotorcold = hotorcold;
        this.menu_price = menu_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_size() {
        return menu_size;
    }

    public void setMenu_size(String menu_size) {
        this.menu_size = menu_size;
    }

    public int getHotorcold() {
        return hotorcold;
    }

    public void setHotorcold(int hotorcold) {
        this.hotorcold = hotorcold;
    }

    public int getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(int menu_price) {
        this.menu_price = menu_price;
    }

}
