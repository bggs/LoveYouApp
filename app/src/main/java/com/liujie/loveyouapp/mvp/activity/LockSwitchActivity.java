package com.liujie.loveyouapp.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.Utils.SharedPreferencesHelper;

import butterknife.BindView;

/**
 * 手势开关
 */
public class LockSwitchActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.swh_status)
    ToggleButton swhStatus;
    @BindView(R.id.rl_update)
    RelativeLayout rlUpdate;

    public static final int SETDIATA = 2000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lock_switch;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    protected void setListener() {
        //开关切换的监听
        swhStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swhStatus.isChecked()) {
                    if (SharedPreferencesHelper.getBooleanUser(LockSwitchActivity.this, "isSettingPwd", true)) {
                        rlUpdate.setVisibility(View.VISIBLE);
                    } else {
                        swhStatus.setChecked(false);
                    }
                } else {
                    rlUpdate.setVisibility(View.GONE);
                }
                SharedPreferencesHelper.putBooleanUser(LockSwitchActivity.this, "isOpen", swhStatus.isChecked());//保存开关的状态
            }
        });
        //修改手势的监听
        rlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LockSwitchActivity.this, GestureLockActivity.class);
                startActivityForResult(intent, SETDIATA);
            }
        });
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        title.setText("手势密码锁定");
        setListener();
        if (SharedPreferencesHelper.getBooleanUser(LockSwitchActivity.this, "isSettingPwd", false)) {//isSettingPwd 代表有手势密码
            swhStatus.setChecked(SharedPreferencesHelper.getBooleanUser(LockSwitchActivity.this, "isOpen", true));
        } else {
            swhStatus.setChecked(false);
        }
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
            LockSwitchActivity.this.finish();
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