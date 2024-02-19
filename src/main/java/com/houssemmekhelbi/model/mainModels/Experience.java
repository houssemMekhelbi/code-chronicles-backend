package com.houssemmekhelbi.model.mainModels;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "cc_experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date start_date;
    private Date end_date;
    private String company;
    private String work_location;
    private String project_name;
    private String project_description;

    //constructors
    public Experience() {
    }
    public Experience(Long id, String title, Date start_date, Date end_date, String company, String work_location,
                      String project_name, String project_description) {
        this.id = id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.company = company;
        this.work_location = work_location;
        this.project_name = project_name;
        this.project_description = project_description;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getCompany() {
        return company;
    }

    public String getWork_location() {
        return work_location;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getProject_description() {
        return project_description;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setWork_location(String work_location) {
        this.work_location = work_location;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    //toString()
    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", company='" + company + '\'' +
                ", work_location='" + work_location + '\'' +
                ", project_name='" + project_name + '\'' +
                ", project_description='" + project_description + '\'' +
                '}';
    }
}
