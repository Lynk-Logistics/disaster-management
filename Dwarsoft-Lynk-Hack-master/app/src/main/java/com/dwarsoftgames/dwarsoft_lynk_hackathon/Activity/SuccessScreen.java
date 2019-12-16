package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;

public class SuccessScreen extends AppCompatActivity {

    private LottieAnimationView lottieAnimationCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_screen);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.white));

        init();
        playAnimation();
    }

    private void init() {
        lottieAnimationCorrect = findViewById(R.id.lottieAnimationCorrect);
    }

    private void playAnimation() {
        lottieAnimationCorrect.bringToFront();
        lottieAnimationCorrect.setAnimation("correct.json");
        lottieAnimationCorrect.playAnimation();
    }
}