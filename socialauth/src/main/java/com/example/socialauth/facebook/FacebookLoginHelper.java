package com.example.socialauth.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.socialauth.result.SocialResultListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Shubham Jain on 28/4/17 2:11 PM
 */


public class FacebookLoginHelper {

    private SocialResultListener mListener;
    private CallbackManager mCallBackManager;
    private Activity activity;
    public FBInfo info ;

    /**
     * FacebookLoginHelper is help full to developer where he want to login either in activity or in fragment and deliver the result there
     *
     * @param facebookLoginListener to listen the status of the facebook login status what status is
     *                              get will be delivered to this called listener
     */
    public FacebookLoginHelper( Activity activity,String appId,@NonNull final SocialResultListener facebookLoginListener) {
        FacebookSdk.sdkInitialize(activity);
        FacebookSdk.setApplicationId(appId);
        mListener = facebookLoginListener;
        this.activity=activity;
        mCallBackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallBackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                       getFbInfo(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @NonNull
    @CheckResult

    public CallbackManager getCallbackManager() {
        return mCallBackManager;
    }


    /** to perform the login from your activity
     */
    public void performSignIn() {
        LoginManager.getInstance()
                .logInWithReadPermissions(activity,
                        Arrays.asList("public_profile", "email"));

    }

    /**
     * to perform the login from your fragment
     * @param fragment reference of your fragment
     */
    public void performSignIn(Fragment fragment) {
        LoginManager.getInstance()
                .logInWithReadPermissions(fragment,
                        Arrays.asList("public_profile", "email"));
    }

    /**
     * just overwrite onActivityResult in your Activity / Fragment to fetch the result
     * if developer will forget to overwrite this method login he will not able to fetch the result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * when ever user want to preform sign-out he need to call this method
     */
    public void performSignOut() {
        FacebookSdk.sdkInitialize(activity);
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // user already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
                mListener.onSignOut();
            }
        }).executeAsync();
    }

    public void getFbInfo(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response",response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String id = response.getJSONObject().getString("id");

                            FBInfo info=new FBInfo(id,firstName,lastName,email);
                            mListener.onSignInSuccess(info,"",null);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();

    }
}
