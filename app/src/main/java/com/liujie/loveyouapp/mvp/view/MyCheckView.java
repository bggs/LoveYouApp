package com.liujie.loveyouapp.mvp.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liujie.loveyouapp.R;
import com.liujie.loveyouapp.mvp.dialog.CustomDialog;

public class MyCheckView extends LinearLayout implements View.OnClickListener {
    private TextView check_left_tv;
    private TextView check_right_tv;
    private boolean isLeft = true;
    private boolean isDefault;//isDefault表示是否有默认选择
    private String leftName = "取消";
    private String rightName = "确认";
    private String leftContent, rightContent;
    private OnSelectClickListener onSelectClickListener;

    public MyCheckView(Context context) {
        this(context, null);
    }

    public MyCheckView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCheckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_check_my, null);
        check_left_tv = view.findViewById(R.id.check_left_tv);
        check_right_tv = view.findViewById(R.id.check_right_tv);
        check_left_tv.setOnClickListener(this);
        check_right_tv.setOnClickListener(this);
        addView(view);
    }

    public void setOnSelectClickListener(String leftContent, String rightContent, OnSelectClickListener onSelectClickListener) {
        this.leftContent = leftContent;
        this.rightContent = rightContent;
        this.onSelectClickListener = onSelectClickListener;
    }

    public void setOnSelectClickListener(boolean isDefault, String leftContent, String rightContent, OnSelectClickListener onSelectClickListener) {
        this.isDefault = isDefault;
        this.leftContent = leftContent;
        this.rightContent = rightContent;
        this.onSelectClickListener = onSelectClickListener;
    }

    private void setState(boolean isState) {
        if (isState == isLeft && isDefault) return;
        if (isState) {
            check_left_tv.setBackgroundResource(R.drawable.login_code_left);
            check_right_tv.setBackgroundDrawable(null);
            check_left_tv.setTextColor(Color.WHITE);
            check_right_tv.setTextColor(Color.parseColor("#646464"));
            if (onSelectClickListener != null) onSelectClickListener.selectLeftListener();
        } else {
            check_right_tv.setBackgroundResource(R.drawable.login_code);
            check_left_tv.setBackgroundDrawable(null);
            check_right_tv.setTextColor(Color.WHITE);
            check_left_tv.setTextColor(Color.parseColor("#646464"));
            if (onSelectClickListener != null) onSelectClickListener.selectRightListener();
        }
        isLeft = isState;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_left_tv:
                if (!isDefault) {
                    new CustomDialog(getContext(), rightContent, leftName, rightName, new CustomDialog.DialogKnowListener() {
                        @Override
                        public void onDialogKnowListener() {
                            setState(true);
                        }
                    }).show();
                } else {
                    setDefaultState();
                }

                break;
            case R.id.check_right_tv:
                if (!isDefault) {
                    new CustomDialog(getContext(), leftContent, leftName, rightName, new CustomDialog.DialogKnowListener() {

                        @Override
                        public void onDialogKnowListener() {
                            setState(false);
                        }
                    }).show();
                } else {
                    setDefaultState();
                }

                break;
            case R.id.check_ll:

                break;
        }
        isDefault = true;
    }

    private void setDefaultState() {
        new CustomDialog(getContext(), isLeft ? leftContent : rightContent, leftName, rightName, new CustomDialog.DialogKnowListener() {

            @Override
            public void onDialogKnowListener() {
                setState(!isLeft);
            }
        }).show();
    }

    public interface OnSelectClickListener {
        void selectLeftListener();

        void selectRightListener();
    }
}
