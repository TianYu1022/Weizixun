package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.DailyNewsBean;
import com.tianyu.weizixun.callback.DailyNewCallback;
import com.tianyu.weizixun.model.DailyNewModel;

public class DailyNewPresenter extends BasePresenter implements DailyNewCallback {

    private DailyNewModel mModel;

    public void getData() {
        mModel.getData(this);
    }

    @Override
    protected void initModel() {
        mModel = new DailyNewModel();
        addModel(mModel);
    }

    @Override
    public void onSuccess(DailyNewsBean dailyNewsBean) {
        mView.onSuccess(dailyNewsBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
