package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.WxArticleBean;
import com.tianyu.weizixun.callback.WxArticleListCallBack;
import com.tianyu.weizixun.model.WxArticleListModel;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public class WxArticleLisPresenter extends BasePresenter implements WxArticleListCallBack {

    private WxArticleListModel model;

    @Override
    protected void initModel() {
        model = new WxArticleListModel();
    }

    public void getWxArticleLisData(int id, int page) {
        model.getWxArticleData(id, page, this);
        addModel(model);
    }

    @Override
    public void onSuccess(WxArticleBean wxArticleBean) {
        mView.onSuccess(wxArticleBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}