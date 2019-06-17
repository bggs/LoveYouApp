package com.liujie.loveyouapp.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.Utils.PatternHelper;
import com.liujie.loveyouapp.mvp.Utils.SharedPreferencesHelper;

import java.util.List;

import butterknife.BindView;

/**
 * 手势密码设置
 */
public class GestureLockActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.pattern_indicator_view)
    PatternIndicatorView patternIndicatorView;
    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockView;
    @BindView(R.id.text_msg)
    TextView textMsg;
    @BindView(R.id.tv_forgetGesturePwd)
    TextView tv_forgetGesturePwd;

    private PatternHelper patternHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gesture_lock;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    protected void setListener() {
        patternLockView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(PatternLockerView view) {
            }

            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {
            }

            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                boolean isOk = isPatternOk(hitList);
                view.updateStatus(!isOk);
                patternIndicatorView.updateState(hitList, !isOk);
                updateMsg();
            }

            @Override
            public void onClear(PatternLockerView view) {
                finishIfNeeded();
            }
        });
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        title.setText("设置手势密码");
        setListener();
        tv_forgetGesturePwd.setVisibility(View.GONE);
        textMsg.setText("设置解锁图案");
        patternHelper = new PatternHelper();
        if (SharedPreferencesHelper.getBooleanUser(GestureLockActivity.this, "isSettingPwd", false)) {
            title.setText("修改手势密码");
        }
    }


    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForSetting(hitList);
        return this.patternHelper.isOk();
    }

    private void updateMsg() {
        textMsg.setText(patternHelper.getMessage());
        textMsg.setTextColor(patternHelper.isOk() ? getResources().getColor(R.color.color333333) : getResources().getColor(R.color.colorAccent));
        if (textMsg.getText().equals("手势解锁图案设置成功！")) {
            SharedPreferencesHelper.putBooleanUser(GestureLockActivity.this, "isSettingPwd", true);
            SharedPreferencesHelper.putBooleanUser(GestureLockActivity.this, "isOpen", true);
            showShort(GestureLockActivity.this, "设置成功!");
        }
    }

    private void finishIfNeeded() {
        if (patternHelper.isFinish()) {
            Intent intent = getIntent();
            setResult(Activity.RESULT_OK, intent);
            finish();
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}
