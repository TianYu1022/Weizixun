package com.tianyu.weizixun.presenter;

import com.tianyu.weizixun.base.BasePresenter;
import com.tianyu.weizixun.bean.SearchBean;
import com.tianyu.weizixun.callback.SearchCallback;
import com.tianyu.weizixun.model.SearchModel;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */

public class SearchPresenter extends BasePresenter implements SearchCallback {

    private SearchModel model;

    @Override
    protected void initModel() {
        model = new SearchModel();
        addModel(model);
    }

    public void getSearchData(int page, String word) {
        model.getSearchData(page, word, this);
    }

    @Override
    public void onSuccess(SearchBean searchBean) {
        mView.onSuccess(searchBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFail(error);
    }
}