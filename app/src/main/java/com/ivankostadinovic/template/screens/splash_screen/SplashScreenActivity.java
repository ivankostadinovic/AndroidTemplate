package com.ivankostadinovic.template.screens.splash_screen;

import android.os.Bundle;

import com.ivankostadinovic.template.R;
import com.ivankostadinovic.template.screens.BaseActivity;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);
    }
}
