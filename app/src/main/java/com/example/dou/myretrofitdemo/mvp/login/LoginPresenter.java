package com.example.dou.myretrofitdemo.mvp.login;

import com.example.dou.myretrofitdemo.Bean.UserInfoEntity;
import com.example.dou.myretrofitdemo.http.api.BaseAPI;
import com.example.dou.myretrofitdemo.http.api.BaseAPIUtils;
import com.example.dou.myretrofitdemo.http.rxjava.BaseObserver;
import com.example.dou.myretrofitdemo.ui.base.BaseActivity;
import com.example.retrofit.RetrofitUtils;

import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by doudou on 2017/11/30.
 */

public class LoginPresenter implements loginContact.Presenter {

    loginContact.View mView;

    public LoginPresenter(loginContact.View view){
        mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void login(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        Logger.d(params);
        Type type = new TypeToken<UserInfoEntity>(){}.getType();

        BaseAPIUtils.post(RetrofitUtils.getInstance().createReq(BaseAPI.class).get(params), (BaseActivity)mView.getContext())
                //				.compose(RxSchedulersHelper.<String>io_main())
                //				.compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                //				.retryWhen(new RetryWithDelay(3, 5*1000, context))    //  添加失败重试策略（每5s之后重试1次 共3次）
                .subscribe(new BaseObserver<UserInfoEntity>(mView.getContext(), type) {
                    @Override
                    public void onSuccess(UserInfoEntity t) {
                       mView.onSuccessLogin(t);
                    }

                    @Override
                    public void onSuccess(String response) {

                    }
                });
    }
}
