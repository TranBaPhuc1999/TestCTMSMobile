package com.dmt.tranbaphuc1999.ctms;

public class List_Teacher {
    private String __nameTeacher;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get__nameTeacher() {
        return __nameTeacher;
    }

    public void set__nameTeacher(String __nameTeacher) {
        this.__nameTeacher = __nameTeacher;
    }

    public List_Teacher(String __nameTeacher, int id) {
        this.__nameTeacher = __nameTeacher;
        this.id = id;
    }
}
