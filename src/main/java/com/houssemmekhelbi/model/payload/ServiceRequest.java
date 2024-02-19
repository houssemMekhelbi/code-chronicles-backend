package com.houssemmekhelbi.model.payload;

public class ServiceRequest {
    private String name;
    private String description;
    private String logoUrl;

    // Constructors
    public ServiceRequest() {
    }

    public ServiceRequest(String name, String description, String logoUrl) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

}

