package com.example.css.base.interfaces;

import com.google.gson.JsonObject;

import org.json.JSONObject;

public interface Result  {

    public void onPreExecute();
    public void onSucess(Object object, int resultCode);
    public void onFailure(String message, int resultCode);
}
