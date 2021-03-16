package com.yi.pojo;

public class CustomerClass {
    private int class_ID;       //会员等级ID
    private String Class_Name;  //会员等级名称
    private float Class_Discount;//会员打折比例


    public int getClass_ID() {return class_ID;}
    public void setClass_ID(int class_ID) {this.class_ID = class_ID;}
    public String getClass_Name() {return Class_Name;}
    public void setClass_Name(String class_Name) {Class_Name = class_Name;}
    public float getClass_Discount() {return Class_Discount;}
    public void setClass_Discount(float class_Discount) {Class_Discount = class_Discount;}
}
