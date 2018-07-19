package com.sky.scanner.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by A0000350 on 5/24/2018.
 */

public class SkyScannerApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }


    public static Context getAppContext() {
        return SkyScannerApplication.context;
    }
}
