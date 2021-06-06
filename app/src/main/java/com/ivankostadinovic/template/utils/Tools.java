package com.ivankostadinovic.template.utils;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import timber.log.Timber;

public class Tools {

    public static void log(String msg) {
        Timber.d(msg);
    }


    public static void showKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static boolean hideKeyboard(AppCompatActivity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null && imm != null) {
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
        return true;
    }

}
