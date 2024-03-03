package com.houssemmekhelbi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cc_reference")
public class Reference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String position;
    private String picture;
    private String review;

    //constructors
    public Reference() {
    }
    public Reference(Long id, String name, String email, String position, String picture, String review) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.picture = picture;
        this.review = review;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public String getPicture() {
        return picture;
    }

    public String getReview() {
        return review;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setReview(String review) {
        this.review = review;
    }

    // toString()
    @Override
    public String toString() {
        return "Reference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", picture='" + picture + '\'' +
                ", review='" + review + '\'' +
                '}';
    }
}
