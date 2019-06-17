package com.liujie.loveyouapp.app;

import android.app.Application;
import android.content.Context;

import com.liujie.loveyouapp.mvp.view.CrashHandler;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {
    public static final String appID = "wxcce8c94bb2981211";
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        IWXAPI wxapi = WXAPIFactory.createWXAPI(getApplicationContext(), appID, true);
        wxapi.registerApp(appID);
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5cbd37743fc1950d43000818", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wxcce8c94bb2981211", "72e4bc57be4cb86a27e85d4a4c52e6d2");//AppSecret 72e4bc57be4cb86a27e85d4a4c52e6d2
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setQQZone("1108851338", "BNPozEfb0LqrPArt");
        CrashHandler.getInstance().init(getApplicationContext());

        JAnalyticsInterface.init(context);//统计
        JAnalyticsInterface.setDebugMode(true);
        JPushInterface.setDebugMode(true);//推送
        JPushInterface.init(this);
    }
}
