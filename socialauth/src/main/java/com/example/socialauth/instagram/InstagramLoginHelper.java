package com.example.socialauth.instagram;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;


import com.example.socialauth.result.SocialResultListener;
import com.squareup.picasso.Picasso;
import com.steelkiwi.instagramhelper.InstagramHelper;
import com.steelkiwi.instagramhelper.InstagramHelperConstants;
import com.steelkiwi.instagramhelper.model.InstagramUser;

import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

public class InstagramLoginHelper {
    private SocialResultListener loginListener;
    private Activity activity;
    InstagramHelper instagramHelper;
    public InstagramLoginHelper(Activity activity,String cliendId,String redirectURL,SocialResultListener loginListener)
    {
        this.activity=activity;
        this.loginListener=loginListener;
        String scope = "basic+public_content+follower_list+comments+relationships+likes";
//scope is for the permissions
         instagramHelper = new InstagramHelper.Builder()
                .withClientId(cliendId)
                .withRedirectUrl(redirectURL)
                .withScope(scope)
                .build();
    }

    public void performSignIn() {

        instagramHelper.loginFromActivity(activity);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == InstagramHelperConstants.INSTA_LOGIN && resultCode == RESULT_OK) {
            InstagramUser user = instagramHelper.getInstagramUser(activity);
            //Picasso.with(activity).load(user.getData().getProfilePicture()).into(userPhoto);
            /*userTextInfo.setText(user.getData().getUsername() + "\n"
                    + user.getData().getFullName() + "\n"
                    + user.getData().getWebsite()*/

            Object object=(Object)user;
          loginListener.onSignInSuccess(object,null,null);

        } else {
            loginListener.onSignInFail("Login Failed");
            Toast.makeText(activity, "Login failed", Toast.LENGTH_LONG).show();
        }
    }


    public void performSignOut() {

        loginListener.onSignOut();
    }
}
