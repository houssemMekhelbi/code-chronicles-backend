package com.houssemmekhelbi.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "cc_blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pricture;
    private String title;
    private String link;
    private Date date;

    //constructors
    public Blog() {
    }
    public Blog(Long id, String pricture, String title, String link, Date date) {
        this.id = id;
        this.pricture = pricture;
        this.title = title;
        this.link = link;
        this.date = date;
    }

    //Getters
    public Long getId() {
        return id;
    }

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

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setPricture(String pricture) {
        this.pricture = pricture;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // toString()
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", pricture='" + pricture + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", date=" + date +
                '}';
    }
}
