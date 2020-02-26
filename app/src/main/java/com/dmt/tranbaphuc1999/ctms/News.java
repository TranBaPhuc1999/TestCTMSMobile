package com.dmt.tranbaphuc1999.ctms;

public class News {
    String link, title, detail;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public News(String title, String detail, String link) {
        this.link = link;
        this.title = title;
        this.detail = detail;
    }
}
