package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.NaviBean;
import com.tianyu.weizixun.callback.NaviCallBack;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public class NaviModel extends BaseModel {
    public void getNaviData(NaviCallBack callBack) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseWanAndroidUrl, ApiService.class)
                .getNaviData()
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<NaviBean>() {
                    @Override
                    public void onSuccess(NaviBean naviBean) {
                        callBack.onSuccess(naviBean);
                    }
                });
    }
}