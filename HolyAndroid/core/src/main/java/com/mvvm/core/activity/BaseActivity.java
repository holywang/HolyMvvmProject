package com.mvvm.core.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mvvm.core.log.Logger;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    protected void jumpActivity(Context context, Class clazz) {
        Logger.d("jumpActivity",clazz.getName());
        Intent it = new Intent();
        it.setClass(context, clazz);
        startActivity(it);
    }

    protected void jumpActivity(Context context, Class clazz, Object data) {
        Intent it = new Intent();

        it.setClass(context, clazz);
        startActivity(it);
    }
}