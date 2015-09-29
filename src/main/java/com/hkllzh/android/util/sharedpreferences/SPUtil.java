package com.hkllzh.android.util.sharedpreferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * {@link android.content.SharedPreferences}的操作类
 * <p/>
 * lizheng -- 15/1/15
 * <p/>
 * FastWeiB
 */
public class SPUtil {
    private static Context mContext;
    private static String mFileName;
    private SharedPreferences saveInfo;
    private SharedPreferences.Editor saveEditor;


    private SPUtil() {
        if (null == mContext) {
            throw new NullPointerException("此类没有进行初始化");
        }
        if (TextUtils.isEmpty(mFileName)) {
            throw new NullPointerException("此类没有进行初始化");
        }
        saveInfo = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        saveEditor = saveInfo.edit();
    }

    private static SPUtil spUtil;

    public static void init(Application app, String fileName) {
        mContext = app;
        mFileName = fileName;
    }

    public static SPUtil getInstance() {
        if (null == spUtil) {
            spUtil = new SPUtil();
        }
        return spUtil;
    }

    /**
     * 删除全部数据
     *
     * @return
     */
    public boolean clearAllItem() {
        saveEditor.clear();
        return saveEditor.commit();
    }

    // --------- string ----------
    public boolean putString(String key, String value) {
        saveEditor.putString(key, value);
        return saveEditor.commit();
    }

    public String getString(String key, String defaultValue) {
        return saveInfo.getString(key, defaultValue);
    }

    // --------- float ----------
    public boolean putFloat(String key, Float value) {
        saveEditor.putFloat(key, value);
        return saveEditor.commit();
    }

    public Float getFloat(String key, Float defaultValue) {
        return saveInfo.getFloat(key, defaultValue);
    }

    // --------- boolean ----------
    public boolean putBoolean(String key, boolean value) {
        saveEditor.putBoolean(key, value);
        return saveEditor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return saveInfo.getBoolean(key, defaultValue);
    }

    // ---------- int ---------
    public boolean putInt(String key, int value) {
        saveEditor.putInt(key, value);
        return saveEditor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return saveInfo.getInt(key, defaultValue);
    }

    // ---------- long ----------
    public boolean putLong(String key, long value) {
        saveEditor.putLong(key, value);
        return saveEditor.commit();
    }

    public long getLong(String key, long defaultValue) {
        return saveInfo.getLong(key, defaultValue);
    }
}
