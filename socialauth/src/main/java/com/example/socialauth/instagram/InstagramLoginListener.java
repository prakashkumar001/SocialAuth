package com.example.socialauth.instagram;

import com.steelkiwi.instagramhelper.model.InstagramUser;

public interface InstagramLoginListener {
    /**
     *
     * @param errorMessage  if login fail then error log
     */
    void onInstagramSignInFail(String errorMessage);

    /** on success get these parameter

     */
    void onInstagramSignInSuccess(InstagramUser instagramUser);

    /**
     * notify when facebook logout action perform
     */
    void onInstagramSignOut();
}
