package com.houssemmekhelbi.model.payload;

public class ReferenceRequest {
    private String name;
    private String email;
    private String position;
    private String picture;
    private String review;

    //constructors

    public ReferenceRequest() {
    }

    public ReferenceRequest(String name, String email, String position, String picture, String review) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.picture = picture;
        this.review = review;
    }

    //Getters


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
}
