package com.mvvm.holyandroid.utils;

import android.app.Activity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mvvm.holyandroid.R;

/**
 * @author rg wang
 * created on  2023/7/19
 */
public class LoginUtils {
    public static SignInClient oneTapClient;
    public static BeginSignInRequest signInRequest;

    public static void googleLogin(Activity context, OnSuccessListener<BeginSignInResult> listener, OnFailureListener failureListener){

        oneTapClient = Identity.getSignInClient(context);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(context.getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(context, listener).addOnFailureListener(failureListener);
    }
}