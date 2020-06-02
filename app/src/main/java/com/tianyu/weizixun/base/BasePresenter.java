package com.tianyu.weizixun.base;

import java.util.ArrayList;

public abstract class BasePresenter<V extends BaseView> {
    public V mView;
    private ArrayList<BaseModel> models = new ArrayList<>();

    public void setmView(V mView) {
        this.mView = mView;
    }

    public BasePresenter() {
        initModel();
    }

    protected abstract void initModel();

    public void onDestroy() {
        mView = null;
        if (models.size() > 0) {
            for (BaseModel model : models) {
                model.onDestroy();
            }
            models.clear();
        }
    }

    public void addModel(BaseModel model) {
        models.add(model);
    }
}
