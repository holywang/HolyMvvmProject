package com.mvvm.holyandroid;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.mvvm.core.activity.BaseActivity;
import com.mvvm.holyandroid.callback.SplashFinishCallback;
import com.mvvm.holyandroid.databinding.ActivityMainBinding;
import com.mvvm.holyandroid.fragment.MainFragment;
import com.mvvm.holyandroid.fragment.SplashFragment;
import com.mvvm.holyandroid.viewmodel.MainViewModel;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseActivity implements SplashFinishCallback {
    private ActivityMainBinding binding;
    private MainViewModel mViewModel;

    private Handler mHandler = new Handler();
    private SplashFragment splashFragment;
    private MainFragment mainFragment;
    private ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getData().observe(this, data -> {
            // TODO: 2023/5/23 更新UI
        });

        viewStub = findViewById(R.id.content_view_stub);
        splash();
    }

    private void splash() {
        splashFragment = new SplashFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, splashFragment);
        transaction.commit();

        getWindow().getDecorView().post(() -> {
            // 开启延迟加载
            mHandler.post(() -> {
                //将viewStub加载进来
                viewStub.inflate();
                this.initView();
            });
        });
        getWindow().getDecorView().post(() -> {
            // 开启延迟加载,也可以不用延迟可以立马执行（我这里延迟是为了实现fragment里面的动画效果的耗时）
            mHandler.postDelayed(new DelayRunnable(MainActivity.this, splashFragment), 2000);
        });
    }

    @Override
    public void initView() {
        mainFragment = MainFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, mainFragment);
        transaction.commit();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    static class DelayRunnable implements Runnable {

        private WeakReference<Context> contextWeakReference;
        private WeakReference<SplashFragment> splashFragmentWeakReference;

        public DelayRunnable(Context context, SplashFragment f) {
            contextWeakReference = new WeakReference<Context>(context);
            splashFragmentWeakReference = new WeakReference<SplashFragment>(f);
        }

        @Override
        public void run() {
            //移除Fragment
            if (contextWeakReference != null) {
                SplashFragment splashFragment = splashFragmentWeakReference.get();
                if (splashFragment == null) {
                    return;
                }
                FragmentActivity activity = (FragmentActivity) contextWeakReference.get();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.remove(splashFragment);
                transaction.commit();
            }

        }
    }
}