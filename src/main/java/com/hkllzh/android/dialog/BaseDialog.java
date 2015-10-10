package com.hkllzh.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.hkllzh.android.R;

/**
 * User: lizheng<br>
 * Email: li396858359@163.com<br>
 * 一个默认弹框的实现,使用是可以通过调整layout，style。更改为适合各自项目的弹框
 */
public abstract class BaseDialog extends Dialog {

    /**
     * 用一个默认样式文件
     *
     * @param context     C
     * @param layoutResId 布局文件
     */
    public BaseDialog(Context context, int layoutResId) {
        super(context, R.style.BaseDialog);
        init(layoutResId);
    }

    /**
     * @param context     C
     * @param layoutResId 布局文件
     * @param styleId     样式文件
     */
    public BaseDialog(Context context, int layoutResId, int styleId) {
        super(context, styleId);
        init(layoutResId);
    }

    private void init(int layoutResId) {
        setContentView(layoutResId);
        setProperty();
        initView();
        initData();
        setListener();
    }


    private void setProperty() {

        Window window = getWindow();
        WindowManager.LayoutParams p = window.getAttributes();

        Point point = getW_H();
        p.width = point.x;
        p.height = point.y;

        window.setAttributes(p);
    }

    /**
     * 控制整个dialog大小。默认为全屏展示此dialog<br/>
     * 如需修改复写此方法即可
     *
     * @return 要显示的宽高的值
     */
    protected Point getW_H() {
        Point point = new Point();
        Display d = getWindow().getWindowManager().getDefaultDisplay();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            point.x = d.getHeight();
            point.y = d.getWidth();
        } else {
            d.getSize(point);
        }
        return point;
    }


    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();
}

