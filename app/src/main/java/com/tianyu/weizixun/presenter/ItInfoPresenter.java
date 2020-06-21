package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.ItInfoBean;
import com.tianyu.weizixun.callback.ItInfoCallback;
import com.tianyu.weizixun.model.ItInfoModel;

/**
 * @date：2020/6/21
 * @describe：IT咨询
 * @author：TianYu
 */
public class ItInfoPresenter extends BasePresenter implements ItInfoCallback {
    private ItInfoModel mModel;

    @Override
    protected void initModel() {
        mModel = new ItInfoModel();
        addModel(mModel);
    }

    public void getItInfoData() {
        mModel.getItInfoData(this);
    }

    @Override
    public void onSuccess(ItInfoBean itInfoBean) {
        mView.onSuccess(itInfoBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}
