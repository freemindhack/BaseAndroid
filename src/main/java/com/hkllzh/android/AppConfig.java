package com.hkllzh.android;

import com.hkllzh.android.util.log.LogInterface;

/**
 * 应用的配置
 * <p/>
 * lizheng -- 2015/09/29
 */
public class AppConfig {

    /**
     * {@link android.content.SharedPreferences}文件的名字
     * <br/>
     * 默认为sp
     */
    public String defaultSpFileName = "sp";
    /**
     * 自定义的日志输出是否打印
     * <br/>
     * 默认不打印
     */
    public boolean isShowLog = false;
    /**
     * 自定义的日志输出级别
     * <br/>
     * 默认所有的全部输出
     */
    public int showLogLevel = LogInterface.VERBOSE;

    /**
     * 日志tag的前缀
     * <br/>
     * 默认为空
     */
    public String logPrefix = "";

    private AppConfig() {
    }

    public static class Builder {
        AppConfig config;

        public Builder() {
            config = new AppConfig();
        }

        public Builder defaultSpFileName(String defaultSpFileName) {
            config.defaultSpFileName = defaultSpFileName;
            return this;
        }

        public Builder showLog(boolean showLog) {
            config.isShowLog = showLog;
            return this;
        }

        public Builder showLogLevel(int showLogLevel) {
            config.showLogLevel = showLogLevel;
            return this;
        }

        /**
         * 设置日志的tag前缀
         */
        public Builder setLogPrefix(String prefix) {
            config.logPrefix = prefix;
            return this;
        }

        public AppConfig builder() {
            return config;
        }
    }
}
