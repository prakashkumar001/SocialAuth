package com.example.socialauth.google;

public class GoogleInfo {
    public String fname;
    public String lname;
    public String email;
    public String id;

    public GoogleInfo(String fname,String lname, String email, String id, String accesstoken) {
        this.fname = fname;
        this.lname=lname;
        this.email = email;
        this.id = id;
        this.accesstoken = accesstoken;
    }

    public String accesstoken;
}
