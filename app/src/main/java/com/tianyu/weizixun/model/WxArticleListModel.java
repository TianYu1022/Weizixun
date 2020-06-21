package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.WxArticleBean;
import com.tianyu.weizixun.callback.WxArticleListCallBack;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */

public class WxArticleListModel extends BaseModel {
    public void getWxArticleData(int id, int page, WxArticleListCallBack callBack) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseWanAndroidUrl, ApiService.class)
                .getWxArticleData(id, page)
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<WxArticleBean>() {
                    @Override
                    public void onSuccess(WxArticleBean wxArticleBean) {
                        callBack.onSuccess(wxArticleBean);
                    }
                });
    }
}