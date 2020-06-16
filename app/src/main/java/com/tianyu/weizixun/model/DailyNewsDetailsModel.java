package com.tianyu.weizixun.model;

import com.tianyu.weizixun.base.BaseModel;
import com.tianyu.weizixun.bean.DailyNewsDetailsBean;
import com.tianyu.weizixun.callback.DailyNewsDetailsCallback;
import com.tianyu.weizixun.net.ApiService;
import com.tianyu.weizixun.net.BaseObserver;
import com.tianyu.weizixun.net.HttpManager;
import com.tianyu.weizixun.utils.RxUtil;

/**
 * @date：2020/6/16
 * @describe：知乎详情
 * @author：TianYu
 */
public class DailyNewsDetailsModel extends BaseModel {
    public void getData(int id, DailyNewsDetailsCallback callback){
        HttpManager.getHttpManager()
                .getApiService(ApiService.baseZhihuUrl,ApiService.class)
                .getDailyNewsDetails(id)
                .compose(RxUtil.rxFlowableTransformer())
                .subscribe(new BaseObserver<DailyNewsDetailsBean>() {
                    @Override
                    protected void onSuccess(DailyNewsDetailsBean dailyNewsDetailsBean) {
                        if (dailyNewsDetailsBean != null) {
                            callback.onSuccess(dailyNewsDetailsBean);
                        } else {
                            callback.onFail("请求失败");
                        }
                    }
                });

    }
}
