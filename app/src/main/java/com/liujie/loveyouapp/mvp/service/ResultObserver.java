package com.liujie.loveyouapp.mvp.service;

import io.reactivex.observers.DisposableObserver;

public abstract class ResultObserver<T> extends DisposableObserver<T> {

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
