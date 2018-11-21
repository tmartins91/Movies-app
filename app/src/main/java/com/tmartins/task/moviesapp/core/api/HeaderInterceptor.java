package com.tmartins.task.moviesapp.core.api;

import com.tmartins.task.moviesapp.core.Config;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private static final String ACCEPT_HEADER = "Accept";
    private static final String JSON_TYPE = "application/json";

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().addHeader(ACCEPT_HEADER, JSON_TYPE).build();

        HttpUrl originalHttpUrl = request.url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", Config.API_KEY)
                .build();

        Request.Builder requestBuilder = request.newBuilder().url(url);
        Request finalRequest = requestBuilder.build();

        return chain.proceed(finalRequest);
    }

}

