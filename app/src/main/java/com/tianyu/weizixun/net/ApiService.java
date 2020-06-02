package com.tianyu.weizixun.net;

import com.tianyu.weizixun.bean.LoginBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    String baseLoginUrl = "https://www.wanandroid.com/";

    /**
     * 背压请求login
     *
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Flowable<LoginBean> login(@Field("username") String username, @Field("password") String password);
}
