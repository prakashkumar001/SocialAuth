package com.example.socialauth.result;

import com.steelkiwi.instagramhelper.model.InstagramUser;

public interface SocialResultListener {

    /**
     *
     * @param errorMessage  if login fail then error log
     */
    void onSignInFail(String errorMessage);

    /** on success get these parameter
     * @param authToken -facebook authentication
     * @param userId -userID
     */
    void onSignInSuccess(String authToken, String userId, InstagramUser user);


    /**
     * notify when social logout action perform
     */
    void onSignOut();
}
