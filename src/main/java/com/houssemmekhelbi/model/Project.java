package com.houssemmekhelbi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cc_project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keys;
    private String link;
    private String picture;

    //constructors
    public Project() {
    }
    public Project(Long id, String keys, String link, String picture) {
        this.id = id;
        this.keys = keys;
        this.link = link;
        this.picture = picture;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getKeys() {
        return keys;
    }

    public String getLink() {
        return link;
    }

    public String getPicture() {
        return picture;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    // toString()
    @Override
    public String toString() {
        return "Projects{" +
                "id=" + id +
                ", keys='" + keys + '\'' +
                ", link='" + link + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
