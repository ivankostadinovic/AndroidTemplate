package com.example.skeleton.screens;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skeleton.utils.Tools;

public abstract class BaseActivity extends AppCompatActivity {

    protected void observeDefaultEvents(ArenaViewModel viewModel) {
        viewModel.messageEvent.observe(this, msg -> Tools.showMsg(this, msg));
    }
}
