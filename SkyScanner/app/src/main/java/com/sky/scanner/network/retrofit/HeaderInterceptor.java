package com.sky.scanner.network.retrofit;

import com.sky.scanner.application.SkyScannerApplication;
import com.sky.scanner.prefernce.AppPrefs;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor implements Interceptor {

    public HeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //Use Realm data base to store the session or token
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("Authorization","bearer "+ AppPrefs.getInstance(SkyScannerApplication.getAppContext()).getAccessToken(SkyScannerApplication.getAppContext()));
        Request requestHeader = requestBuilder.build();
        return chain.proceed(requestHeader);
    }
}
