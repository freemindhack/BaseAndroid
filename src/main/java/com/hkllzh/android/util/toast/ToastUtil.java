package com.hkllzh.android.util.toast;

import android.app.Application;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * {@link Toast}的一个简易显示工具类
 * <p/>
 * lizheng -- 2015/10/08
 */
public class ToastUtil {
    /**
     * 唯一的toast
     */
    private static Toast mToast = null;

    private static Application mApp;

    public static void init(Application application) {
        mApp = application;
    }

    /**
     * 使用默认的{@link Toast}显示信息
     *
     * @param text 要显示的信息
     */
    public static void show(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 使用默认的{@link Toast}显示信息
     *
     * @param textRid 要显示的信息
     */
    public static void show(@StringRes int textRid) {
        show(textRid, Toast.LENGTH_SHORT);
    }

    /**
     * 使用默认的{@link Toast}显示信息
     *
     * @param text     要显示的信息
     * @param duration 信息显示时间  {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public static void show(String text, int duration) {
        checkNotNull();
        if (null == mToast) {
            mToast = Toast.makeText(mApp, text, duration);
        }
        mToast.setText(text);
        mToast.show();
    }

    /**
     * 使用默认的{@link Toast}显示信息
     *
     * @param textRid  要显示的信息
     * @param duration 信息显示时间  {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public static void show(@StringRes int textRid, int duration) {
        checkNotNull();
        if (null == mToast) {
            mToast = Toast.makeText(mApp, textRid, duration);
        }
        mToast.setText(textRid);
        mToast.show();
    }

    private static void checkNotNull() {
        if (null == mApp) {
            throw new IllegalArgumentException("context为空，请为此类进行初始化");
        }
    }


}
