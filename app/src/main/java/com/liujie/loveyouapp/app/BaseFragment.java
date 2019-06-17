package com.liujie.loveyouapp.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liujie.loveyouapp.mvp.dialog.CustomDialog;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author wavewave
 * @CreateDate: 2019/3/21 10:36 AM
 * @Description:基类
 * @Version: 1.0
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    protected P mPresenter;

    protected abstract int getLayoutId();

    protected abstract void afterCreate();

    protected void setListener() {

    }

    protected abstract P createPresenter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterCreate();
    }

    @Override
    public void showMessage(String msg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMessage(msg);
        }
    }

    @Override
    public void showLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    /**
     * 权限组
     */
    private List<String> mPermissionList = new ArrayList<>();

    /**
     * 获取权限成功
     */
    protected void permissionSuccess() {

    }

    private String permissionName;//权限名字

    /**
     * 用于申请权限处理
     * 子类复写 permissionSuccess
     * 用于权限申请成功回调
     * permissionName 权限名字
     *
     * @param permissions
     */
    public void showPermission(String[] permissions, String permissionName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionList.clear();
            this.permissionName = permissionName;
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(getActivity(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            //未授予的权限为空，表示都授予了
            if (mPermissionList.isEmpty()) {
                permissionSuccess();
            } else {
                //请求权限方法
                //将List转为数组
                permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                requestPermissions(permissions, 1);
//                ActivityCompat.requestPermissions(getActivity(), permissions, 1);
            }
        } else {
            permissionSuccess();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
//            ActivityCompat.requestPermissions(getActivity(), mPermissionList.toArray(new String[mPermissionList.size()]), 1);
            requestPermissions(mPermissionList.toArray(new String[mPermissionList.size()]), 1);
        }
    }

    //处理权限申请回调(写在Activity中)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // 授权被允许
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionSuccess();
                } else {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        new CustomDialog(getActivity(), "缺失权限！", "我们需要您授权" + permissionName + "权限！",
                                "设置权限", "设置权限",
                                1, 4, new CustomDialog.DialogKnowListener() {
                            @Override
                            public void onDialogKnowListener() {
                                //权限授予了
                                if (grantResults.length > 0
                                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                    permissionSuccess();
                                } else {
                                    //请求权限方法
                                    //将List转为数组
                                    if (!shouldShowRequestPermissionRationale(permissions[0])) {
                                        // 跳转到应用设置界面
                                        goToAppSetting();
                                    } else {
                                        showPermission(mPermissionList.toArray(new String[mPermissionList.size()]), permissionName);
                                    }
                                }
                            }
                        }, new CustomDialog.DialogListener() {
                            @Override
                            public void onDialogOkListener() {
                                //权限授予了
                                if (grantResults.length > 0
                                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                    permissionSuccess();
                                } else {
                                    //请求权限方法
                                    //将List转为数组
                                    if (!shouldShowRequestPermissionRationale(permissions[0])) {
                                        // 跳转到应用设置界面
                                        goToAppSetting();
                                    } else {
                                        showPermission(mPermissionList.toArray(new String[mPermissionList.size()]), permissionName);
                                    }
                                }
                            }

                            @Override
                            public void onDialogCancelListener() {
                                //权限授予了
                                if (grantResults.length > 0
                                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                    permissionSuccess();
                                } else {
                                    //请求权限方法
                                    //将List转为数组
                                    if (!shouldShowRequestPermissionRationale(permissions[0])) {
                                        // 跳转到应用设置界面
                                        goToAppSetting();
                                    } else {
                                        showPermission(mPermissionList.toArray(new String[mPermissionList.size()]), permissionName);
                                    }
                                }
                            }
                        }).show();
                    }
                }
                return;
            }
        }
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }
}
