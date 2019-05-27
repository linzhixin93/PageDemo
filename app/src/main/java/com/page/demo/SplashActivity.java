package com.page.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.page.demo.pages.MainPage;
import com.wish.ui.page.PageManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PageManager.openPage(this, MainPage.class);
        finish();
    }
}
