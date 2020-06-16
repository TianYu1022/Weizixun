package com.tianyu.weizixun.view;

import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.DailyNewsDetailsBean;

/**
 * @date：2020/6/16
 * @describe：知乎详情
 * @author：TianYu
 */
public interface DailyNewsDetailsView extends BaseView<DailyNewsDetailsBean> {
    @Override
    void onSuccess(DailyNewsDetailsBean dailyNewsDetailsBean);

    @Override
    void onFail(String error);
}
