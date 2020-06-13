package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.DailyNewsBean;

public interface DailyNewCallback extends BaseCallback<DailyNewsBean> {
    @Override
    void onSuccess(DailyNewsBean dailyNewsBean);

    @Override
    void onFail(String error);
}
