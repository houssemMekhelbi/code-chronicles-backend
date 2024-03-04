package com.houssemmekhelbi.model.payload;

import java.sql.Date;

public class ExperienceRequest {
    private String title;
    private Date start_date;
    private Date end_date;
    private String company;
    private String work_location;
    private String project_name;
    private String project_description;

    // Constructors
    public ExperienceRequest() {
    }

    public ExperienceRequest(String title, Date start_date, Date end_date, String company, String work_location, String project_name, String project_description) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.company = company;
        this.work_location = work_location;
        this.project_name = project_name;
        this.project_description = project_description;
    }

    // Getters
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
}
