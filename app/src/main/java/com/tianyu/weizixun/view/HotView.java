package com.tianyu.weizixun.view;

import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.HotNewsBean;

public interface HotView extends BaseView<HotNewsBean> {
    @Override
    void onSuccess(HotNewsBean hotNewsBean);

    @Override
    void onFail(String error);
}
