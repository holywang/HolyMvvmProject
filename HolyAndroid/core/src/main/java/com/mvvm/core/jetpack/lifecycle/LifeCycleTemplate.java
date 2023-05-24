package com.mvvm.core.jetpack.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author rg wang
 * created on  2023/5/23
 */
public interface LifeCycleTemplate extends LifecycleEventObserver {

    @Override
    void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event);
}
