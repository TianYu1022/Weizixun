package com.tianyu.weizixun.view;

import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.NaviBean;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public interface NaviView extends BaseView<NaviBean> {
    @Override
    void onSuccess(NaviBean naviBean);

    @Override
    void onFail(String error);
}
