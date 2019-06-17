package com.liujie.loveyouapp.mvp.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.app.BasePresenter;
import com.liujie.loveyouapp.mvp.adapter.ViewPagerAdatper;
import com.liujie.loveyouapp.mvp.service.ResultObserver;
import com.liujie.loveyouapp.mvp.view.CustomVideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.in_viewpager)
    ViewPager inViewpager;

    private ResultObserver<Long> observer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        observer = new ResultObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                List<View> mViewList = new ArrayList<>();
                LayoutInflater lf = getLayoutInflater().from(SplashActivity.this);
//                View view1 = lf.inflate(R.layout.view_guide_one, null);
                final View view2 = lf.inflate(R.layout.view_guide_second, null);
//                View view3 = lf.inflate(R.layout.view_guide_second, null);
//                mViewList.add(view1);
                mViewList.add(view2);
//                mViewList.add(view3);
                inViewpager.setAdapter(new ViewPagerAdatper(mViewList));

//                final CustomVideoView videoview = view1.findViewById(R.id.videoview);
//                //加载视频文件
//                videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.support ));
//                //播放
//                videoview.start();
                //播放结束后的动作
//                videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        SplashActivity.this.finish();
//                    }
//                });
                final CustomVideoView customVideoView = view2.findViewById(R.id.videoview);
                customVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videoone));
                customVideoView.start();
                //播放结束后的动作
                customVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        SplashActivity.this.finish();
                    }
                });
//                view2.findViewById(R.id.btn_experience).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        SplashActivity.this.finish();
//                    }
//                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }

        ;
        Observable.timer(2, TimeUnit.SECONDS).

                subscribeOn(AndroidSchedulers.mainThread()).

                observeOn(AndroidSchedulers.mainThread()).

                subscribe(observer);

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
