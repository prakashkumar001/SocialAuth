package com.example.css.base.interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public interface BaseView {
    public void showMessage(String message);
    public void showProgessDialog(String title, String message);
    public void hideProgressDialog();
    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String fragmentTag);
    public void startActivityIntent(Context a, Class b, Bundle bundle);
}
