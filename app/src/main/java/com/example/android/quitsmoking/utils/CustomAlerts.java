package com.example.android.quitsmoking.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Class contains Toast method.
 * Created by sam on 2017-11-20.
 */

public class CustomAlerts {
    private Context applicationContext;

    public CustomAlerts(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void showMessage(String message) {
        Toast.makeText(applicationContext,
                message, Toast.LENGTH_LONG).show();
    }
}