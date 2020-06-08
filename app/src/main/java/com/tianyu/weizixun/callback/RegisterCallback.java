package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.LoginBean;
import com.tianyu.weizixun.bean.RegisterBean;

public interface RegisterCallback extends BaseCallback<RegisterBean> {
    @Override
    void onSuccess(RegisterBean registerBean);

    @Override
    void onFail(String error);
}
