package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.LoginBean;
import com.tianyu.weizixun.callback.LoginCallback;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

public class LoginModel extends BaseModel {
    public void login(String name, String password, LoginCallback callback) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseLoginUrl, ApiService.class)
                .login(name, password)
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        int errorCode = loginBean.getErrorCode();
                        if (errorCode == 0) {
                            callback.onSuccess(loginBean);
                        } else {
                            callback.onFail(loginBean.getErrorMsg());
                        }
                    }
                });
    }
}
