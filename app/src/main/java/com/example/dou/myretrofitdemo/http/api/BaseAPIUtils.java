package com.example.dou.myretrofitdemo.http.api;

import android.content.Context;

import com.example.dou.myretrofitdemo.http.rxjava.RetryWithDelay;
import com.example.retrofit.rxjava.RxSchedulersHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by doudou on 2017/9/25.
 */

public class BaseAPIUtils {

    public static Observable<String> post(Observable<ResponseBody> observable, RxAppCompatActivity activity){
        return observable.map(new Function<ResponseBody, String>() {
            @Override
            public String apply(@NonNull ResponseBody responseBody) throws Exception {
                String string = responseBody.string();
                return string;
            }
        })
                .compose(RxSchedulersHelper.<String>io_main())
                .compose(activity.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .retryWhen(new RetryWithDelay(3, 5*1000, (Context) activity));
    };
}
