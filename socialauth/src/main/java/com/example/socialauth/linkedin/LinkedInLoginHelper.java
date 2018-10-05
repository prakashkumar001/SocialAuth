package com.example.socialauth.linkedin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import java.security.MessageDigest;
import java.util.Arrays;

public class LinkedInLoginHelper {
    Activity activity;
    LinkedInLoginListener linkedInLoginListener;

    public LinkedInLoginHelper(final LinkedInLoginListener linkedInLoginListener, final Activity activity, String key)
    {
        this.activity=activity;
        this.linkedInLoginListener=linkedInLoginListener;

    }


    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    /**
     * to perform the login from your fragment
     * @param activity reference of your fragment
     */
    public void performSignIn(final Activity activity) {
        computePakageHash();
        LISessionManager.getInstance(activity).init(activity, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.

                //fetchPersonalInfo();
                linkedInLoginListener.onLinkedInAuthSignIn(LISessionManager.getInstance(activity).getSession().getAccessToken().toString(),"");
            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                Log.e("NIKHIL",error.toString());
            }
        }, true);
    }
    /**
     * just overwrite onActivityResult in your Activity / Fragment to fetch the result
     * if developer will forget to overwrite this method login he will not able to fetch the result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(activity).onActivityResult(activity, requestCode, resultCode, data);
    }
    public void computePakageHash() {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "com.example.css.base",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            Log.e("TAG",e.getMessage());
        }
    }
}
