package com.liujie.loveyouapp.mvp.presenter;

import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.contract.SecretContract;

public class SecretPresenter extends BasePresenter<SecretContract.Model, SecretContract.View> {
    public SecretPresenter(SecretContract.View mView, SecretContract.Model mModel) {
        super(mView, mModel);
    }
}
