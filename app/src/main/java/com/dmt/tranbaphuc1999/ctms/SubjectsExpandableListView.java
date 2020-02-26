package com.dmt.tranbaphuc1999.ctms;

public class SubjectsExpandableListView {
    private int __icon;
    private String __title;

    //Getter and Setter
    public int get__icon() {
        return __icon;
    }

    public void set__icon(int __icon) {
        this.__icon = __icon;
    }

    public String get__title() {
        return __title;
    }


    public void set__title(String __title) {
        this.__title = __title;
    }

    //Constructor
    public SubjectsExpandableListView( int __icon, String __title) {
        this.__icon = __icon;
        this.__title = __title;
    }
}
