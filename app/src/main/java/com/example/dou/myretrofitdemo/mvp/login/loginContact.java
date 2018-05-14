package com.example.dou.myretrofitdemo.mvp.login;

import android.content.Context;

import com.example.dou.myretrofitdemo.Bean.UserInfoEntity;
import com.example.dou.myretrofitdemo.mvp.BasePresenter;
import com.example.dou.myretrofitdemo.mvp.BaseView;


/**
 * Created by doudou on 2017/11/30.
 */

public interface loginContact {
    interface View extends BaseView<Presenter> {

        void onSuccessLogin(UserInfoEntity userInfo);

        Context getContext();

    }
    interface Presenter extends BasePresenter {
        void login(String username, String password);
    }
}
