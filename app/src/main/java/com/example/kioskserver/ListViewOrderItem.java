package com.example.kioskserver;

public class ListViewOrderItem {
    private String orderNoStr;
    private String orderTimeStr;
    private String orderDescStr;

    public void setOrderNo(String orderNo) {
        orderNoStr = "주문번호 : " + orderNo;
    }
    public void setOrderTime(String orderTime) {
        orderTimeStr = "주문시각 : " + orderTime;
    }
    public void setOrderDesc(String orderDesc) {
        orderDescStr = orderDesc;
    }

    public String getOrderNo() {
        return this.orderNoStr;
    }
    public String getOrderTime() {
        return this.orderTimeStr;
    }
    public String getOrderDesc() {
        return this.orderDescStr;
    }
}
