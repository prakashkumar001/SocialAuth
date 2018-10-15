package com.example.socialauth.facebook;

public class FBInfo {
    public String id;
    public String fname;
    public String lname;

    public FBInfo(String id, String fname, String lname,String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public String email;
}
