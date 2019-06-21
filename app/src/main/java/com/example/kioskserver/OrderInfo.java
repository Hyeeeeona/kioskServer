package com.example.kioskserver;

public class OrderInfo {
    private int order_id;
    private String order_time;
    private String name;
    private String phone_number;
    private String reservation_time;
    private int shop_id;
    private int total;
    private int status;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() { return status; }

    /* 0: 접수대기중 1:준비중 2:완료 */
    public void setStatus(int status) { this.status = status; }


    public OrderInfo() {
    }

    public OrderInfo(int order_id, String order_time, String name, String phone_number, String reservation_time, int shop_id, int total, int status) {
        this.order_id = order_id;
        this.order_time = order_time;
        this.name = name;
        this.phone_number = phone_number;
        this.reservation_time = reservation_time;
        this.shop_id = shop_id;
        this.total = total;
        this.status = status;
    }
}
