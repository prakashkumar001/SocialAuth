package com.example.socialauth.result;


public interface SocialResultListener {

    /**
     *
     * @param errorMessage  if login fail then error log
     */
    void onSignInFail(String errorMessage);

    /** on success get these parameter
     * @param data -facebook authentication
     * @param userId -userID
     */
    void onSignInSuccess(Object data, String userId, String user);



    /**
     * notify when social logout action perform
     */
    void onSignOut();
}
