package com.example.silvio.moviesaoo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.silvio.moviesaoo.R;
import com.example.silvio.moviesaoo.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        ObjectAnimator animation = ObjectAnimator.ofFloat(binding.tvAppName, "translationX", 100f);
        animation.setDuration(1000);

        ObjectAnimator returnanimation = ObjectAnimator.ofFloat(binding.tvAppName, "translationX", -50f);
        returnanimation.setDuration(1000);

        ObjectAnimator center = ObjectAnimator.ofFloat(binding.tvAppName, "translationX", 0f);

        center.setDuration(1000);
        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(animation).before(returnanimation);
        bouncer.play(returnanimation).before(center);
        bouncer.start();

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, 5000);
    }

}
