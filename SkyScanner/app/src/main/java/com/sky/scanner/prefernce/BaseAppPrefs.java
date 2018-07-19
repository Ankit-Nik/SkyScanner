package com.sky.scanner.prefernce;

import android.content.Context;

/**
 * This Abstract class is used for token and assigments abstract methods
 */

public abstract class BaseAppPrefs  {
    public abstract String getToken(Context context);
    public abstract void saveToken(Context context, String token);

}
