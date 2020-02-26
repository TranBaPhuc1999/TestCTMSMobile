package com.dmt.tranbaphuc1999.ctms;

public class Schedule_Class {
    private String __TimeStart, __TimeEnd, __Room, __Teacher,__Subject;
    private int __IconStatus, __Background;

    //Getter and Setter
    public String get__TimeStart() {
        return __TimeStart;
    }

    public void set__TimeStart(String __TimeStart) {
        this.__TimeStart = __TimeStart;
    }

    public String get__TimeEnd() {
        return __TimeEnd;
    }

    public void set__TimeEnd(String __TimeEnd) {
        this.__TimeEnd = __TimeEnd;
    }

    public String get__Room() {
        return __Room;
    }

    public void set__Room(String __Room) {
        this.__Room = __Room;
    }

    public String get__Teacher() {
        return __Teacher;
    }

    public void set__Teacher(String __Teacher) {
        this.__Teacher = __Teacher;
    }

    public String get__Subject() {
        return __Subject;
    }

    public void set__Subject(String __Subject) {
        this.__Subject = __Subject;
    }

    public int get__IconStatus() {
        return __IconStatus;
    }

    public void set__IconStatus(int __IconStatus) {
        this.__IconStatus = __IconStatus;
    }

    public int get__Background() {
        return __Background;
    }

    public void set__Background(int __Background) {
        this.__Background = __Background;
    }

    //Constructor
    public Schedule_Class(String __TimeStart, String __TimeEnd, String __Room, String __Teacher, String __Subject, int __IconStatus,int __Background) {

        this.__TimeStart = __TimeStart;
        this.__TimeEnd = __TimeEnd;
        this.__Room = __Room;
        this.__Teacher = __Teacher;
        this.__Subject = __Subject;
        this.__IconStatus = __IconStatus;
        this.__Background = __Background;
    }
}
