package com.liujie.loveyouapp.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseFragment;
import com.liujie.loveyouapp.mvp.Utils.DateUtils;
import com.liujie.loveyouapp.mvp.activity.ImageActivity;
import com.liujie.loveyouapp.mvp.contract.LoveContract;
import com.liujie.loveyouapp.mvp.heart.HeartView;
import com.liujie.loveyouapp.mvp.heart.Point;
import com.liujie.loveyouapp.mvp.model.LoveModel;
import com.liujie.loveyouapp.mvp.presenter.LovePresenter;
import com.liujie.loveyouapp.mvp.view.UPMarqueeView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.liujie.loveyouapp.app.BaseActivity.showShort;

/**
 * 小恩爱主页面
 */
public class LoveFragment extends BaseFragment<LovePresenter> implements LoveContract.View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.upTitle)
    UPMarqueeView upTitle;
    @BindView(R.id.iv_me)
    RoundedImageView ivMe;
    @BindView(R.id.iv_you)
    RoundedImageView ivYou;
    @BindView(R.id.tv_lovedays)
    TextView tvLoveDays;


    private static final String ARG_PARAM1 = "param1";
    Unbinder unbinder;
    private String mParam1;
    public static int H, W;
    private List<String> listTitle = new ArrayList<>();
    private List<View> views = new ArrayList<>();

    public LoveFragment() {
    }


    public static LoveFragment newInstance(String param1) {
        LoveFragment fragment = new LoveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getScreen(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love;
    }

    @Override
    protected void afterCreate() {
        banner.setLayoutParams(new RelativeLayout.LayoutParams(W, H / 3));
        List<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553834310766&di=97e6f59506a8b7238e168673c1161f44&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F1bfa426be03c460c0ef77e88a816ea6f33efc1e7b5e8-E9wvlk_fw658");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553834140016&di=650c644237ecbba36f9ed5d620294b6b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201304%2F28%2F20130428233751_XYHEL.jpeg");
        //简单使用
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
        listTitle.add("小婕儿和小波成为情侣了!!!");
        listTitle.add("今天是小婕儿和小波的相爱日!!!");
        listTitle.add("小婕儿和小波成为情侣了!!!");
        listTitle.add("今天是小婕儿和小波的相爱日!!!");
        setView(listTitle);
        upTitle.setViews(views);
        culLoveDate();
    }

    @Override
    protected LovePresenter createPresenter() {
        return new LovePresenter(this, new LoveModel());
    }


    @OnClick({R.id.iv_location, R.id.iv_me, R.id.iv_you})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_location:
                showShort(getActivity(), "放飞我们的爱 !!!");
                break;
            case R.id.iv_me:
//                comp();
                startActivity(new Intent(getActivity(), ImageActivity.class).putExtra("header_me", R.mipmap.header_me));
                break;
            case R.id.iv_you:
                startActivity(new Intent(getActivity(), ImageActivity.class).putExtra("header_me", R.mipmap.header));
                break;
        }
    }


    private Bitmap comp() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.header_me, options);

        options.inJustDecodeBounds = true;
//根据原始图片的宽高比和期望的输出图片的宽高比计算最终输出的图片的宽和高
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        float maxWidth = 100;//期望输出的图片宽度
        float maxHeight = 100;//期望输出的图片高度
        float srcRatio = srcWidth / srcHeight;
        float outRatio = maxWidth / maxHeight;
        float actualOutWidth = srcWidth;//最终输出的图片宽度
        float actualOutHeight = srcHeight;//最终输出的图片高度

        if (srcWidth > maxWidth || srcHeight > maxHeight) {
            if (srcRatio < outRatio) {
                actualOutHeight = maxHeight;
                actualOutWidth = actualOutHeight * srcRatio;
            } else if (srcRatio > outRatio) {
                actualOutWidth = maxWidth;
                actualOutHeight = actualOutWidth / srcRatio;
            } else {
                actualOutWidth = maxWidth;
                actualOutHeight = maxHeight;
            }
        }
        //计算sampleSize
        options.inSampleSize = caculateInSampleSize(options, actualOutWidth, actualOutHeight);
        return bitmap;
    }

    //计算采样率
    private static int caculateInSampleSize(BitmapFactory.Options options, float reqWidth, float reqHeight) {
        int width = options.outWidth; //原始图片宽
        int height = options.outHeight; //原始图片高

        int inSampleSize = 1; //采样率
        if (width > reqWidth || height > reqHeight) //原始的宽比目标宽大，或者原始高比目标高大
        {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView(List<String> toutiao) {
        for (int i = 0; i < toutiao.size(); i++) {
            //设置滚动的单个布局
            RelativeLayout moreView = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_upmarqueeview, null);
            //初始化布局的控件
            TextView title = (TextView) moreView.findViewById(R.id.itemup_title);
            TextView itemup_time = (TextView) moreView.findViewById(R.id.itemup_time);
            title.setText(toutiao.get(i));
            itemup_time.setText(DateUtils.getNow(DateUtils.FORMAT_SHORT));
            views.add(moreView);
        }
    }

    //计算相恋时间
    private void culLoveDate() {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long between = 0;
        try {
            Date begin = dfs.parse("2018-03-26 09:00:00");  //设置开始计算时间
            Date end = new Date(System.currentTimeMillis());//获取当前时间
            between = (end.getTime() - begin.getTime());// 得到两者的秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String loveHoursMinutesSeconds = hour + "时" + min + "分" + sec + "秒";
        tvLoveDays.setText(day + "");
    }
}