package com.example.retrofit;

import retrofit2.Retrofit;

/**
 * Created by doudou on 2017/9/20.
 */

public class RetrofitUtils {

    private static RetrofitUtils mRetrofitUtils;
    private Retrofit mRetrofit;

    public RetrofitUtils(Retrofit retrofit)
    {
        if (retrofit == null)
        {
            mRetrofit = new Retrofit.Builder().build();
        } else
        {
            mRetrofit = retrofit;
        }
    }


    public static RetrofitUtils initClient(Retrofit retrofit)
    {
        if (mRetrofitUtils == null)
        {
            synchronized (RetrofitUtils.class)
            {
                if (mRetrofitUtils == null)
                {
                    mRetrofitUtils = new RetrofitUtils(retrofit);
                }
            }
        }
        return mRetrofitUtils;
    }

    public static RetrofitUtils getInstance()
    {
        return initClient(null);
    }

    public <T> T createReq(Class<T> reqServer){
        return mRetrofit.create(reqServer);
    }
}
