package com.tianyu.weizixun.callback;

import com.tianyu.weizixun.base.BaseCallback;
import com.tianyu.weizixun.bean.SearchBean;

/**
 * @date：2020/6/21
 * @describe：
 * @author：TianYu
 */
public interface SearchCallback extends BaseCallback<SearchBean> {
    @Override
    void onSuccess(SearchBean searchBean);

    @Override
    void onFail(String error);
}