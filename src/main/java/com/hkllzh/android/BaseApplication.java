package com.hkllzh.android;

import android.app.Application;

import com.hkllzh.android.util.cache.ACache;
import com.hkllzh.android.util.log.LogHandler;
import com.hkllzh.android.util.log.LogInterface;
import com.hkllzh.android.util.sharedpreferences.SPUtil;

/**
 * 基类Application
 * <p/>
 * lizheng -- 2015/08/10
 * <p/>
 * BaseAndroid
 */
public abstract class BaseApplication extends Application {

    protected LogInterface log;

    @Override
    public void onCreate() {
        super.onCreate();
        // 配置文件初始化
        SPUtil.init(this, getAppConfig().defaultSpFileName);
        // 缓存初始化
        ACache.init(this);
        // 输出日志初始化
        log = LogHandler.getInstance();
        log.setLoggingEnabled(getAppConfig().isShowLog);
        log.setLoggingLevel(getAppConfig().showLogLevel);
    }

    /**
     * 返回配置信息
     * <p/>
     * <code>
     * sp_default_file_name - SharedPreferences的默认文件名字 - 空 - 默认名字为sp
     * </code>
     *
     * @return 配置信息
     */
    protected abstract AppConfig getAppConfig();

}
