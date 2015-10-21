package com.hkllzh.android.util.log;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

public class LogHandler implements LogInterface {

    private static LogHandler logHandler = new LogHandler();

    private LogHandler() {
    }

    public static LogHandler getInstance() {
        return logHandler;
    }

    private boolean mLoggingEnabled = true;
    private int mLoggingLevel = VERBOSE;

    public static String LOG_PREFIX = "log_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    @Override
    public boolean isLoggingEnabled() {
        return mLoggingEnabled;
    }

    @Override
    public void setLoggingEnabled(boolean loggingEnabled) {
        this.mLoggingEnabled = loggingEnabled;
    }

    @Override
    public int getLoggingLevel() {
        return mLoggingLevel;
    }

    @Override
    public void setLoggingLevel(int loggingLevel) {
        this.mLoggingLevel = loggingLevel;
    }

    @Override
    public boolean shouldLog(int logLevel) {
        return logLevel >= mLoggingLevel;
    }

    public void log(int logLevel, String tag, String msg) {
        logWithThrowable(logLevel, tag, msg, null);
    }

    public void logWithThrowable(int logLevel, String tag, String msg, Throwable t) {
        if (isLoggingEnabled() && shouldLog(logLevel)) {
            switch (logLevel) {
                case VERBOSE:
                    Log.v(tag, msg, t);
                    break;
                case WARN:
                    Log.w(tag, msg, t);
                    break;
                case ERROR:
                    Log.e(tag, msg, t);
                    break;
                case DEBUG:
                    Log.d(tag, msg, t);
                    break;
                case WTF:
                    if (Build.VERSION.SDK_INT > 8) {
                        checkedWtf(tag, msg, t);
                    } else {
                        Log.e(tag, msg, t);
                    }
                    break;
                case INFO:
                    Log.i(tag, msg, t);
                    break;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private void checkedWtf(String tag, String msg, Throwable t) {
        Log.wtf(tag, msg, t);
    }

    @Override
    public void v(String tag, String msg) {
        log(VERBOSE, tag, msg);
    }

    @Override
    public void v(String tag, String msg, Throwable t) {
        logWithThrowable(VERBOSE, tag, msg, t);
    }

    @Override
    public void d(String tag, String msg) {
        log(DEBUG, tag, msg);
    }

    @Override
    public void d(String tag, String msg, Throwable t) {
        logWithThrowable(DEBUG, tag, msg, t);
    }

    @Override
    public void i(String tag, String msg) {
        log(INFO, tag, msg);
    }

    @Override
    public void i(String tag, String msg, Throwable t) {
        logWithThrowable(INFO, tag, msg, t);
    }

    @Override
    public void w(String tag, String msg) {
        log(WARN, tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable t) {
        logWithThrowable(WARN, tag, msg, t);
    }

    @Override
    public void e(String tag, String msg) {
        log(ERROR, tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable t) {
        logWithThrowable(ERROR, tag, msg, t);
    }

    @Override
    public void wtf(String tag, String msg) {
        log(WTF, tag, msg);
    }

    @Override
    public void wtf(String tag, String msg, Throwable t) {
        logWithThrowable(WTF, tag, msg, t);
    }
}