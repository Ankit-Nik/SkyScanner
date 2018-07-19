package com.sky.scanner.interfaces;

import android.content.DialogInterface;

public interface AlertMagnaticDialog {
    void onButtonClicked(DialogInterface dialog, boolean value, String status);
}