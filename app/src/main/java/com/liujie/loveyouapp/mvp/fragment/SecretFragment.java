package com.liujie.loveyouapp.mvp.fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.app.BaseFragment;
import com.liujie.loveyouapp.mvp.adapter.SecretAdapter;
import com.liujie.loveyouapp.mvp.contract.SecretContract;
import com.liujie.loveyouapp.mvp.model.SecretResponse;
import com.liujie.loveyouapp.mvp.model.SecretModel;
import com.liujie.loveyouapp.mvp.presenter.SecretPresenter;
import com.liujie.loveyouapp.mvp.view.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

import static com.liujie.loveyouapp.app.BaseActivity.showShort;

/**
 * 密友的主页面
 */
public class SecretFragment extends BaseFragment<SecretPresenter> implements SecretContract.View {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mListView)
    ListView mListview;

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private SecretAdapter adapter;
    private List<SecretResponse> addressList = new ArrayList<>();

    public SecretFragment() {
    }


    public static SecretFragment newInstance(String param1) {
        SecretFragment fragment = new SecretFragment();
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
        return R.layout.fragment_secret;
    }

    @Override
    protected void afterCreate() {
        title.setText("密友");
        addressList.clear();
        adapter = new SecretAdapter(getActivity(), addressList);
        showPermission(new String[]{Manifest.permission.READ_CONTACTS}, "联系人");
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + addressList.get(i).getPhoneNum());
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    /**
     * 获取权限成功
     */
    protected void permissionSuccess() {
        readContacts();
    }

    /**
     * 获取联系人的姓名和手机号
     */
    private void readContacts() {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    cols, null, null, null);
            if (cursor != null) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    // 取得联系人名字
                    int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String name = cursor.getString(nameFieldColumnIndex);
                    String phoneNum = cursor.getString(numberFieldColumnIndex);

                    addressList.add(new SecretResponse(name, phoneNum));
                }
                if (addressList.size() == 0) {
                    showShort(getActivity(), "您当前无联系人!");
                } else {
                    Collections.sort(addressList, new Sort.AddressCompartor());
                    mListview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //记得关掉cursor
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected SecretPresenter createPresenter() {
        return new SecretPresenter(this, new SecretModel());
    }
}