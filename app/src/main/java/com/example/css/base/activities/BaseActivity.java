package com.example.css.base.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.css.base.R;
import com.example.css.base.interfaces.APIInterface;
import com.example.css.base.interfaces.BaseView;
import com.example.css.base.interfaces.Notify;
import com.example.css.base.interfaces.Result;
/*import com.example.css.base.retrofit.APIClient;*/

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaseActivity extends AppCompatActivity implements BaseView,Notify {
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void callApi(String key,final Result result)
    {
        result.onPreExecute();
        Call call=null;

     /*   APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        if(key.equalsIgnoreCase("Doget"))
        {
             call=apiInterface.doGetListResources();
        }else if(key.equalsIgnoreCase("Login"))
        {
             call = apiInterface.doGetUserList("1");
        }*/


        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                Object object=new Object();
                object=(Object) response.body();
                result.onSucess(object,1);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                result.onFailure(t.getMessage(),0);
                call.cancel();
            }
        });


    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgessDialog(String title,String message) {
        progressDialog=new ProgressDialog(BaseActivity.this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();

    }

    @Override
    public void hideProgressDialog() {
     progressDialog.dismiss();
    }

    @Override
    public void addFragment(int containerViewId, @NonNull Fragment fragment, @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    public void startActivityIntent(Context a, Class b,Bundle bundle) {
        Intent i=new Intent(a,b);
        i.putExtra("data",bundle);
        startActivity(i);
    }


    @Override
    public void getNotification(Context a, Class b) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(a)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(a, b);
        PendingIntent contentIntent = PendingIntent.getActivity(a, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) a.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
