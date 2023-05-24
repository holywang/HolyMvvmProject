package com.mvvm.holyandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mvvm.holyandroid.R;
import com.mvvm.holyandroid.animation.RotationAnimation;


/**
 * @author rg wang
 * created on  2023/5/15
 */
public class SplashFragment extends Fragment {

    private View view;
    private ImageView splash_fragment_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_splash,container,false);

        splash_fragment_image = view.findViewById(R.id.splash_fragment_image);
        RotationAnimation animation = new RotationAnimation(view.getWidth() / 2, view.getHeight() / 2, 0, 360, 1, 0, 1, 0);
        animation.setDuration(3000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        splash_fragment_image.startAnimation(animation);

        return view;
    }
}
