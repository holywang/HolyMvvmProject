package com.mvvm.holyandroid.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm.core.jetpack.viewmodel.BaseViewModel;
import com.mvvm.holyandroid.model.ThirdPartyLoginModel;

/**
 * @author rg wang
 * created on  2023/7/17
 */
public class ThirdPartyLoginViewModel extends BaseViewModel {

    private MutableLiveData<ThirdPartyLoginModel> mData = new MutableLiveData<>();

    public LiveData<ThirdPartyLoginModel> getData() {
        return mData;
    }

}
