package com.liujie.loveyouapp.mvp.activity;

import android.os.Bundle;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.mvp.contract.SecretContract;
import com.liujie.loveyouapp.mvp.presenter.SecretDetailPresenter;

public class SecretDetailActivity extends BaseActivity<SecretDetailPresenter> implements SecretContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_secret_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected SecretDetailPresenter createPresenter() {
        return null;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {


    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void showMessage(String message) {


    }
}
