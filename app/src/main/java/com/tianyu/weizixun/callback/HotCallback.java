package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.HotNewsBean;

public interface HotCallback extends BaseCallback<HotNewsBean> {
    @Override
    void onSuccess(HotNewsBean hotNewsBean);

    @Override
    void onFail(String error);
}
