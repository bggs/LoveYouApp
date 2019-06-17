package com.liujie.loveyouapp.mvp.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liujie.loveyouapp.R;


/**
 * 自定义Dialog
 */
public class CustomDialog extends AlertDialog {

    public static final int MODEL_ONE_BUTTON = 1;
    public static final int MODEL_TWO_BUTTON = 2;
    public static final int MODEL_ONE_LINE = 3;
    public static final int MODEL_TWO_LINE = 4;
    public static final int MODEL_ONE_LINE_CENTER = 5;

    private Context context;
    private TextView mMessageView;
    private TextView mTitleView;
    private TextView mOneLineMessageView;
    private TextView mOneLineCenterMessageView;
    private TextView mKnowView;
    private TextView mCancelView;
    private TextView mOkView;
    private LinearLayout mTwoButtonView;

    private DialogListener mDialogOkListener;
    private DialogKnowListener mDialogKnowListener;

    private String title;
    private String message;
    private int buttonType;
    private int messageType;
    private String leftButtonText;
    private String rightButtonText;


    /**
     * @param context             上下文对象
     * @param title               对话框有两行文字时，标题
     * @param message             对话框有两行文字时，提示的内容
     * @param leftButtonText      对话框有两个Button时，左面Button的文字，有一个Button时的文字
     * @param rightButtonText     有两个文字时右侧的Button的文字，有一个Button时，写null
     * @param buttonType          对话框有几个Button
     * @param messageType         对话框有几行文字
     * @param mDialogKnowListener 对话框只有一个Button时的监听事件
     * @param mDialogOkListener   对话框有两个Button时的点击事件
     */
    public CustomDialog(Context context, String title, String message, String leftButtonText, String rightButtonText, int buttonType, int messageType, DialogKnowListener mDialogKnowListener, DialogListener mDialogOkListener) {
        super(context, R.style.CircularDialog);
        this.context = context;
        this.title = title;
        this.message = message;
        this.mDialogKnowListener = mDialogKnowListener;
        this.mDialogOkListener = mDialogOkListener;
        this.buttonType = buttonType;
        this.messageType = messageType;
        this.leftButtonText = leftButtonText;
        this.rightButtonText = rightButtonText;
    }

    /**
     * @param context           上下文对象
     * @param title             对话框有两行文字时，标题
     * @param message           对话框有两行文字时，提示的内容
     * @param leftButtonText    对话框有两个Button时，左面Button的文字，有一个Button时的文字
     * @param rightButtonText   有两个文字时右侧的Button的文字，有一个Button时，写null
     * @param mDialogOkListener 对话框有两个Button时，右侧的Button的点击事件
     */
    public CustomDialog(Context context, String title, String message, String leftButtonText, String rightButtonText, DialogListener mDialogOkListener) {
        super(context, R.style.CircularDialog);
        this.context = context;
        this.title = title;
        this.message = message;
        this.mDialogOkListener = mDialogOkListener;
        this.leftButtonText = leftButtonText;
        this.rightButtonText = rightButtonText;


        this.buttonType = MODEL_TWO_BUTTON;
        this.messageType = MODEL_ONE_LINE_CENTER;
    }

    /**
     * @param context            上下文对象
     * @param title              对话框有两行文字时，标题
     * @param message            对话框有两行文字时，提示的内容
     * @param leftButtonText     对话框有两个Button时，左面Button的文字，有一个Button时的文字
     * @param dialogKnowListener 对话框有一个Button时，右侧的Button的点击事件
     */
    public CustomDialog(Context context, String title, String message, String leftButtonText, DialogKnowListener dialogKnowListener) {
        super(context, R.style.CircularDialog);
        this.context = context;
        this.title = title;
        this.message = message;
        this.buttonType = MODEL_ONE_BUTTON;
        this.mDialogKnowListener = dialogKnowListener;
        this.leftButtonText = leftButtonText;
    }

    public void setOneLineCenterModelStyle(Context context, int resId, int textSize) {
        mCancelView.setTextColor(context.getResources().getColor(resId));
        mOkView.setTextColor(context.getResources().getColor(resId));
        mKnowView.setTextColor(context.getResources().getColor(resId));
        mCancelView.setTextSize(textSize);
        mKnowView.setTextSize(textSize);
        mOkView.setTextSize(textSize);
        mCancelView.setTypeface(Typeface.DEFAULT);
        mOkView.setTypeface(Typeface.DEFAULT);
        mOneLineCenterMessageView.setPadding(0, 96, 0, 56);
    }

    public void setKnowTextColor(int resId) {
        mKnowView.setTextColor(resId);
    }

    public void setKnowText(String knowText) {
        mKnowView.setText(knowText);
    }


    private void init() {
        mTitleView = (TextView) findViewById(R.id.dialog_tip_title);
        mMessageView = (TextView) findViewById(R.id.dialog_tip_message);
        mOneLineMessageView = (TextView) findViewById(R.id.dialog_tip_oneline_message);
        mOneLineCenterMessageView = (TextView) findViewById(R.id.dialog_tip_oneline_center_message);
        mKnowView = (TextView) findViewById(R.id.dialog_tip_know);
        mCancelView = (TextView) findViewById(R.id.dialog_tip_cancel);
        mOkView = (TextView) findViewById(R.id.dialog_tip_ok);
        mTwoButtonView = (LinearLayout) findViewById(R.id.dialog_two_button);
    }

    private void initData() {
        if (buttonType == MODEL_ONE_BUTTON) {
            mTwoButtonView.setVisibility(View.GONE);
            mKnowView.setVisibility(View.VISIBLE);
            mKnowView.setText(leftButtonText);
        } else if (buttonType == MODEL_TWO_BUTTON) {
            mKnowView.setVisibility(View.GONE);
            mTwoButtonView.setVisibility(View.VISIBLE);
            mOkView.setText(rightButtonText);
            mCancelView.setText(leftButtonText);
        }

        if (messageType == MODEL_ONE_LINE) {
            mMessageView.setVisibility(View.GONE);
            mTitleView.setVisibility(View.GONE);
            mOneLineMessageView.setVisibility(View.VISIBLE);
            mOneLineMessageView.setText(message);
        } else if (messageType == MODEL_TWO_LINE) {
            mMessageView.setVisibility(View.VISIBLE);
            mTitleView.setVisibility(View.VISIBLE);
            mOneLineMessageView.setVisibility(View.GONE);
            mTitleView.setText(title);
            mMessageView.setText(message);
        } else if (messageType == MODEL_ONE_LINE_CENTER) {
            mMessageView.setVisibility(View.GONE);
            mTitleView.setVisibility(View.GONE);
            mOneLineMessageView.setVisibility(View.GONE);
            mOneLineCenterMessageView.setVisibility(View.VISIBLE);
            mOneLineCenterMessageView.setText(message);
        }
    }

    protected CustomDialog(@NonNull Context context) {
        super(context);
        init();
    }

    protected CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);
        init();
        initData();
        initListener();
        setCancelable(false);
    }

    private void initListener() {
        mKnowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mDialogKnowListener != null) {
                    mDialogKnowListener.onDialogKnowListener();
                }
            }
        });

        mCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mDialogOkListener != null) {
                    mDialogOkListener.onDialogCancelListener();
                }
            }
        });

        mOkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mDialogOkListener != null) {
                    mDialogOkListener.onDialogOkListener();
                }
            }
        });
    }


    public interface DialogListener {
        void onDialogOkListener();

        void onDialogCancelListener();
    }

    public interface DialogKnowListener {
        void onDialogKnowListener();
    }
}
