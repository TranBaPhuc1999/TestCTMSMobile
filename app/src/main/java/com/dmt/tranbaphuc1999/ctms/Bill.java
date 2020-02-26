package com.dmt.tranbaphuc1999.ctms;

public class Bill {
    private  String __Bill_Day;
    private String __Bill_Code;
    private String __Person_Create;
    private String __Total_TC;
    private String __Money_Paid;

    public String get__TT() {
        return __TT;
    }

    public void set__TT(String __TT) {
        this.__TT = __TT;
    }

    public String get__GT() {
        return __GT;
    }

    public void set__GT(String __GT) {
        this.__GT = __GT;
    }

    public String get__CanNop() {
        return __CanNop;
    }

    public void set__CanNop(String __CanNop) {
        this.__CanNop = __CanNop;
    }

    private String __TT;
    private String __GT;
    private String __CanNop;

    public String get__Bill_Day() {
        return __Bill_Day;
    }

    public void set__Bill_Day(String __Bill_Day) {
        this.__Bill_Day = __Bill_Day;
    }

    public String get__Bill_Code() {
        return __Bill_Code;
    }

    public void set__Bill_Code(String __Bill_Code) {
        this.__Bill_Code = __Bill_Code;
    }

    public String get__Person_Create() {
        return __Person_Create;
    }

    public void set__Person_Create(String __Person_Create) {
        this.__Person_Create = __Person_Create;
    }

    public String get__Total_TC() {
        return __Total_TC;
    }

    public void set__Total_TC(String __Total_Money) {
        this.__Total_TC = __Total_Money;
    }

    public String get__Money_Paid() {
        return __Money_Paid;
    }

    public void set__Money_Paid(String __Money_Paid) {
        this.__Money_Paid = __Money_Paid;
    }

    public Bill(String __Bill_Day, String __Bill_Code, String __Person_Create, String __Total_TC,String __TT, String __GT, String __Money_Paid,String __CanNop) {
        this.__Bill_Day = __Bill_Day;
        this.__Bill_Code = __Bill_Code;
        this.__Person_Create = __Person_Create;
        this.__Total_TC = __Total_TC;
        this.__Money_Paid = __Money_Paid;
        this.__TT = __TT;
        this.__GT = __GT;
        this.__CanNop = __CanNop;
    }
}
