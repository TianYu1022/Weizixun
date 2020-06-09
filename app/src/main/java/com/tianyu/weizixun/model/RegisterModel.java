package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.RegisterBean;
import com.tianyu.weizixun.callback.RegisterCallback;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;


public class RegisterModel extends BaseModel {
    public void register(String userid, String psd, String pwd, RegisterCallback callback) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseUrl, ApiService.class)
                .register(userid, psd, pwd)
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<RegisterBean>() {
                    @Override
                    protected void onSuccess(RegisterBean registerBean) {
                        callback.onSuccess(registerBean);
                    }
                });
    }
}
