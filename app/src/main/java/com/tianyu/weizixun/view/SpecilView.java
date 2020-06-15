package com.tianyu.weizixun.view;

import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.SpecilBean;

public interface SpecilView extends BaseView<SpecilBean> {
    @Override
    void onSuccess(SpecilBean specilBean);

    @Override
    void onFail(String error);
}
