package com.liujie.loveyouapp.app;

public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {
    protected M mModel;

    public BasePresenter(V mView, M mModel) {
        if (mView == null) {
            throw new NullPointerException("view不可以为null");
        }
        if (mModel == null) {
            throw new NullPointerException("model不可以null");
        }
        this.mModel = mModel;
    }

}
