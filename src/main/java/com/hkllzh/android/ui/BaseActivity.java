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
    private static final SPUtil _spUtil;
    private static final int _W_PX;
    private static final int _H_PX;

    static {
        _log = LogHandler.getInstance();
        _spUtil = SPUtil.getInstance();
        _W_PX = _spUtil.getInt(C.SP.SCREEN_WIDTH, 0);
        _H_PX = _spUtil.getInt(C.SP.SCREEN_HEIGHT, 0);
    }

    protected LogInterface log;
    protected SPUtil spUtil;
    protected int W_PX;
    protected int H_PX;
    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        log = _log;
        spUtil = _spUtil;
        W_PX = _W_PX;
        H_PX = _H_PX;
    }
}
