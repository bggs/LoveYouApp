package com.liujie.loveyouapp.mvp.presenter;

import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.contract.SecretDetailContract;

public class SecretDetailPresenter extends BasePresenter<SecretDetailContract.Model, SecretDetailContract.View> {
    public SecretDetailPresenter(SecretDetailContract.View mView, SecretDetailContract.Model mModel) {
        super(mView, mModel);
    }
}
