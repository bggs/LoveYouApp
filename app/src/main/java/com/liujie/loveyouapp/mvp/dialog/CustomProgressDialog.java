package com.liujie.loveyouapp.mvp.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.liujie.loveyouapp.R;

/**
 * 自定义加载框
 */
public class CustomProgressDialog extends ProgressDialog {

    private boolean aBoolean;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme, boolean boo) {
        super(context, theme);
        this.aBoolean = boo;
    }

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        this.init(this.getContext());
    }

    private void init(Context context) {
        this.setCanceledOnTouchOutside(this.aBoolean);
        this.setContentView(R.layout.loading_layout_loading);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = -2;
        params.height = -2;
        this.getWindow().setAttributes(params);
    }

    public void show() {
        super.show();
    }

    public interface DialogKnowListener {
        void onDialogKnowListener();
    }
}
