package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.SpecilBean;

public interface SpecilCallback extends BaseCallback<SpecilBean> {
    @Override
    void onSuccess(SpecilBean specilBean);

    @Override
    void onFail(String error);
}
