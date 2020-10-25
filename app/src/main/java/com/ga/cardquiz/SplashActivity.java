package com.ga.cardquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {
    // Uses EasySplash which is added to the Gradle build
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EasySplashScreen welcome =new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(RegisterOrLogin.class)
                .withSplashTimeOut(1000)  //display 5 seconds
                .withBackgroundColor(Color.parseColor("#F0B3A6"))
                .withHeaderText("CardQuiz Emporium!")
                .withAfterLogoText("Try your luck")
                .withBeforeLogoText("Get Rich Quick!")
                .withLogo(R.mipmap.ic_launcher_round);
        welcome.getHeaderTextView().setTextColor(Color.BLACK);
        View welcomeSplashScreen = welcome.create();
        setContentView(welcomeSplashScreen);  //splash screen does not have a layout


    }
}