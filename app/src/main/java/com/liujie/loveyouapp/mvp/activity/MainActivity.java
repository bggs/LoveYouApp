package com.liujie.loveyouapp.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseActivity;
import com.liujie.loveyouapp.mvp.Utils.StatusBarUtils;
import com.liujie.loveyouapp.mvp.fragment.DynamicFragment;
import com.liujie.loveyouapp.mvp.fragment.LoveFragment;
import com.liujie.loveyouapp.mvp.fragment.MyFragment;
import com.liujie.loveyouapp.mvp.fragment.SecretFragment;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity {

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.setStatusBarTranslucent(this, false);
        StatusBarUtils.setStatusColor(this, getResources().getColor(R.color.colorFF9266));
        initView();
    }

    private void initView() {
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.tab_love, LoveFragment.newInstance("小恩爱"));
        mFragmentSparseArray.append(R.id.tab_dynamic, DynamicFragment.newInstance("动态"));
        mFragmentSparseArray.append(R.id.tab_secret, SecretFragment.newInstance("密友"));
        mFragmentSparseArray.append(R.id.tab_my, MyFragment.newInstance("我的"));
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
            }
        });
        // 默认显示第一个
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.tab_love)).commit();
        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddWebviewActivity.class));

            }
        });
    }

    // 记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                BaseActivity.showShort(MainActivity.this, "再按一次退出应用");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}