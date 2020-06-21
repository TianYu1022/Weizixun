package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.NaviBean;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public interface NaviCallBack extends BaseCallback<NaviBean> {
    @Override
    void onSuccess(NaviBean naviBean);

    @Override
    void onFail(String error);
}
