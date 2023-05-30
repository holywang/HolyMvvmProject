package com.mvvm.core.fragment;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.mvvm.core.log.Logger;

/**
 * @author rg wang
 * created on  2023/5/16
 */
public class BaseFragment extends Fragment {

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
