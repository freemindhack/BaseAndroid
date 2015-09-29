package com.hkllzh.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hkllzh.android.C;
import com.hkllzh.android.util.log.LogHandler;
import com.hkllzh.android.util.log.LogInterface;
import com.hkllzh.android.util.sharedpreferences.SPUtil;

/**
 * {@link android.app.Activity}的基类
 * <p/>
 * lizheng -- 2015/08/10
 * <p/>
 * BaseAndroid
 */
public class BaseActivity extends AppCompatActivity {

    private static final LogInterface _log;
    protected static final SPUtil spUtil;
    protected static final int W_PX;
    protected static final int H_PX;

    static {
        _log = LogHandler.getInstance();
        spUtil = SPUtil.getInstance();
        W_PX = spUtil.getInt(C.SP.SCREEN_WIDTH, 0);
        H_PX = spUtil.getInt(C.SP.SCREEN_HEIGHT, 0);
    }

    protected LogInterface log;

    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        log = _log;
    }
}
