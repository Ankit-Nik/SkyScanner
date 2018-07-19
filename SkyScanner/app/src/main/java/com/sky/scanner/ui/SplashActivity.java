package com.sky.scanner.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.sky.scanner.R;
import com.sky.scanner.prefernce.AppPrefs;
import com.sky.scanner.ui.dashboard.DashboardActivity;
import com.sky.scanner.ui.login.LoginActivity;
import com.sky.scanner.utils.AppConstants;
import com.sky.scanner.utils.LogManager;
import com.sky.scanner.utils.ToastUtils;

/**
 * Created by A0000350 on 5/24/2018.
 */

public class SplashActivity extends Activity {
    int progressStatus = 0;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        handler = new Handler();
        normalFlow();
    }


    private void normalFlow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 10) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    try {
                        Thread.sleep(AppConstants.SPLASH_TIME_OUT);
                    } catch (InterruptedException e) {
                        LogManager.printStackTrace(e);
                        LogManager.printStackTrace(e);
                    }
                }

                try {
                    if (progressStatus == 10) {
                        if (!AppPrefs.getInstance(SplashActivity.this).getLoginStatus(SplashActivity.this)) {
                            switchToLoginScreen();
                            overridePendingTransition(R.anim.slide_right_to_left, R.anim.fadeout_animation);
                            finish();

                        }else {
                            switchToDashboardScreen();
                            overridePendingTransition(R.anim.slide_right_to_left, R.anim.fadeout_animation);
                            finish();
                        }
                    }

                } catch (final Exception ex) {
                    LogManager.printStackTrace(ex);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.longToast(SplashActivity.this, ex.getMessage());
                            finish();
                        }
                    });
                }
            }


        }).start();
    }

    private void switchToLoginScreen() {
        LoginActivity.switchToLoginScreen(SplashActivity.this, "");
        overridePendingTransition(R.anim.slide_right_to_left, R.anim.fadeout_animation);
        finish();
       // startActivity(new Intent(LoginActivity.this, FormDetails.class));
    }

    private void switchToDashboardScreen() {
        DashboardActivity.switchToDashboardScreen(SplashActivity.this, "");
        overridePendingTransition(R.anim.slide_right_to_left, R.anim.fadeout_animation);
        finish();
    }
}
