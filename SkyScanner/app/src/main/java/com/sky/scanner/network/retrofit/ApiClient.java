package com.sky.scanner.network.retrofit;

import com.sky.scanner.network.WebApiInterface;
import com.sky.scanner.utils.AppUrls;
import com.sky.scanner.utils.LogManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    public static final int HTTP_CONNECT_TIMEOUT = 60000 * 2; // milliseconds
    public static final int HTTP_READ_TIMEOUT = 60000 * 2; // milliseconds

    private static Retrofit retrofit = null;
    private static WebApiInterface serviceApi;

    /**
     * MEthod to get retrofit client.
     *
     * @return Retrofit.
     */
    public static Retrofit getClient() {
        try {
            // setup for Logging
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // setup here http client
            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new HeaderInterceptor())
                    .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(interceptor).build();


            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(AppUrls.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(client)
                        .build();
                serviceApi = retrofit.create(WebApiInterface.class);
            }

        }catch (Exception e){
            LogManager.printStackTrace(e);
        }
        return retrofit;
    }

    public static void resetRetrofit() {
        if (retrofit != null) {
            retrofit = null;
        }
    }


}
