package com.example.socialauth.twitter;

public interface TwitterLoginListener {
    /**
     *
     * @param errorMessage  if login fail then error log
     */
    void onTwitterSignInFail(String errorMessage);

    /** on success get these parameter
     * @param authToken -onTwitter authentication
     * @param userId -userID
     */
    void onTwitterSignInSuccess(String authToken, String userId);

    /**
     * notify when onTwitter logout action perform
     */
    void onTwitterSignOut();
}
