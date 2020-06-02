package com.tianyu.weizixun.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel {
    private CompositeDisposable compositeDisposable;

    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    // TODO: 2020/6/2  
    public void addmodel(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
