package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.SearchBean;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.presenter.SearchPresenter;
import com.tianyu.weizixun.utils.RxUtil;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */

public class SearchModel extends BaseModel {
    public void getSearchData(int page, String word, SearchPresenter callback) {
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseWanAndroidUrl, ApiService.class)
                .getSearchData(page, word)
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<SearchBean>() {
                    @Override
                    public void onSuccess(SearchBean searchBean) {
                        callback.onSuccess(searchBean);
                    }
                });
    }
}