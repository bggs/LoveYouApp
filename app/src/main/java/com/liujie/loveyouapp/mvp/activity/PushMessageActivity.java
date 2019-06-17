package com.liujie.loveyouapp.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.app.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关于我们
 */
public class PushMessageActivity extends BaseActivity {
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.bottom_left)
    ImageView bottomLeft;
    @BindView(R.id.bottom_right)
    ImageView bottomRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_push_message;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        tvMsg.setText(content);
        initAnim();
    }

    private void initAnim() {
        // 平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 350, 0, 400);
        translateAnimation.setDuration(3000);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        topLeft.startAnimation(translateAnimation);

        TranslateAnimation bAnimation = new TranslateAnimation(0, -300, 45, 500);
        bAnimation.setDuration(3000);
        bAnimation.setRepeatCount(Animation.INFINITE);
        topRight.startAnimation(bAnimation);

        TranslateAnimation blAnimation = new TranslateAnimation(0, 300, -45, -450);
        blAnimation.setDuration(3000);
        blAnimation.setRepeatCount(Animation.INFINITE);
        bottomLeft.startAnimation(blAnimation);

        TranslateAnimation tAnimation = new TranslateAnimation(0, -450, -45, -450);
        tAnimation.setDuration(3000);
        tAnimation.setRepeatCount(Animation.INFINITE);
        bottomRight.startAnimation(tAnimation);

        // 旋转动画
        Animation rotate = new RotateAnimation(0, 360, 100, 0);
        rotate.setDuration(3000);
        rotate.setRepeatCount(Animation.INFINITE);
        tvMsg.startAnimation(rotate);
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