package com.example.css.base.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.css.base.R;
import com.example.socialauth.facebook.FBInfo;
import com.example.socialauth.facebook.FacebookLoginHelper;
import com.example.socialauth.google.GoogleInfo;
import com.example.socialauth.google.GoogleLoginHelper;
import com.example.socialauth.result.SocialResultListener;
import com.example.socialauth.twitter.TwitterInfo;
import com.example.socialauth.twitter.TwitterLoginHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button google, twitter, linkedIn;
    Button facebook, git, instagram;
    FacebookLoginHelper helper;
    GoogleLoginHelper googleLoginHelper;
    TwitterLoginHelper twitterLoginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printHashKey(MainActivity.this);
        facebook = (Button) findViewById(R.id.facebook);
        google = (Button) findViewById(R.id.google);
        linkedIn = (Button) findViewById(R.id.linkedin);
        twitter = (Button) findViewById(R.id.twitter);
        git = (Button) findViewById(R.id.github);
        instagram = (Button) findViewById(R.id.instagram);
        facebook.setOnClickListener(this);
        google.setOnClickListener(this);
        linkedIn.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100) {
            googleLoginHelper.onActivityResult(requestCode,resultCode,data);
        }else if(requestCode==64206)
        {
            helper.onActivityResult(requestCode,resultCode,data);
        }else {
            twitterLoginHelper.onActivityResult(requestCode,resultCode,data);
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
        }
    }

    public void onFBLogin() {
        helper = new FacebookLoginHelper(MainActivity.this, "703379750037772", new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {

            }

            @Override
            public void onSignInSuccess(Object authToken, String userId, String user) {

                FBInfo fbInfo=(FBInfo)authToken;
               // showMessage(fbInfo.fname +fbInfo.email);
            }

            @Override
            public void onSignOut() {

            }
        });
        helper.performSignIn();
    }

    public void onTwitterLogin() {
        twitterLoginHelper = new TwitterLoginHelper(MainActivity.this, getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {

            }

            @Override
            public void onSignInSuccess(Object authToken, String userId, String user) {
                TwitterInfo twitterInfo=(TwitterInfo)authToken;
                //showMessage(twitterInfo.email);

            }


            @Override
            public void onSignOut() {

            }
        });
        twitterLoginHelper.performSignIn();
    }

    public void onGoogleLogin() {
        googleLoginHelper = new GoogleLoginHelper(MainActivity.this, "AIzaSyAgKu_LvQWQ9n7hiORUEvWo-60fbdSbnrc", new SocialResultListener() {
            @Override
            public void onSignInFail(String errorMessage) {
                showMessage(errorMessage);
            }

            @Override
            public void onSignInSuccess(Object authToken, String userId, String user) {
                GoogleInfo info=(GoogleInfo)authToken;

               // showMessage(info.email);
            }


            @Override
            public void onSignOut() {

            }


        });


     googleLoginHelper.performSignIn();

    }

    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("printHashKe ", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("printHashKey() Hash Key: ", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("printHashKey() Hash Key: ", "printHashKey()", e);
        }
    }


}
