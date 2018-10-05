package com.example.socialauth.github;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.socialauth.github.lib.GithubOauth;
import com.example.socialauth.result.SocialResultListener;
import com.facebook.login.LoginManager;


import java.util.Arrays;

public class GithubLoginHelper {
    public static final String TAG = "GITHUB";
    public static final String PREFERENCE = "github_prefs";

    private SocialResultListener loginListener;
    Activity activity;
    public GithubLoginHelper(Activity activity,SocialResultListener loginListener)
    {
        this.activity=activity;
        this.loginListener=loginListener;


    }
    /** to perform the login from your activity
     */
    public void performSignIn() {
        GithubOauth
                .Builder()
                .withClientId("84e934a861c18661c139")
                .withClientSecret("c43b8be29783216d00fd4b227effc5642b92743f")
                .withContext(activity)
                .packageName("com.example.css.base")
                //.nextActivity("com.hardikgoswami.github_oauth_lib.UserActivity")
                .debug(true)
                .execute();



    }
    /**
     * just overwrite onActivityResult in your Activity / Fragment to fetch the result
     * if developer will forget to overwrite this method login he will not able to fetch the result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFERENCE, 0);
            String oauthToken = sharedPreferences.getString("oauth_token", null);
            Log.e(TAG, "oauth token for github loged in user is :" + oauthToken);
            //Toast.makeText(activity,"Entered",Toast.LENGTH_SHORT).show();
            if(oauthToken!=null)
            {
                loginListener.onSignInSuccess(oauthToken,"",null);
            }else
            {
                loginListener.onSignInFail("Authentication Failed");
            }

        }else
        {
            loginListener.onSignInFail("Authentication Failed");
        }
    }


}
