package com.tianyu.weizixun.presenter;

import android.text.TextUtils;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.RegisterBean;
import com.tianyu.weizixun.callback.RegisterCallback;
import com.tianyu.weizixun.model.RegisterModel;

public class RegisterPresenter extends BasePresenter implements RegisterCallback {

    private RegisterModel mModel;

    public void register(String userid, String psd, String accessToken, String typeid) {
        if (TextUtils.isEmpty(userid)) {
            mView.onFail("姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(psd)) {
            mView.onFail("密码不能为空");
            return;
        }
        mModel.register(userid, psd, accessToken, typeid, this);
    }

    @Override
    protected void initModel() {
        mModel = new RegisterModel();
        addModel(mModel);
    }

    @Override
    public void onSuccess(RegisterBean registerBean) {
        mView.onSuccess(registerBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
