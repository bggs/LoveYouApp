package com.liujie.loveyouapp.mvp.Utils;

import com.liujie.loveyouapp.mvp.service.Api;
import com.liujie.loveyouapp.mvp.service.MoviceService;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络帮助类
 */
public class RetrofitHelper {
    private static OkHttpClient mOkHttpClient;
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String TAG = RetrofitHelper.class.getCanonicalName();
    public static boolean quitLogin = true;

    /**
     * 初始化 请求
     */
    static {
        initOkHttpClient();
//        mOkHttpClient = genericClient();
    }

    //APP
    public static MoviceService getProvingServiceApi() {
        return createApi(MoviceService.class, Api.BASE_URL);
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {

        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
//        genericClient();
    }


    /**
     * 添加统一header,超时时间,http日志打印
     *
     * @return
     */
//    private static OkHttpClient genericClient() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//
//                        request = request.newBuilder()
//                                .addHeader("userid", LoginUserInfo.getUserId())
//                                .addHeader("token", getToken())
//                                .addHeader("timestamp", timeString).build();
//                        Log.e("aaaaaa", "timeString:" + timeString);
//                        Response originalResponse;
//                        try {
//                            originalResponse = chain.proceed(request);
//                            int code = originalResponse.code();
//                            if (code == 401 && quitLogin) {//此时退出登录去登录
//                                BaseApplication.startLogin();
//                            }
//                            LogUtils.e(TAG, "genericClient  code:" + code);
//                            String token = originalResponse.headers().get("token");
//                            if (!TextUtils.isEmpty(token)) {
//                                LoginUserInfo.setUserPassword(token);
//                            }
//                            if (!TextUtils.isEmpty(LoginUserInfo.getUserId())
//                                    && LoginUserInfo.getUserTokenCount() > LoginUserInfo.REFRESH_TOKEN_COUNT) {//到达次数 刷新token
//                                if (request.url().toString().indexOf("/api/LogIn/RefreshTokenByUserName") == -1 &&
//                                        request.url().toString().indexOf("/api/LogIn/RefreshToken") == -1) {//判断url
//                                    refreshToken();
//                                }
//                            }
//                            final String header = originalResponse.headers().toString();
//                            Log.e("aaaaaa", "header:" + header + "token:" + token);
//                        } catch (Exception e) {
//                            throw e;
//                        } finally {
//                            LoginUserInfo.setUserTokenCount(1, true);
//                        }
//                        return originalResponse;
//                    }
//                })
//                .addInterceptor(logging)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
//                .build();
//        return httpClient;
//    }
    private static void refreshToken() {//直接刷新
//        RetrofitHelper.getProvingServiceApi()
//                .refreshToken()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseResponse<String>>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse<String> baseResponse) {
//                        LoginUserInfo.setUserTokenCount(1, false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {//错误
//                        refreshToken(LoginUserInfo.getUserName(), LoginUserInfo.getUserPassword());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//    private static void refreshToken(String userName, String passWord) {//需要信息刷新
//        RetrofitHelper.getProvingServiceApi()
//                .refreshToken(userName, passWord)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseResponse<String>>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse<String> baseResponse) {
//                        LoginUserInfo.setUserTokenCount(1, false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {//错误
//                        LoginUserInfo.setUserTokenCount(1, false);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//    public static String timeString;//时间戳
//
//    public static String getToken() {
//        timeString = TimeDataUtils.getUTCTimeStr() + "";
//        Log.e("aaaaaa", "timeString:" + timeString);
//        String str = LoginUserInfo.getUserId() + LoginUserInfo.getUserPassword() + timeString;
//        Log.e("aaaaaa", "timeString:" + timeString + "   str:" + str);
//        return MD5Uitls.getMd5Value(str).toUpperCase();
//    }

//    private static String bodyToString(final RequestBody request) {
//        try {
//            final RequestBody copy = request;
//            final Buffer buffer = new Buffer();
//            if (copy != null)
//                copy.writeTo(buffer);
//            else
//                return "";
//            return buffer.readUtf8();
//        } catch (final IOException e) {
//            return "did not work";
//        }
    }

}
