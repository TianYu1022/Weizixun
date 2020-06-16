package com.tianyu.weizixun.net;

import com.tianyu.weizixun.bean.DailyNewsBean;
import com.tianyu.weizixun.bean.DailyNewsDetailsBean;
import com.tianyu.weizixun.bean.HotNewsBean;
import com.tianyu.weizixun.bean.LoginBean;
import com.tianyu.weizixun.bean.RegisterBean;
import com.tianyu.weizixun.bean.SpecilBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String baseUrl = "https://www.wanandroid.com/user/";

    /**
     * 注册,
     *
     * @param username   用户名
     * @param password   密码
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
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("login")
    @FormUrlEncoded
    Flowable<LoginBean> login(@Field("username") String username,
                              @Field("password") String password);


    String baseZhihuUrl = "https://news-at.zhihu.com/api/4/";

    /**
     * 知乎日报
     *
     * @return
     */
    @GET("news/latest")
    Flowable<DailyNewsBean> getDailyNewsData();

    /**
     * 知乎日报详情内容
     *
     * @param id ID从HotNewsBean中取
     * @return
     */
    @GET("news/{id}")
    Flowable<DailyNewsDetailsBean> getDailyNewsDetails(@Path("id") int id);

    /**
     * 获取往期日报
     *
     * @return
     */
    @GET("news/before/{date}")
    Flowable<DailyNewsBean> getBeforeData(@Path("date") String date);

    /**
     * 知乎专栏
     *
     * @return
     */
    @GET("sections")
    Flowable<SpecilBean> getSpecilData();

    /**
     * 知乎热门
     *
     * @return
     */
    @GET("news/hot")
    Flowable<HotNewsBean> getHotNewsData();
}
