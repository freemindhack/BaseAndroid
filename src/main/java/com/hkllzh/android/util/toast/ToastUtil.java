package com.hkllzh.android.util.toast;

import android.app.Application;
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

    public static void show(String text) {
        if (null == mToast) {
            mToast = Toast.makeText(mApp, text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }

    public static void show(int textRid) {
        if (null == mToast) {
            mToast = Toast.makeText(mApp, textRid, Toast.LENGTH_SHORT);
        }
        mToast.setText(textRid);
        mToast.show();
    }
}
