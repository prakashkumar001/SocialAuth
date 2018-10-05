package com.example.socialauth.linkedin;

public interface LinkedInLoginListener {
    /** on success get these parameter
     * @param authToken -facebook authentication
     * @param userId -userID
     */
    void onLinkedInAuthSignIn(String authToken, String userId);

    /**
     *
     * @param errorMessage  if login fail then error log
     */
    void onLinkedInAuthSignInFailed(String errorMessage);

    /**
     * notify when facebook logout action perform
     */
    void onLinkedInAuthSignOut();
}
