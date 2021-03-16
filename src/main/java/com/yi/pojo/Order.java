package com.yi.pojo;

import java.math.BigInteger;
import java.sql.Timestamp;

public class Order {
    private BigInteger Order_ID;        //订单ID
    private BigInteger Customer_ID;     //会员ID
    private BigInteger Schedule_ID;     //时间表ID
    private String Seat_ID;         //座位ID
    private int Order_IsActive;     //订单是否可用
    private String Order_SecretKey; //用于存放密钥
    private String Order_Cipher;    //用于存放加密后的密文
    private float Order_AdjustedPrice;//会员打折之后的价格
    private Timestamp Order_BuyDate;     //购买时间

    public BigInteger getOrder_ID() {return Order_ID;}
    public void setOrder_ID(BigInteger order_ID) {Order_ID = order_ID;}

    public BigInteger getCustomer_ID() {return Customer_ID;}
    public void setCustomer_ID(BigInteger customer_ID) {Customer_ID = customer_ID;}

    public BigInteger getSchedule_ID() {return Schedule_ID;}
    public void setSchedule_ID(BigInteger schedule_ID) {Schedule_ID = schedule_ID;}

    public String getSeat_ID() {return Seat_ID;}
    public void setSeat_ID(String seat_ID) {Seat_ID = seat_ID;}

    public int getOrder_IsActive() {return Order_IsActive;}
    public void setOrder_IsActive(int order_IsActive) {Order_IsActive = order_IsActive;}

    public String getOrder_SecretKey() {return Order_SecretKey;}
    public void setOrder_SecretKey(String order_SecretKey) {Order_SecretKey = order_SecretKey;}

    public String getOrder_Cipher() {return Order_Cipher;}
    public void setOrder_Cipher(String order_Cipher) {Order_Cipher = order_Cipher;}

    public float getOrder_AdjustedPrice() {return Order_AdjustedPrice;}
    public void setOrder_AdjustedPrice(float order_AdjustedPrice) {Order_AdjustedPrice = order_AdjustedPrice;}

    public Timestamp getOrder_BuyDate() {return Order_BuyDate;}
    public void setOrder_BuyDate(Timestamp order_BuyDate) {Order_BuyDate = order_BuyDate;}
}
