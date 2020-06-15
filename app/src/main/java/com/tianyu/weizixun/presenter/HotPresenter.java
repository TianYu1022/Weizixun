package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.HotNewsBean;
import com.tianyu.weizixun.callback.HotCallback;
import com.tianyu.weizixun.model.HotModel;

public class HotPresenter extends BasePresenter implements HotCallback {
    private HotModel mModel;

    public void getData() {
        mModel.getData(this);
    }

    @Override
    protected void initModel() {
        mModel = new HotModel();
    }

    @Override
    public void onSuccess(HotNewsBean hotNewsBean) {
        mView.onSuccess(hotNewsBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
