package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.ItInfoBean;
import com.tianyu.weizixun.callback.ItInfoCallback;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

/**
 * @date：2020/6/21
 * @describe：IT咨询
 * @author：TianYu
 */
public class ItInfoModel extends BaseModel {
    public void getItInfoData(ItInfoCallback callback) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseWanAndroidUrl, ApiService.class)
                .getItInfoData()
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<ItInfoBean>() {
                    @Override
                    public void onSuccess(ItInfoBean itInfoBean) {
                        callback.onSuccess(itInfoBean);
                    }
                });
    }
}
