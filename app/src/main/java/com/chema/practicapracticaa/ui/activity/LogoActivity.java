package com.chema.practicapracticaa.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.chema.practicapracticaa.R;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        ocultarBarraDeEstadoEnSplashScreen();

        View imageViewLogo = findViewById(R.id.imageViewLogo);

        iniciarAnimacionLatido(imageViewLogo);

        imageViewLogo.setOnClickListener(view -> {
            view.clearAnimation();

            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 720f);
            animator.setDuration(1500);
            animator.setInterpolator(new DecelerateInterpolator());

            animator.addListener(new android.animation.Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(android.animation.Animator animation) { }

                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    Intent intent = new Intent(LogoActivity.this, BienvenidoActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onAnimationCancel(android.animation.Animator animation) { }

                @Override
                public void onAnimationRepeat(android.animation.Animator animation) { }
            });

            animator.start();
        });
    }

    private void iniciarAnimacionLatido(View view) {
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f);

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1f);


        scaleUpX.setDuration(300);
        scaleUpY.setDuration(300);
        scaleDownX.setDuration(300);
        scaleDownY.setDuration(300);

        scaleUpX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleUpY.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleDownX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleDownY.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleUpX.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) { }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {

                scaleDownX.start();
                scaleDownY.start();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) { }

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) {

            }
        });

        scaleDownX.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) { }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {

                scaleUpX.start();
                scaleUpY.start();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) { }

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) { }
        });

        scaleUpX.start();
        scaleUpY.start();
    }

    private void ocultarBarraDeEstadoEnSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
}
