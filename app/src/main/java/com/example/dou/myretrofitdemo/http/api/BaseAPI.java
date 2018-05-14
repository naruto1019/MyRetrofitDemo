package com.example.dou.myretrofitdemo.http.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by doudou on 2017/9/18.
 */

public interface BaseAPI {

    // 文件上传
    @POST("/index.php?")
    Observable<ResponseBody> upload(@Body RequestBody body, @QueryMap Map<String, String> map);

    @GET("/index.php")
    Observable<ResponseBody> get(@QueryMap Map<String, String> map);

    @POST("/index.php")
    @FormUrlEncoded
    Observable<ResponseBody> post(@FieldMap Map<String, String> map);

    @Streaming
    @GET
    Observable<ResponseBody> downLoad(@Url String fileUrl);

}
