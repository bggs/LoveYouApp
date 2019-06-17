package com.liujie.loveyouapp.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseApplication;
import com.liujie.loveyouapp.app.BaseFragment;
import com.liujie.loveyouapp.mvp.Utils.CacheDataManager;
import com.liujie.loveyouapp.mvp.Utils.PlatformUtil;
import com.liujie.loveyouapp.mvp.Utils.SharedPreferencesHelper;
import com.liujie.loveyouapp.mvp.Utils.StatusBarUtils;
import com.liujie.loveyouapp.mvp.activity.AboutActivity;
import com.liujie.loveyouapp.mvp.activity.LockSettingActivity;
import com.liujie.loveyouapp.mvp.activity.LockSwitchActivity;
import com.liujie.loveyouapp.mvp.contract.MyContract;
import com.liujie.loveyouapp.mvp.dialog.CustomDialog;
import com.liujie.loveyouapp.mvp.model.MyModel;
import com.liujie.loveyouapp.mvp.presenter.MyPresenter;
import com.liujie.loveyouapp.mvp.view.TrembleAnim;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

import static com.liujie.loveyouapp.app.BaseActivity.showShort;

/**
 * 我的
 */
public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {

    @BindView(R.id.tv_cacheClean)
    TextView tv_cacheClean;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private IWXAPI wxApi;

    private String OKFileName = " app-debug.apk";

    public MyFragment() {
    }

    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void afterCreate() {
        StatusBarUtils.setStatusBarTranslucent(getActivity(), false);
        StatusBarUtils.setStatusColor(getActivity(), getResources().getColor(R.color.colorFF9266));
        wxApi = WXAPIFactory.createWXAPI(getActivity(), BaseApplication.appID);
        wxApi.registerApp(BaseApplication.appID);
        collapsingToolbarLayout.setTitle("我的");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        String totalCacheSize = null;
        try {
            totalCacheSize = CacheDataManager.getTotalCacheSize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_cacheClean.setText(totalCacheSize);
    }
//    在这个特别的日子里，我没有别的话，只想你知道，每年今天，你都会收到我的祝福，感到我的爱，永远的爱!
//    天天都在你身边，不用苦苦地想你，也不必酸酸地恋你，只需甜甜地爱着你！

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(this, new MyModel());
    }

    @OnClick({R.id.rl_tremble, R.id.rl_wxfriend, R.id.rl_clearCache, R.id.rl_setting, R.id.rl_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tremble:
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(2000);//振动两秒
//                long[] pattern = { 200, 2000, 2000, 200, 200, 200 };
//                vibrator.vibrate(pattern, -1);// 下边是可以使震动有规律的震动   -1：表示不重复 0：循环的震动

                TrembleAnim anim = new TrembleAnim();
                anim.setDuration(800);
                anim.setRepeatCount(2);
                coordinatorLayout.startAnimation(anim);
                break;
            case R.id.rl_wxfriend://分享
                if (PlatformUtil.isInstallApp(getActivity(), "微信")) {
                    showShort(getActivity(), "亲，您还未安装客户端!");
                } else {
                    UMWeb web = new UMWeb("https://blog.csdn.net/qq_38913065");
                    web.setTitle("刘婕给你们分享福利咯 !!!");
                    web.setThumb(new UMImage(getActivity(), "https://blog.csdn.net/qq_38913065"));  //缩略图
                    web.setDescription("https://blog.csdn.net/qq_38913065");

                    new ShareAction(getActivity())
                            .withMedia(web)
                            .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                            .setCallback(shareListener)
                            .open();
                }
                break;
            case R.id.rl_clearCache://清除缓存
                new CustomDialog(getActivity(), "清理缓存", "您确定清除缓存数据吗?", "取消", "确定", new CustomDialog.DialogListener() {
                    @Override
                    public void onDialogOkListener() {
                        try {
                            CacheDataManager.clearAllCache(getActivity());
                            String totalCacheSize = CacheDataManager.getTotalCacheSize(getActivity());
                            tv_cacheClean.setText(totalCacheSize);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        showShort(getActivity(), "亲，缓存清除成功!");
                    }

                    @Override
                    public void onDialogCancelListener() {

                    }
                }).show();
                break;
            case R.id.rl_setting://安全设置
                if (SharedPreferencesHelper.getBooleanUser(getActivity(), "isSettingPwd", false)) {
                    Intent intent = new Intent(getActivity(), LockSwitchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LockSettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_about://关于我们
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }

    private static UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            showShort(BaseApplication.context, "分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            showShort(BaseApplication.context, "分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showShort(BaseApplication.context, "取消了");
        }
    };

    /**
     * 将输入的流转换为byte数组
     *
     * @return byte数组
     */
    public static byte[] inputStreamToByte(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = fis.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}