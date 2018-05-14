package com.example.dou.myretrofitdemo.http.rxjava;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.dou.myretrofitdemo.R;
import com.example.dou.myretrofitdemo.view.LoadingDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by doudou on 2017/9/19.
 */

public abstract class BaseObserver<T> implements Observer<String> {

    Type t;
    private LoadingDialog builder;
    private Context mContext;

    public BaseObserver(Context context, Type t){
        mContext = context;
        this.t = t;
    }

    public BaseObserver(Context context){
        mContext = context;
        this.t = getType();
    }

    private Type getType(){
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            return Object.class;
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
//        Log.e("dd", "onSubscribe");
        if (builder == null) {
            builder = new LoadingDialog(mContext, R.mipmap.loading, "正在加载中...");
        }

        // builder.setOutsideTouchable(false);//点击空白区域是否关闭
        // builder.setBackTouchable(true);//按返回键是否关闭
        builder.show();
    }

    @Override
    public void onNext(@NonNull String string) {
//        Log.e("dd", "onNext");
        if (builder!=null){
            builder.dismiss();
        }
        try{
            Gson gson = new Gson();
            T bean = gson.fromJson(string, t);
            if (bean!=null) {
                onSuccess(bean);
            }else{
                onSuccess(string);
            }
        }catch (JsonSyntaxException e){
            onSuccess(string);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Logger.e("onError"+e.getMessage());
        if (builder!=null){
            builder.dismiss();
        }
    }

    @Override
    public void onComplete() {
//        Log.e("dd", "onComplete");
        if (builder!=null){
            builder.dismiss();
        }
    }

    public abstract void onSuccess(T t);   //  json数据解析成功
    public void onSuccess(String string){   // json数据解析失败

    };
}
