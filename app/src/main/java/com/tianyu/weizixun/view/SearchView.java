package com.tianyu.weizixun.view;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.base.BaseView;
import com.tianyu.weizixun.bean.SearchBean;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public interface SearchView extends BaseView<SearchBean> {
    @Override
    void onSuccess(SearchBean searchBean);

    @Override
    void onFail(String error);
}