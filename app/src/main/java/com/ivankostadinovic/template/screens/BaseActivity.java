package com.ivankostadinovic.template.screens;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ivankostadinovic.template.utils.Tools;

public abstract class BaseActivity extends AppCompatActivity {

    public Toast toast;

    protected void observeDefaultEvents(BaseViewModel viewModel) {
        viewModel.messageEvent.observe(this, this::showMsg);
    }

    public void showMsg(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
