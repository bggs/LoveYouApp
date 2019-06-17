package com.liujie.loveyouapp.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * 手势密码验证
 */
public class GestureCheckingActivity extends BaseActivity {

    @BindView(R.id.pattern_indicator_view)
    PatternIndicatorView patternIndicatorView;
    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockView;
    @BindView(R.id.text_msg)
    TextView textMsg;
    @BindView(R.id.tv_forgetGesturePwd)
    TextView tvForgetGesturePwd;
    @BindView(R.id.title)
    TextView title;
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
                boolean isError = !isPatternOk(hitList);
                if (isError == true) {
                    if (patternHelper.getMessage().contains("0")) {
                        SharedPreferencesHelper.clear(GestureCheckingActivity.this);
                        startActivity(new Intent(GestureCheckingActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        GestureCheckingActivity.this.finish();
                        SharedPreferences.Editor editor = SharedPreferencesHelper.getSharedPreferenceUser(GestureCheckingActivity.this).edit();
                        editor.clear();
                        editor.commit();
                    } else {
                        view.updateStatus(isError);
                        patternIndicatorView.updateState(hitList, isError);
                        updateMsg();
                    }
                }
            }

            @Override
            public void onClear(PatternLockerView view) {
                finishIfNeeded();
            }
        });
        //忘记手势密码的监听
        tvForgetGesturePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesHelper.clear(GestureCheckingActivity.this);
                startActivity(new Intent(GestureCheckingActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                GestureCheckingActivity.this.finish();
                SharedPreferences.Editor editor = SharedPreferencesHelper.getSharedPreferenceUser(GestureCheckingActivity.this).edit();
                editor.clear();
                editor.commit();
            }
        });
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setListener();
        title.setText("解锁手势密码");
        this.textMsg.setText("绘制解锁图案");
        this.patternHelper = new PatternHelper();
    }

    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForChecking(hitList);
        return this.patternHelper.isOk();
    }

    private void updateMsg() {
        this.textMsg.setText(this.patternHelper.getMessage());
        this.textMsg.setTextColor(this.patternHelper.isOk() ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.colorAccent));
    }

    private void finishIfNeeded() {
        if (this.patternHelper.isFinish()) {
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
        isActive = true;
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //返回手机的主屏幕
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
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
