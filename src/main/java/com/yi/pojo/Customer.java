package com.yi.pojo;

import java.math.BigInteger;
import java.sql.Timestamp;

public class Customer {
    private BigInteger Customer_ID;     //会员ID
    private String Customer_Name;   //会员名
    private String Customer_Email;  //会员邮箱
    private String Customer_Mobile; //会员电话
    private String Customer_PassWord;        //会员密码
    private int Class_ID;           //会员等级
    private Timestamp Customer_CreationDate;//创建时间
    private Timestamp Customer_ModifyDate;//更新时间

    public BigInteger getCustomer_ID() {return Customer_ID;}
    public void setCustomer_ID(BigInteger customer_ID) {Customer_ID = customer_ID;}

    public String getCustomer_Name() {return Customer_Name;}
    public void setCustomer_Name(String customer_Name) {Customer_Name = customer_Name;}

    public String getCustomer_Email() {return Customer_Email;}
    public void setCustomer_Email(String customer_Email) {Customer_Email = customer_Email;}

    public String getCustomer_Mobile() {return Customer_Mobile;}
    public void setCustomer_Mobile(String customer_Mobile) {Customer_Mobile = customer_Mobile;}

    public String getPassWord() {return Customer_PassWord;}
    public void setPassWord(String customer_PassWord) {Customer_PassWord = customer_PassWord;}

    public int getClass_ID() {return Class_ID;}
    public void setClass_ID(int class_ID) {Class_ID = class_ID;}

    public Timestamp getCustomer_CreationDate() {return Customer_CreationDate;}
    public void setCustomer_CreationDate(Timestamp customer_CreationDate) {Customer_CreationDate = customer_CreationDate;}

    public Timestamp getCustomer_ModifyDate() {return Customer_ModifyDate;}
    public void setCustomer_ModifyDate(Timestamp customer_ModifyDate) {Customer_ModifyDate = customer_ModifyDate;}

}
