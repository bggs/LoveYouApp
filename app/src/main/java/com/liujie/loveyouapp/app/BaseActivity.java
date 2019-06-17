package com.liujie.loveyouapp.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.liujie.loveyouapp.mvp.Utils.SharedPreferencesHelper;
import com.liujie.loveyouapp.mvp.Utils.StatusBarUtils;
import com.liujie.loveyouapp.mvp.activity.GestureCheckingActivity;
import com.liujie.loveyouapp.mvp.dialog.CustomProgressDialog;
import com.liujie.loveyouapp.R;

import java.util.List;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseActivity<P extends BasePresenter> extends SwipeBackActivity
        implements BaseView {
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract P createPresenter();

    protected void setListener() {
    }

    protected abstract void afterCreate(Bundle savedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        通过对象获取class，通过class查询缓存 看看有没有，如果没有，就用classLoder加载一个 clsName_ViewBinding类文件，这个就是刚刚生成，然后拿到构造方法，传入布局和对象，
// 创建clsName_ViewBinding 对象，而clsName_ViewBinding的构造方法就是进行findViewById了
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        afterCreate(savedInstanceState);
        StatusBarUtils.setStatusBarTranslucent(this, false);
        StatusBarUtils.setStatusColor(this, getResources().getColor(R.color.colorFF9266));
        initView();
        setListener();
        //自定义设置滑动
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);//设定从哪个方向可以滑动---左滑动
        swipeBackLayout.setEdgeSize(200);//来设置滑动触发的范围
        swipeBackLayout.setScrimColor(R.color.colorAccent);//来设置滑动返回的背景色
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        super.setContentView(layoutResID);
    }

    private CustomProgressDialog customDialog;
    public static boolean isActive = true;

    protected void showLoadingView() {
        if (customDialog == null)
            customDialog = new CustomProgressDialog(this, R.style.CustomDialog, false);
        customDialog.show();
    }

    protected void hideLoadingView() {
        if (customDialog != null) {
            customDialog.dismiss();
        }
    }

    //小米手机 Toast会显示APP名字的问题解决
    public static void showShort(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnFreground()) {
            isActive = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            //从后台唤醒
            isActive = true;
            if (SharedPreferencesHelper.getBooleanUser(BaseActivity.this, "isSettingPwd", false) && SharedPreferencesHelper.getBooleanUser(BaseActivity.this, "isOpen", false)) {
                Intent intent = new Intent(BaseActivity.this, GestureCheckingActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 是否在后台
     */
    public boolean isAppOnFreground() {
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if (app == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo a : app) {
            if (a.processName.equals(curPackageName) && a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
