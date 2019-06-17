package com.liujie.loveyouapp.mvp.presenter;

import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.contract.DynamicContract;

public class DynamicPresenter extends BasePresenter<DynamicContract.Model, DynamicContract.View> {
    public DynamicPresenter(DynamicContract.View mView, DynamicContract.Model mModel) {
        super(mView, mModel);
    }
}
