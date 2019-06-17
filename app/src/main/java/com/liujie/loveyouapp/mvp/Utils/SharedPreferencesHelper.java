package com.liujie.loveyouapp.mvp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * sp保存
 */
public class SharedPreferencesHelper {
    private static String name = "config";
    private static String userName = "myUser";

    /**
     * 获取SharedPreferences实例对象
     */
    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    //.............保存对象
    public static SharedPreferences getSharedPreferenceUser(Context context) {
        return context.getSharedPreferences(userName, Context.MODE_PRIVATE);
    }

    public static boolean putStringUser(Context context, String key, String value) {
        SharedPreferences sharedPreference = getSharedPreferenceUser(context);
        Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getStringUser(Context context, String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreferenceUser(context);
        return sharedPreference.getString(key, defValue);
    }

    public static boolean putBooleanUser(Context context, String key, Boolean value) {
        SharedPreferences sharedPreference = getSharedPreferenceUser(context);
        Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBooleanUser(Context context, String key,
                                         Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreferenceUser(context);
        return sharedPreference.getBoolean(key, defValue);
    }

    /**
     * 保存一个Boolean类型的值！
     */
    public static boolean putBoolean(Context context, String key, Boolean value) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 保存一个int类型的值！
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    /**
     * 保存一个float类型的值！
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 保存一个long类型的值！
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 保存一个String类型的值！
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 保存一个String类型的集合！
     */
    public static boolean putStrList(Context context, String key, List value) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        Gson gson = new Gson();
        String str = gson.toJson(value);
        editor.putString(key, str);
        return editor.commit();
    }

    /**
     * 序列化对象
     */
    public static String serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     */
    public static Object deSerialization(String str) throws IOException,
            ClassNotFoundException {
        if (str != null) {
            String redStr = java.net.URLDecoder.decode(str, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            Object person = (Object) objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return person;
        } else {
            return null;
        }
    }

    /**
     * 获取String的value
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getString(key, defValue);
    }

    /**
     * 获取int的value
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getInt(key, defValue);
    }

    /**
     * 获取float的value
     */
    public static float getFloat(Context context, String key, Float defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getFloat(key, defValue);
    }

    /**
     * 获取boolean的value
     */
    public static boolean getBoolean(Context context, String key,
                                     Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getBoolean(key, defValue);
    }

    /**
     * 获取long的value
     */
    public static long getLong(Context context, String key, long defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        return sharedPreference.getLong(key, defValue);
    }

    /**
     * 获取String类型的set集合
     */
    public static List<String> getStrList(Context context, String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        String listStr = sharedPreference.getString(key, defValue);
        Gson gson = new Gson();
        if (listStr != null) {
            List list = gson.fromJson(listStr, new TypeToken<List<String>>() {
            }.getType()); //将json字符串转换成List集合
            return list;
        } else {
            return null;
        }
    }

    /**
     * 删除Key对应的内容
     */
    public static boolean remove(Context context, String key) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清除SharedPreferences中的所有值！
     */
    public static boolean clear(Context context) {
        SharedPreferences sharedPreference = getSharedPreference(context);
        Editor editor = sharedPreference.edit();
        editor.clear();
        return editor.commit();
    }

    // ArrayList类型转成String类型
    public static String ArrayList2String(ArrayList<String> arrayList) {
        String result = "";
        if (arrayList != null && arrayList.size() > 0) {
            for (String item : arrayList) { // 把列表中的每条数据用逗号分割开来，然后拼接成字符串
                result += item + ",";
            } // 去掉最后一个逗号
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
