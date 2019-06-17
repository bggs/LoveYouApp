package com.liujie.loveyouapp.mvp.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.Utils.StatusBarUtils;
import com.liujie.loveyouapp.mvp.view.TouchImageView;

import butterknife.BindView;

/**
 * 展示大图
 */
public class ImageActivity extends BaseActivity {
    @BindView(R.id.touchImageView)
    TouchImageView touchImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        StatusBarUtils.setStatusBarTranslucent(ImageActivity.this, false);
        StatusBarUtils.setStatusColor(ImageActivity.this, getResources().getColor(R.color.colorFF9266));
    }

    @Override
    protected void initView() {
//        Uri uri = (Uri) getIntent().getExtras().getParcelable("uri");
        int uri = getIntent().getIntExtra("header_me", 0);
        touchImageView.setImageResource(uri);
//        touchImageView.setImageURI(uri);
//        Glide.with(this).load(uri).into(touchImageView);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}
