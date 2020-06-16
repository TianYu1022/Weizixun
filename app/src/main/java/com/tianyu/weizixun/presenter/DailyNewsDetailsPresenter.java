package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.DailyNewsDetailsBean;
import com.tianyu.weizixun.callback.DailyNewsDetailsCallback;
import com.tianyu.weizixun.model.DailyNewsDetailsModel;

/**
 * @date：2020/6/16
 * @describe：知乎详情
 * @author：TianYu
 */
public class DailyNewsDetailsPresenter extends BasePresenter implements DailyNewsDetailsCallback {

    private DailyNewsDetailsModel mModel;

    public void getData(int id){
        mModel.getData(id,this);
    }

    @Override
    protected void initModel() {
        mModel = new DailyNewsDetailsModel();
    }

    @Override
    public void onSuccess(DailyNewsDetailsBean dailyNewsDetailsBean) {
        mView.onSuccess(dailyNewsDetailsBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
