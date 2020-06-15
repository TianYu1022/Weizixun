package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.SpecilBean;
import com.tianyu.weizixun.callback.SpecilCallback;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

public class SpecilModel extends BaseModel {
    public void getData(SpecilCallback callback) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseZhihuUrl, ApiService.class)
                .getSpecilData()
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<SpecilBean>() {
                    @Override
                    protected void onSuccess(SpecilBean specilBean) {
                        if (specilBean != null) {
                            callback.onSuccess(specilBean);
                        } else {
                            callback.onFail("请求失败");
                        }
                    }
                });
    }
}
