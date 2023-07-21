package com.mvvm.core.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mvvm.core.log.Logger;

/**
 * @author rg wang
 * created on  2023/7/17
 */
public class BaseDialog extends Dialog {
    Context context;

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (context != null) {
            context = null;
        }
    }

    protected void jumpActivity(Class clazz) {
        Logger.d("jumpActivity", clazz.getName());
        Intent it = new Intent();
        it.setClass(context, clazz);
        context.startActivity(it);
    }

    protected void jumpActivity(Class clazz, Object data) {
        Intent it = new Intent();

        it.setClass(context, clazz);
        context.startActivity(it);
    }
}
