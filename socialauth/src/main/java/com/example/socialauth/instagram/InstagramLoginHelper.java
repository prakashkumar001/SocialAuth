package com.example.socialauth.instagram;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.socialauth.result.SocialResultListener;
import com.facebook.login.LoginManager;
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
    /**
     * to perform the login from your fragment
     */
    public void performSignIn() {

        instagramHelper.loginFromActivity(activity);
    }

    /**
     * just overwrite onActivityResult in your Activity / Fragment to fetch the result
     * if developer will forget to overwrite this method login he will not able to fetch the result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == InstagramHelperConstants.INSTA_LOGIN && resultCode == RESULT_OK) {
            InstagramUser user = instagramHelper.getInstagramUser(activity);
          /*  Picasso.with(activity).load(user.getData().getProfilePicture()).into(userPhoto);
            userTextInfo.setText(user.getData().getUsername() + "\n"
                    + user.getData().getFullName() + "\n"
                    + user.getData().getWebsite()
            );*/
          loginListener.onSignInSuccess(null,null,user);

        } else {
            loginListener.onSignInFail("Login Failed");
            Toast.makeText(activity, "Login failed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * when ever user want to preform sign-out he need to call this method
     */
    public void performSignOut() {

        loginListener.onSignOut();
    }
}
