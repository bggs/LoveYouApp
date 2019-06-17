package com.liujie.loveyouapp.mvp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.liujie.loveyouapp.mvp.activity.MainActivity;
import com.liujie.loveyouapp.mvp.activity.SplashActivity;

public class MyReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent i = new Intent(context, SplashActivity.class);
//            Intent i = new Intent(context, MainActivity.class);
            i.setAction("android.intent.action.MAIN");
            i.addCategory("android.intent.category.LAUNCHER");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//非常重要，如果缺少的话，程序将在启动时报错
            context.startActivity(i);
            Toast.makeText(context,"我自启动成功了哈",Toast.LENGTH_LONG).show();
        }
    }
}
