package com.liujie.loveyouapp.app;

public interface BaseView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     *
     * @param message
     */
    void showMessage(String message);
}
