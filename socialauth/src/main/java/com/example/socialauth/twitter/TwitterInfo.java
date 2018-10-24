package com.example.socialauth.twitter;

public class TwitterInfo {
    public String id;
    public String fname;
    public String lname;

    public TwitterInfo(String id, String fname, String lname,String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public String email;
}
