package com.example.socialauth.github;

public interface GithubLoginListener {

    /**
     *
     * @param errorMessage  if login fail then error log
     */
    void onGitSignInFail(String errorMessage);

    /** on success get these parameter
     * @param authToken -facebook authentication
     * @param userId -userID
     */
    void onGitSignInSuccess(String authToken, String userId);

    /**
     * notify when facebook logout action perform
     */
    void onGitSignOut();
}
