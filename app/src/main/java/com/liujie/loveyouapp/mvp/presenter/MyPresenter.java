package com.liujie.loveyouapp.mvp.presenter;

import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.contract.MyContract;

public class MyPresenter extends BasePresenter<MyContract.Model, MyContract.View> {
    public MyPresenter(MyContract.View mView, MyContract.Model mModel) {
        super(mView, mModel);
    }
}
