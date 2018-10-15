package com.example.socialauth.google;

public class GoogleInfo {
    public String name;
    public String email;
    public String id;

    public GoogleInfo(String name, String email, String id, String accesstoken) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.accesstoken = accesstoken;
    }

    public String accesstoken;
}
