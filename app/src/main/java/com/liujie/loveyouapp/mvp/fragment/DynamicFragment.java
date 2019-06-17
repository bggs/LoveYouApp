package com.liujie.loveyouapp.mvp.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseFragment;
import com.liujie.loveyouapp.mvp.adapter.DynamicAdapter;
import com.liujie.loveyouapp.mvp.contract.DynamicContract;
import com.liujie.loveyouapp.mvp.model.DynamicModel;
import com.liujie.loveyouapp.mvp.model.DynamicResponse;
import com.liujie.loveyouapp.mvp.presenter.DynamicPresenter;
import com.liujie.loveyouapp.mvp.service.OnItemClickListener;
import com.luolc.emojirain.EmojiRainLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.liujie.loveyouapp.app.BaseActivity.showShort;

/**
 * 动态的主页面
 */
public class DynamicFragment extends BaseFragment<DynamicPresenter> implements DynamicContract.View {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler)
    RecyclerView rlvHomeYt;
    @BindView(R.id.group_emoji_container)
    EmojiRainLayout mContainer;

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    List<DynamicResponse> data = new ArrayList<>();

    public DynamicFragment() {
    }


    public static DynamicFragment newInstance(String param1) {
        DynamicFragment fragment = new DynamicFragment();
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
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void afterCreate() {
        title.setText("动态");
        data.clear();
        for (int i = 0; i < 10; i++) {
            data.add(new DynamicResponse("你的爱人已发动态,点击立即查看" + i, "描述" + i));
        }
        rlvHomeYt.setLayoutManager(new LinearLayoutManager(getActivity()));
        DynamicAdapter adapter = new DynamicAdapter(getActivity());
        adapter.setDataList(data);
        rlvHomeYt.setAdapter(adapter);
        rlvHomeYt.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                showShort(getActivity(), postion + "");
            }
        });
        // add emoji sources
        mContainer.addEmoji(R.mipmap.emoji_one);
        mContainer.addEmoji(R.mipmap.emoji_two);
        mContainer.addEmoji(R.mipmap.emoji_three);
        mContainer.addEmoji(R.mipmap.emoji_four);
        mContainer.addEmoji(R.mipmap.emoji_five);
        mContainer.setPer(7);  //每一波掉落的emoji个数，默认6个
        mContainer.setDuration(7200);// 掉落动画持续的总时长，默认8000ms
        mContainer.setDropDuration(2400); // 每个emoji掉落时长的平均值，默认2400ms
        mContainer.setDropFrequency(500);// 掉落频率，即每两拨的时间间隔，默认500ms
        mContainer.startDropping();

    }

    @Override
    protected DynamicPresenter createPresenter() {
        return new DynamicPresenter(this, new DynamicModel());
    }
}