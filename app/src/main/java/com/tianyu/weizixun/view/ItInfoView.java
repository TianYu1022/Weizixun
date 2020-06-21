package com.tianyu.weizixun.view;

import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.ItInfoBean;

/**
 * @date：2020/6/21
 * @describe：IT咨询
 * @author：TianYu
 */
public interface ItInfoView extends BaseView<ItInfoBean> {
    @Override
    void onSuccess(ItInfoBean itInfoBean);

    @Override
    void onFail(String error);
}
