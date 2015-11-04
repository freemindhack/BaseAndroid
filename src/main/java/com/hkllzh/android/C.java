package com.hkllzh.android;

import com.hkllzh.android.util.log.LogInterface;

/**
 * 常量类
 * <p/>
 * lizheng -- 2015/09/29
 */
public final class C {
    private C() {
    }

    public static final class SP {
        // 屏幕宽（px）int
        public static final String SCREEN_WIDTH = "sp_screen_width";
        // 屏幕高（px）int
        public static final String SCREEN_HEIGHT = "sp_screen_height";
        // 屏幕密度 float
        public static final String SCREEN_DENSITY = "sp_screen_density";
    }

    public static final class AppConfig {
        public static String DEFAULT_SP_FILE_NAME = "sp";
        public static String DEFAULT_LOG_PREFIX = "";
        public static boolean DEFAULT_IS_SHOW = true;
        public static int DEFAULT_LOG_LEVEL = LogInterface.VERBOSE;
    }
}
