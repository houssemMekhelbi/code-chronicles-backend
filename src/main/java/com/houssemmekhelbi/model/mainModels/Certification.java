package com.houssemmekhelbi.model.mainModels;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "cc_certification")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date year;
    private String details;

    //constructors
    public Certification() {
    }

    public Certification(Long id, String name, Date year, String details) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.details = details;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getYear() {
        return year;
    }

    public String getDetails() {
        return details;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    // toString()
    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", details='" + details + '\'' +
                '}';
    }
}
