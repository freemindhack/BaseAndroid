package com.hkllzh.android;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.hkllzh.android.util.cache.ACache;
import com.hkllzh.android.util.log.LogHandler;
import com.hkllzh.android.util.log.LogInterface;
import com.hkllzh.android.util.sharedpreferences.SPUtil;
import com.hkllzh.android.util.toast.ToastUtil;

/**
 * 基类Application
 * <p>
 * lizheng -- 2015/08/10
 * <p>
 * BaseAndroid
 */
public abstract class BaseApplication extends Application {

    protected LogInterface log;

    @Override
    public void onCreate() {
        super.onCreate();

        AppConfig appConfig = getAppConfig();
        if (null != appConfig) {
            // 配置文件初始化
            SPUtil.init(this, appConfig.defaultSpFileName);
            // 输出日志初始化
            log = LogHandler.getInstance();
            LogHandler.LOG_PREFIX = appConfig.logPrefix;
            log.setLoggingEnabled(appConfig.isShowLog);
            log.setLoggingLevel(appConfig.showLogLevel);
        } else {
            // 配置文件初始化
            SPUtil.init(this, C.AppConfig.DEFAULT_SP_FILE_NAME);
            // 输出日志初始化
            log = LogHandler.getInstance();
            LogHandler.LOG_PREFIX = C.AppConfig.DEFAULT_LOG_PREFIX;
            log.setLoggingEnabled(C.AppConfig.DEFAULT_IS_SHOW);
            log.setLoggingLevel(C.AppConfig.DEFAULT_LOG_LEVEL);
        }

        // 初始化sp中的值
        initSpValue();
        // 缓存初始化
        ACache.init(this);
        // Toast初始化
        ToastUtil.init(this);
    }

    /**
     * 初始化sp中的值
     */
    private void initSpValue() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display dis = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        dis.getMetrics(outMetrics);
        SPUtil.getInstance().putInt(C.SP.SCREEN_WIDTH, outMetrics.widthPixels);
        SPUtil.getInstance().putInt(C.SP.SCREEN_HEIGHT, outMetrics.heightPixels);
        SPUtil.getInstance().putFloat(C.SP.SCREEN_DENSITY, outMetrics.density);
    }

    /**
     * 返回配置信息
     * <p>
     * <code>
     * sp_default_file_name - SharedPreferences的默认文件名字 - 空 - 默认名字为sp
     * </code>
     *
     * @return 配置信息
     */
    protected abstract AppConfig getAppConfig();

}
