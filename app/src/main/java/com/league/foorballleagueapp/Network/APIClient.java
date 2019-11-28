package com.league.foorballleagueapp.Network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.league.foorballleagueapp.AppConsts.AppConst;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;


    public static Retrofit getRetrofitInstance(){
       if(okHttpClient==null) {
            initOkHttp();
        }
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConst.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp() {
      int REQUEST =60;
    OkHttpClient.Builder okBuilder = new OkHttpClient().newBuilder()
            .connectTimeout(REQUEST, TimeUnit.SECONDS)
            .readTimeout(REQUEST,TimeUnit.SECONDS)
            .writeTimeout(REQUEST,TimeUnit.SECONDS);

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okBuilder.addInterceptor(interceptor);

        okBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept","application/json")
                        .addHeader("Content_Type","application/json")
                        .addHeader("X-Auth-Token",AppConst.API_KEY);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
    okHttpClient = okBuilder.build();
}



}
