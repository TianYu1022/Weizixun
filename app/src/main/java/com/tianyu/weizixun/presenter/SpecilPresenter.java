package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.SpecilBean;
import com.tianyu.weizixun.callback.SpecilCallback;
import com.tianyu.weizixun.model.SpecilModel;

public class SpecilPresenter extends BasePresenter implements SpecilCallback {

    private SpecilModel mModel;

    public void getData() {
        mModel.getData(this);
        addModel(mModel);
    }

    @Override
    protected void initModel() {
        mModel = new SpecilModel();
    }

    @Override
    public void onSuccess(SpecilBean specilBean) {
        mView.onSuccess(specilBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
