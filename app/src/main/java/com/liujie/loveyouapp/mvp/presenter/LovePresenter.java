package com.liujie.loveyouapp.mvp.presenter;

import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.contract.LoveContract;

public class LovePresenter extends BasePresenter<LoveContract.Model, LoveContract.View> {
    public LovePresenter(LoveContract.View mView, LoveContract.Model mModel) {
        super(mView, mModel);
    }
}
