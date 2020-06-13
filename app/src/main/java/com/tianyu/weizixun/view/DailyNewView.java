package com.tianyu.weizixun.view;


import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.DailyNewsBean;

public interface DailyNewView extends BaseView<DailyNewsBean> {
    @Override
    void onSuccess(DailyNewsBean dailyNewsBean);

    @Override
    void onFail(String error);
}
