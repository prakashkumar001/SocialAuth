package com.example.socialauth.google;

public class GoogleInfo {
    public String fname;
    public String lname;
    public String email;
    public String id;
    public String profileUrl;

    public GoogleInfo(String fname,String lname, String email, String id, String accesstoken,String profileUrl) {
        this.fname = fname;
        this.lname=lname;
        this.email = email;
        this.id = id;
        this.accesstoken = accesstoken;
        this.profileUrl=profileUrl;
    }

    public String accesstoken;
}
