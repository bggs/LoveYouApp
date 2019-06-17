package com.liujie.loveyouapp.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.app.BasePresenter;

import butterknife.BindView;

/**
 * 安全设置
 */
public class LockSettingActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_setup)
    TextView tvSetup;
    private static final int SETDIATA = 2000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lock_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    protected void setListener() {
        //创建手势的监听
        tvSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LockSettingActivity.this, GestureLockActivity.class);
                startActivityForResult(intent, SETDIATA);
            }
        });
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        title.setText("安全设置");
        setListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SETDIATA && data != null) {
            startActivity(new Intent(LockSettingActivity.this, LockSwitchActivity.class));
            LockSettingActivity.this.finish();
        }
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
