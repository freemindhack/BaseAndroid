package com.hkllzh.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hkllzh.android.C;
import com.hkllzh.android.util.log.LogHandler;
import com.hkllzh.android.util.log.LogInterface;
import com.hkllzh.android.util.sharedpreferences.SPUtil;

/**
 * {@link Fragment}的基类
 * <p/>
 * lizheng -- 2015/08/10
 * <p/>
 * BaseAndroid
 */
public class BaseFragment extends Fragment {

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log = _log;
        spUtil = _spUtil;
        W_PX = _W_PX;
        H_PX = _H_PX;
    }
}
