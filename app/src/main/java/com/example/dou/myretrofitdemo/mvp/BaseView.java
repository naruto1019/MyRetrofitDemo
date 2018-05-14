package com.example.dou.myretrofitdemo.mvp;

/**
 * Created by doudou on 2017/11/30.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
