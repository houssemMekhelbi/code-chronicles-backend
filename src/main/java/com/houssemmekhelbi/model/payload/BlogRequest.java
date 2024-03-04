package com.houssemmekhelbi.model.payload;

import java.sql.Date;

public class BlogRequest {
    private String pricture;
    private String title;
    private String link;
    private Date date;

    //Constructors

    public BlogRequest() {
    }

    public BlogRequest(String pricture, String title, String link, Date date) {
        this.pricture = pricture;
        this.title = title;
        this.link = link;
        this.date = date;
    }

    //Getters

    public String getPricture() {
        return pricture;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Date getDate() {
        return date;
    }
}
