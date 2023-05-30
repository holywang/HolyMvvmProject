package com.mvvm.holyandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.mvvm.core.activity.BaseActivity;
import com.mvvm.holyandroid.R;
import com.mvvm.holyandroid.databinding.ActivityLoginBinding;
import com.mvvm.holyandroid.viewmodel.LoginViewModel;
import com.mvvm.holyandroid.viewmodel.MainViewModel;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(LoginViewModel.class);
        model.getData().observe(this, data -> {

        });
    }
}