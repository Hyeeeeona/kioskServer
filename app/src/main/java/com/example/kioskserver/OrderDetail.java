package com.example.kioskserver;

public class OrderDetail {
    private int order_id;
    private String menu_name;
    private String menu_size;
    private int quantity;
    private int total;

    public OrderDetail() {

    }

    public OrderDetail(int order_id, String menu_name, String menu_size, int quantity, int total) {
        this.order_id = order_id;
        this.menu_name = menu_name;
        this.menu_size = menu_size;
        this.quantity = quantity;
        this.total = total;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
