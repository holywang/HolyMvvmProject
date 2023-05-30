package com.mvvm.holyandroid.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm.core.jetpack.viewmodel.BaseViewModel;
import com.mvvm.holyandroid.model.LoginModel;

/**
 * @author rg wang
 * created on  2023/5/24
 */
public class LoginViewModel extends BaseViewModel {
    private MutableLiveData<LoginModel> mData = new MutableLiveData<>();

    public LiveData<LoginModel> getData() {
        return mData;
    }
}
