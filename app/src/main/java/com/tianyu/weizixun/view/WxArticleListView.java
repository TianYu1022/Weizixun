package com.tianyu.weizixun.view;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.WxArticleBean;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public interface WxArticleListView extends BaseView<WxArticleBean> {

    @Override
    void onSuccess(WxArticleBean wxArticleBean);

    @Override
    void onFail(String error);
}