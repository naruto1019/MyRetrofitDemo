package com.example.retrofit;

import okhttp3.OkHttpClient;

/**
 * Created by doudou on 2017/9/20.
 */

public class OkhttpUtils {

    private static OkhttpUtils mOkhttpUtils;
    private OkHttpClient mOkHttpClient;

    public OkhttpUtils(OkHttpClient okHttpClient)
    {
        if (okHttpClient == null)
        {
            mOkHttpClient = new OkHttpClient.Builder().build();
        } else
        {
            mOkHttpClient = okHttpClient;
        }
    }


    public static OkhttpUtils initClient(OkHttpClient okHttpClient)
    {
        if (mOkhttpUtils == null)
        {
            synchronized (OkhttpUtils.class)
            {
                if (mOkhttpUtils == null)
                {
                    mOkhttpUtils = new OkhttpUtils(okHttpClient);
                }
            }
        }
        return mOkhttpUtils;
    }

    public static OkhttpUtils getInstance()
    {
        return initClient(null);
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }
}
