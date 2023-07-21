package com.mvvm.holyandroid.dialog;

import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mvvm.core.dialog.BaseDialog;
import com.mvvm.holyandroid.R;
import com.mvvm.holyandroid.activity.LoginActivity;
import com.mvvm.holyandroid.utils.LoginUtils;

/**
 * @author rg wang
 * created on  2023/7/17
 */
public class ThirdPartyDialog extends BaseDialog implements View.OnClickListener {

    LinearLayout login_with_email,
            login_with_apple,
            login_with_facebook,
            login_with_google;
    TextView
            tp_login_dialog_sign_up_btn;
    public static final int GOOGLE_LOGIN = 0001;

    public static final String THIRD_PARTY_LOGIN = "THIRD_PARTY_LOGIN";

    public ThirdPartyDialog(@NonNull Context context) {
        super(context);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attribute = window.getAttributes();
        attribute.width = WindowManager.LayoutParams.MATCH_PARENT;
        attribute.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(attribute);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.third_party_login_dialog);
        login_with_email = findViewById(R.id.login_with_email);
        login_with_apple = findViewById(R.id.login_with_apple);
        login_with_facebook = findViewById(R.id.login_with_facebook);
        login_with_google = findViewById(R.id.login_with_google);
        tp_login_dialog_sign_up_btn = findViewById(R.id.tp_login_dialog_sign_up_btn);
        login_with_email.setOnClickListener(this);
        login_with_apple.setOnClickListener(this);
        login_with_facebook.setOnClickListener(this);
        login_with_google.setOnClickListener(this);
        tp_login_dialog_sign_up_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_with_email) {
            loginWithEmail();
        } else if (v.getId() == R.id.login_with_apple) {
            loginWithApple();
        } else if (v.getId() == R.id.login_with_facebook) {
            loginWithFacebook();
        } else if (v.getId() == R.id.login_with_google) {
            loginWithGoogle();
        } else if (v.getId() == R.id.tp_login_dialog_sign_up_btn) {
            signUp();
        }
    }

    private void loginWithEmail() {
        jumpActivity(LoginActivity.class);
    }

    private void loginWithApple() {

    }

    private void loginWithFacebook() {

    }

    private void loginWithGoogle() {
        LoginUtils.googleLogin(getOwnerActivity(),
                beginSignInResult -> {
                    try {
                        getOwnerActivity().startIntentSenderForResult(beginSignInResult.getPendingIntent().getIntentSender(), GOOGLE_LOGIN,
                                null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(THIRD_PARTY_LOGIN, e.getLocalizedMessage());
                    }
                },
                e -> {
                    Log.e(THIRD_PARTY_LOGIN, e.getLocalizedMessage());
                });
    }


    private void signUp() {

    }
}
