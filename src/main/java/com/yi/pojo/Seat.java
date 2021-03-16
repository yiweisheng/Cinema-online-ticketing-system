package com.yi.pojo;

public class Seat {
    private String Seat_ID;     //座位ID
    private String Hall_ID;     //影厅ID
    private int Seat_Row;       //座位所在行数
    private int Seat_Column;    //座位所在列数
    private int Seat_IsActive;  //座位是否可用
    private int Seat_IsCorridor;//是否为过道


    public String getSeat_ID() {return Seat_ID;}
    public void setSeat_ID(String seat_ID) {Seat_ID = seat_ID;}

    public String getHall_ID() {return Hall_ID;}
    public void setHall_ID(String hall_ID) {Hall_ID = hall_ID;}

    public int getSeat_Row() {return Seat_Row;}
    public void setSeat_Row(int seat_Row) {Seat_Row = seat_Row;}

    public int getSeat_Column() {return Seat_Column;}
    public void setSeat_Column(int seat_Column) {Seat_Column = seat_Column;}

    public int getSeat_IsActive() {return Seat_IsActive;}
    public void setSeat_IsActive(int seat_IsActive) {Seat_IsActive = seat_IsActive;}

    public int getSeat_IsCorridor() {return Seat_IsCorridor;}
    public void setSeat_IsCorridor(int seat_IsCorridor) {Seat_IsCorridor = seat_IsCorridor;}

}
