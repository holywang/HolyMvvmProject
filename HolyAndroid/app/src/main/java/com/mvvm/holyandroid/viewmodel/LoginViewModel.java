package com.mvvm.holyandroid.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;
import com.google.android.gms.auth.api.identity.SavePasswordResult;
import com.google.android.gms.auth.api.identity.SignInPassword;
import com.google.android.gms.tasks.OnSuccessListener;
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


    public void save(String id, String pwd, Context context, OnSuccessListener<SavePasswordResult> listener) {
        SignInPassword signInPassword = new SignInPassword(id, pwd);
        SavePasswordRequest savePasswordRequest =
                SavePasswordRequest.builder().setSignInPassword(signInPassword).build();
        Identity.getCredentialSavingClient(context)
                .savePassword(savePasswordRequest)
                .addOnSuccessListener(listener);
    }
}
