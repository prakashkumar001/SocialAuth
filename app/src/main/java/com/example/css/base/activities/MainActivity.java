package com.example.css.base.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.css.base.R;

import com.example.socialauth.facebook.FacebookLoginHelper;
import com.example.socialauth.facebook.FacebookLoginListener;
import com.example.socialauth.github.GithubLoginHelper;
import com.example.socialauth.github.GithubLoginListener;
import com.example.socialauth.google.GoogleLoginHelper;
import com.example.socialauth.google.GoogleLoginListener;
import com.example.socialauth.instagram.InstagramLoginHelper;
import com.example.socialauth.instagram.InstagramLoginListener;
import com.example.socialauth.linkedin.LinkedInLoginHelper;
import com.example.socialauth.linkedin.LinkedInLoginListener;
import com.example.socialauth.twitter.TwitterLoginHelper;
import com.example.socialauth.twitter.TwitterLoginListener;
import com.steelkiwi.instagramhelper.model.InstagramUser;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button google, twitter, linkedIn;
    Button facebook, git, instagram;
    FacebookLoginHelper helper;
    GoogleLoginHelper googleLoginHelper;
    TwitterLoginHelper twitterLoginHelper;
    LinkedInLoginHelper linkedInLoginHelper;
    GithubLoginHelper githubLoginHelper;
    InstagramLoginHelper instagramLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        facebook = (Button) findViewById(R.id.facebook);
        google = (Button) findViewById(R.id.google);
        linkedIn = (Button) findViewById(R.id.linkedin);
        twitter = (Button) findViewById(R.id.twitter);
        git = (Button) findViewById(R.id.github);
        instagram = (Button) findViewById(R.id.instagram);
        facebook.setOnClickListener(this);
        google.setOnClickListener(this);
        linkedIn.setOnClickListener(this);
        twitter.setOnClickListener(this);
        git.setOnClickListener(this);
        instagram.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100) {
            googleLoginHelper.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 1000) {
            githubLoginHelper.onActivityResult(requestCode, resultCode, data);
        }

        // super.onActivityResult(requestCode, resultCode, data);
        //showMessage("success");

    }


    @Override
    public void onClick(View v) {

        if (v == facebook) {
            onFBLogin();

        } else if (v == google) {
            onGoogleLogin();
        } else if (v == twitter) {
            onTwitterLogin();
        } else if (v == linkedIn) {
            onLinkedInLogin();
        } else if (v == git) {
            onGitHubLogin();
        } else if (v == instagram) {
            onInstaGramLogin();
        }
    }

    public void onFBLogin() {
        helper = new FacebookLoginHelper(MainActivity.this, new FacebookLoginListener() {
            @Override
            public void onFbSignInFail(String errorMessage) {

            }

            @Override
            public void onFbSignInSuccess(String authToken, String userId) {

                showMessage(authToken);
            }

            @Override
            public void onFBSignOut() {

            }
        }, "302975346940807");
        helper.performSignIn(MainActivity.this);
    }

    public void onTwitterLogin() {
        twitterLoginHelper = new TwitterLoginHelper(MainActivity.this, getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), new TwitterLoginListener() {
            @Override
            public void onTwitterSignInFail(String errorMessage) {

            }

            @Override
            public void onTwitterSignInSuccess(String authToken, String userId) {

                showMessage(authToken);
            }

            @Override
            public void onTwitterSignOut() {

            }
        });
        twitterLoginHelper.performSignIn(MainActivity.this);
    }

    public void onGoogleLogin() {
        googleLoginHelper = new GoogleLoginHelper(new GoogleLoginListener() {
            @Override
            public void onGoogleAuthSignIn(String authToken, String userId) {
                showMessage(authToken);

            }

            @Override
            public void onGoogleAuthSignInFailed(String errorMessage) {
                showMessage(errorMessage);
            }

            @Override
            public void onGoogleAuthSignOut() {

            }
        }, MainActivity.this, "AIzaSyDctVc0CGhVOt6f3YAWua9X1f0hWXgSZqs");

        googleLoginHelper.performSignIn(MainActivity.this);


    }

    void onLinkedInLogin() {
        linkedInLoginHelper = new LinkedInLoginHelper(new LinkedInLoginListener() {
            @Override
            public void onLinkedInAuthSignIn(String authToken, String userId) {

                showMessage(authToken);
            }

            @Override
            public void onLinkedInAuthSignInFailed(String errorMessage) {

            }

            @Override
            public void onLinkedInAuthSignOut() {

            }
        }, MainActivity.this, "");

        linkedInLoginHelper.performSignIn(MainActivity.this);
    }

    public void onGitHubLogin() {
        githubLoginHelper = new GithubLoginHelper(MainActivity.this, new GithubLoginListener() {
            @Override
            public void onGitSignInFail(String errorMessage) {
                showMessage(errorMessage);
            }

            @Override
            public void onGitSignInSuccess(String authToken, String userId) {

                showMessage(authToken);
            }

            @Override
            public void onGitSignOut() {

            }
        });

        githubLoginHelper.performSignIn(MainActivity.this);

    }

    public void onInstaGramLogin() {
        instagramLoginHelper = new InstagramLoginHelper(MainActivity.this, "8a9feb73aed44f65bba04709c228afd5", "https://www.zencode.guru", new InstagramLoginListener() {
            @Override
            public void onInstagramSignInFail(String errorMessage) {

            }

            @Override
            public void onInstagramSignInSuccess(InstagramUser user) {

            }

            @Override
            public void onInstagramSignOut() {

            }
        });
        instagramLoginHelper.performSignIn(MainActivity.this);

    }


}
