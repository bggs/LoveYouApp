package com.liujie.loveyouapp.wxapi;

import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

}
//public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
//    private IWXAPI api;
//    private WXShare wxShare;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxentry);
//        wxShare = new WXShare(this);
//        wxShare.setListener(new OnResponseListener() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onCancel() {
//                finish();
//            }
//
//            @Override
//            public void onFail(String message) {
//
//            }
//        });
//        api = wxShare.getApi();
//        wxShare.share("这是要分享的文字");
//        wxShare.shareUrl(0, this, "https://open.weixin.qq.com", "微信分享", "I am so crazy");
//        //注意：
//        // 第三方开发者如果使用透明界面来实现WXEntryActivity，
//        // 需要判断handleIntent的返回值，如果返回值为false，
//        // 则说明入参不合法未被SDK处理，应finish当前透明界面，避
//        // 免外部通过传递非法参数的Intent导致停留在透明界面，
//        // 引起用户的疑惑
//        try {
//            if (!api.handleIntent(getIntent(), this)) {
//                finish();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        wxShare.register();
//    }
//
//    @Override
//    protected void onDestroy() {
//        wxShare.unregister();
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        if (!api.handleIntent(intent, this)) {
//            finish();
//        }
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
//        Intent intent = new Intent(WXShare.ACTION_SHARE_RESPONSE);
//        intent.putExtra(WXShare.EXTRA_RESULT, new WXShare.Response(baseResp));
//        sendBroadcast(intent);
//        finish();
//    }