package com.example.socialauth.google;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;


import com.example.socialauth.result.SocialResultListener;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.IOException;

public class GoogleLoginHelper {
    private final int RC_SIGN_IN = 100;
    private FragmentActivity mContext;
    private SocialResultListener mListener;
    private GoogleSignInClient mGoogleApiClient;
    public String TAG="GOOgle";


    /**
     * GoogleLoginHelper is help full to developer where he want to login either in activity or in fragment and deliver the result there
     *
     * @param listener       to listen the status of the facebook login status what status is
     *                       get will be delivered to this called listener
     * @param activity        application context
     * @param googleApiKey server client Id from G+ login
     */
    public GoogleLoginHelper( FragmentActivity activity,
                             @Nullable String googleApiKey,@NonNull SocialResultListener listener) {
        mContext = activity;
        mListener = listener;

        buildSignInOptions(googleApiKey);
    }

    private void buildSignInOptions(@Nullable String serverClientId) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = GoogleSignIn.getClient(mContext, gso);


    }

    /**
     * to perform the login from your activity
     *
     */
    public void performSignIn() {
        Intent signInIntent = mGoogleApiClient .getSignInIntent();
        mContext.startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    /**
     * just overwrite onActivityResult in your Activity / Fragment to fetch the result
     * if developer will forget to overwrite this method login he will not able to fetch the result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleInfo info=new GoogleInfo(account.getDisplayName(),account.getEmail(),account.getId(),account.getIdToken());
           // Toast.makeText(mContext,account.getEmail(),Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
           // updateUI(account);
            mListener.onSignInSuccess(info,"","");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
           // updateUI(null);
            mListener.onSignInFail("Authentication Failed");
        }
    }

}