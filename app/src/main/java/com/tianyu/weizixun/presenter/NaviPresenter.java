package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.NaviBean;
import com.tianyu.weizixun.callback.NaviCallBack;
import com.tianyu.weizixun.model.NaviModel;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */

public class NaviPresenter extends BasePresenter implements NaviCallBack {

    private NaviModel model;

    @Override
    protected void initModel() {
        model = new NaviModel();
    }

    public void getNaviDta() {
        model.getNaviData(this);
        addModel(model);
    }

    @Override
    public void onSuccess(NaviBean naviBean) {
        mView.onSuccess(naviBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}