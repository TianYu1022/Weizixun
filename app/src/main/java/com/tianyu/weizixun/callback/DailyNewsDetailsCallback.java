package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.DailyNewsDetailsBean;

/**
 * @date：2020/6/16
 * @describe：知乎详情
 * @author：TianYu
 */
public interface DailyNewsDetailsCallback extends BaseCallback<DailyNewsDetailsBean> {
    @Override
    void onSuccess(DailyNewsDetailsBean dailyNewsDetailsBean);

    @Override
    void onFail(String error);
}
