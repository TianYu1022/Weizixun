package com.tianyu.weizixun.net;

import com.tianyu.weizixun.bean.LoginBean;
import com.tianyu.weizixun.bean.RegisterBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
//    String baseUrl = "http://47.110.151.50/p6/";
    String baseUrl = "https://www.wanandroid.com/user/";

    /**
     * 注册,
     *
     * @param username 用户名
     * @param password 密码
     * @param repassword 确认密码
     * @return
     */
    @POST("register")
    @FormUrlEncoded
    Flowable<RegisterBean> register(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("repassword") String repassword);

    /**
     * 账号密码登录
     *
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @POST("login")
    @FormUrlEncoded
    Flowable<LoginBean> login(@Field("username") String username,
                              @Field("password") String password);
}
