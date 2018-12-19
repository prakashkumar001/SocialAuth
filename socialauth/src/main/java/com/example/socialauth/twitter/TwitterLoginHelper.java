package com.example.socialauth.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.example.socialauth.R;
import com.example.socialauth.result.SocialResultListener;
import com.facebook.login.LoginManager;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TwitterLoginHelper {
    private SocialResultListener loginListener;
    private TwitterAuthClient client;
Activity activity;


    public  TwitterLoginHelper(final Activity activity, @Nullable String secret, @Nullable String consumerkey , @NonNull final SocialResultListener loginListener)
    {
        Twitter.initialize(activity);
        client = new TwitterAuthClient();
        TwitterConfig config = new TwitterConfig.Builder(activity)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(consumerkey,secret))
                .debug(true)
                .build();
        Twitter.initialize(config);
        this.loginListener=loginListener;
        this.activity=activity;



    }


    /**
     * get authenticates user session
     *
     * @return twitter session
     */
     TwitterSession getTwitterSession() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        //NOTE : if you want to get token and secret too use uncomment the below code
        /*TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;*/

        return session;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        client.onActivityResult(requestCode, resultCode, data);
    }


    /** to perform the login from your activity
     */
    public void performSignIn() {
        logoutTwitter();

        if (getTwitterSession() == null) {

            if(client!=null)
            {
                client.cancelAuthorize();
            }
            //if user is not authenticated start authenticating
            client.authorize(activity, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                   final TwitterSession twitterSession = result.data;

                    //loginListener.onSignInSuccess(twitterSession.getAuthToken().token,String.valueOf(twitterSession.getUserId()),null);


                    //call fetch email only when permission is granted

                    client.requestEmail(twitterSession, new com.twitter.sdk.android.core.Callback<String>() {
                        @Override
                        public void success(Result<String> emailResult) {
                            String email = emailResult.data;
                            // ...

                          /*  String[] names=twitterSession.getUserName().split("_");
                            String fname="";
                            String lname="";
                            for(int i=0;i<names.length;i++)
                            {
                                if(i==0)
                                {
                                    fname=names[0];
                                }else {
                                    lname=lname+names[i];
                                }



                            }
*/

                            String fname=twitterSession.getUserName();
                            String lname="";
                            TwitterInfo info=new TwitterInfo(String.valueOf(twitterSession.getUserId()),fname,lname,email);

                            loginListener.onSignInSuccess(info,"","");

                        }

                        @Override
                        public void failure(TwitterException e) {
                            loginListener.onSignInFail(e.toString());
                        }
                    });
                }

                @Override
                public void failure(TwitterException e) {

                    loginListener.onSignInFail(e.getMessage());
                    // Do something on failure
                    Toast.makeText(activity, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(activity, "User already authenticated", Toast.LENGTH_SHORT).show();
        }

    }

    public void logoutTwitter() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (twitterSession != null) {
            ClearCookies(getApplicationContext());
             TwitterCore.getInstance().getSessionManager().clearActiveSession();
        }
    }

    public static void ClearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }


}
