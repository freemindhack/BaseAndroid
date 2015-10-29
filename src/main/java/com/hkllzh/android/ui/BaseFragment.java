package com.hkllzh.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkllzh.android.C;
import com.hkllzh.android.util.log.LogHandler;
import com.hkllzh.android.util.log.LogInterface;
import com.hkllzh.android.util.sharedpreferences.SPUtil;

/**
 * {@link Fragment}的基类
 * <p>
 * lizheng -- 2015/08/10
 * <p>
 * BaseAndroid
 */
public abstract class BaseFragment extends Fragment {

    protected static final LogInterface log;
    protected static final SPUtil spUtil;
    protected static final int W_PX;
    protected static final int H_PX;

    static {
        log = LogHandler.getInstance();
        spUtil = SPUtil.getInstance();
        W_PX = spUtil.getInt(C.SP.SCREEN_WIDTH, 0);
        H_PX = spUtil.getInt(C.SP.SCREEN_HEIGHT, 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentViewId(), container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadIntentData();
        initView();
    }


    protected void loadIntentData() {
    }

    protected abstract int getContentViewId();

    protected abstract void initView();

    protected String makeLogTag(Class cls) {
        return LogHandler.makeLogTag(cls);
    }

    protected String makeLogTag(String str) {
        return LogHandler.makeLogTag(str);
    }
}
