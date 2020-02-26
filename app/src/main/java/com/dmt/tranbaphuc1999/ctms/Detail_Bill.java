package com.dmt.tranbaphuc1999.ctms;

public class Detail_Bill {
    private String __Sub,__NumberTC,__Cost,__Money;

    public Detail_Bill( String __Sub, String __NumberTC, String __Cost, String __Money) {
        this.__Sub = __Sub;
        this.__NumberTC = __NumberTC;
        this.__Cost = __Cost;
        this.__Money = __Money;
    }

    public String get__Sub() {
        return __Sub;
    }

    public void set__Sub(String __Sub) {
        this.__Sub = __Sub;
    }

    public String get__NumberTC() {
        return __NumberTC;
    }

    public void set__NumberTC(String __NumberTC) {
        this.__NumberTC = __NumberTC;
    }

    public String get__Cost() {
        return __Cost;
    }

    public void set__Cost(String __Cost) {
        this.__Cost = __Cost;
    }

    public String get__Money() {
        return __Money;
    }

    public void set__Money(String __Money) {
        this.__Money = __Money;
    }
}
