package com.example.retrofit;

import android.content.Context;

import com.example.retrofit.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by doudou on 2017/9/25.
 */

public class CacheInterceptor implements Interceptor {

    private Context context;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtils.isConnected()) {
            Response response = chain.proceed(request);
            // read from cache for 0 s  有网络不会使用缓存数据
            int maxAge = 60;
            String cacheControl = request.cacheControl().toString();
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            //无网络时强制使用缓存数据
            int maxStale = 60 * 60;
            request = request.newBuilder()
                    .cacheControl(CacheControl.parse(Headers.of("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)))
                    .build();
            Response response = chain.proceed(request);
            //set cahe times ; value is useless at all !
            //            int maxStale = 60;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
