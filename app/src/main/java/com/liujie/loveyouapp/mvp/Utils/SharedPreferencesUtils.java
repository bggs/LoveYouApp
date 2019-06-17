package com.liujie.loveyouapp.mvp.Utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.liujie.loveyouapp.app.BaseApplication;

/**
 * 手势的保存
 */
public class SharedPreferencesUtils {

    private static SharedPreferencesUtils instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefer;

    public SharedPreferencesUtils() {
        this.prefer = PreferenceManager.getDefaultSharedPreferences(BaseApplication.context);
        this.editor = this.prefer.edit();
    }

    public static SharedPreferencesUtils getInstance() {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtils();
                }
            }
        }
        return instance;
    }

    public void saveString(String name, String data) {
        this.editor.putString(name, data);
        this.editor.commit();
    }

    public String getString(String name) {
        return this.prefer.getString(name, null);
    }
}
