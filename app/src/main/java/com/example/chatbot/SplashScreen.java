package com.example.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbot.utills.Constants;
import com.example.chatbot.utills.PreferenceManager;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        SystemClock.sleep(2000);
//        startActivity(new Intent(SplashScreen.this,MainActivity.class));
//        finish();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               PreferenceManager preferenceManager = new PreferenceManager(Constants.USER,SplashScreen.this);
               if (preferenceManager.isUserLoggedIn()){
                   startActivity(new Intent(SplashScreen.this,MainActivity.class));
                   finish();
               }else {
                   startActivity(new Intent(SplashScreen.this,SignIn.class));
                   finish();
               }

            }
        },3000);
    }
}
