package com.example.css.base.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.css.base.R;

import com.example.socialauth.facebook.FacebookLoginHelper;
import com.example.socialauth.github.GithubLoginHelper;
import com.example.socialauth.google.GoogleLoginHelper;
import com.example.socialauth.instagram.InstagramLoginHelper;
import com.example.socialauth.linkedin.LinkedInLoginHelper;
import com.example.socialauth.result.SocialResultListener;
import com.example.socialauth.twitter.TwitterLoginHelper;
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
        helper = new FacebookLoginHelper(MainActivity.this, "302975346940807", new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {

            }

            @Override
            public void onSignInSuccess(String authToken, String userId, InstagramUser user) {

                showMessage(authToken);
            }

            @Override
            public void onSignOut() {

            }
        });
        helper.performSignIn(MainActivity.this);
    }

    public void onTwitterLogin() {
        twitterLoginHelper = new TwitterLoginHelper(MainActivity.this, getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {

            }

            @Override
            public void onSignInSuccess(String authToken, String userId, InstagramUser user) {
                showMessage(authToken);

            }


            @Override
            public void onSignOut() {

            }
        });
        twitterLoginHelper.performSignIn();
    }

    public void onGoogleLogin() {
        googleLoginHelper = new GoogleLoginHelper(MainActivity.this, "AIzaSyDctVc0CGhVOt6f3YAWua9X1f0hWXgSZqs", new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {

            }

            @Override
            public void onSignInSuccess(String authToken, String userId, InstagramUser user) {
                showMessage(authToken);
            }


            @Override
            public void onSignOut() {

            }


        });

        googleLoginHelper.performSignIn();


    }

    void onLinkedInLogin() {
        linkedInLoginHelper = new LinkedInLoginHelper(MainActivity.this, new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {

            }

            @Override
            public void onSignInSuccess(String authToken, String userId, InstagramUser user) {
                showMessage(authToken);

            }

            @Override
            public void onSignOut() {

            }

        });

        linkedInLoginHelper.performSignIn();
    }

    public void onGitHubLogin() {
        githubLoginHelper = new GithubLoginHelper(MainActivity.this, new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {
                showMessage(errorMessage);
            }

            @Override
            public void onSignInSuccess(String authToken, String userId, InstagramUser user) {

                showMessage(authToken);
            }

            @Override
            public void onSignOut() {

            }
        });

        githubLoginHelper.performSignIn();

    }

    public void onInstaGramLogin() {
        instagramLoginHelper = new InstagramLoginHelper(MainActivity.this, "8a9feb73aed44f65bba04709c228afd5", "https://www.zencode.guru", new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {

            }

            @Override
            public void onSignInSuccess(String authToken, String userId, InstagramUser user) {

            }


            @Override
            public void onSignOut() {

            }
        });
        instagramLoginHelper.performSignIn();

    }


}
