package com.mvvm.core.jetpack.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.mvvm.core.jetpack.lifecycle.LifeCycleTemplate;

/**
 * @author rg wang
 * created on  2023/5/24
 */
public abstract class BaseViewModel extends ViewModel implements LifeCycleTemplate {

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                onCreate();
                break;
            case ON_START:
                onStart();
                break;
            case ON_RESUME:
                onResume();
                break;
            case ON_PAUSE:
                onPause();
                break;
            case ON_STOP:
                onStop();
                break;
            case ON_DESTROY:
                onDestroy();
                break;
            case ON_ANY:
                onAny();
                break;
            default:
        }
    }

    protected void onCreate(){}
    protected void onStart(){}
    protected void onResume(){}
    protected  void onPause(){}
    protected void onStop(){}
    protected void onDestroy(){}
    protected void onAny(){}

}
