package com.mvvm.holyandroid.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm.core.jetpack.viewmodel.BaseViewModel;

/**
 * @author rg wang
 * created on  2023/5/23
 */
public class MainViewModel extends BaseViewModel  {

    private MutableLiveData<String> mData = new MutableLiveData<>();

    public LiveData<String> getData() {
        return mData;
    }

    @Override
    protected void onCreate() {
        loadData();
    }

    private void loadData() {

    }
}
