package com.example.skeleton.utils;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Tools {
    private static Toast toast;

    public static void log(String msg) {
        Log.d("**com.skeleton**", msg);
    }

    public static void showMsg(Context ctx, String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public static boolean isActionDown(KeyEvent event) {
        return event.getAction() == KeyEvent.ACTION_DOWN;
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
