package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.DailyNewsBean;
import com.tianyu.weizixun.callback.DailyNewCallback;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

public class DailyNewModel extends BaseModel {
    public void getData(DailyNewCallback callback) {
        new HttpManager().getApiService(ApiService.baseZhihuUrl, ApiService.class)
                .getDailyNewsData()
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<DailyNewsBean>() {
                    @Override
                    protected void onSuccess(DailyNewsBean dailyNewsBean) {
                        callback.onSuccess(dailyNewsBean);
                    }
                });
    }
}
