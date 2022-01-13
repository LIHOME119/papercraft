package com.example.papercraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.SplashScreen;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Splash_wellcome extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    ImageView logo;
    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash_wellcome);
        logo=findViewById(R.id.wellcome_logo);
        textView1=findViewById(R.id.wellcome_textView);
        textView2=findViewById(R.id.wellcome_textView2);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.setAnimation(animation);
        textView1.setAnimation(animation);
        textView2.setAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
        GifImageView imageView1=findViewById(R.id.wellcome_background);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.burning);
            imageView1.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}