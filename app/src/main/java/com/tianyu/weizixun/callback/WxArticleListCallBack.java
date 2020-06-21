package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.WxArticleBean;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public interface WxArticleListCallBack extends BaseCallback<WxArticleBean> {

    @Override
    void onSuccess(WxArticleBean wxArticleBean);

    @Override
    void onFail(String error);
}