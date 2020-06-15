package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.HotNewsBean;
import com.tianyu.weizixun.callback.HotCallback;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

public class HotModel extends BaseModel {
    public void getData(HotCallback callback) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseZhihuUrl, ApiService.class)
                .getHotNewsData()
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<HotNewsBean>() {
                    @Override
                    protected void onSuccess(HotNewsBean hotNewsBean) {
                        if (hotNewsBean != null) {
                            callback.onSuccess(hotNewsBean);
                        } else {
                            callback.onFail("获取数据失败");
                        }
                    }
                });
    }
}
