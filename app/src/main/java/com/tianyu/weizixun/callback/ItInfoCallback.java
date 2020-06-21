package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.ItInfoBean;

/**
 * @date：2020/6/21
 * @describe：IT咨询
 * @author：TianYu
 */
public interface ItInfoCallback extends BaseCallback<ItInfoBean> {
    @Override
    void onSuccess(ItInfoBean itInfoBean);

    @Override
    void onFail(String error);
}
