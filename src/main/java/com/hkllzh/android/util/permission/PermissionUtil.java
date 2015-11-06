package com.hkllzh.android.util.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.CheckResult;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.hkllzh.android.C;

/**
 * 权限检查、申请工具类
 * <p>
 * 检查结果会回调至入参{@link android.app.Activity}页面
 * lizheng -- 2015/11/05
 */
public class PermissionUtil {

    /**
     * 检查定位权限
     *
     * @return true 有、false 没有
     */
    @CheckResult
    public static boolean checkLocationPermission(Context context) {
        return checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    /**
     * 申请定位权限
     * <p>
     * 检查结果会回调至入参{@link android.app.Activity}页面的{@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * <br/>
     * 回调方法的requestCode为{@link com.hkllzh.weather.C.Permissions#LOCATION_REQUEST_CODE}
     *
     * @param act 调用页面
     */
    public static void requestLocationPermission(Activity act) {
        requestLocationPermission(act, C.Permissions.LOCATION_REQUEST_CODE);
    }

    /**
     * 申请定位权限
     * <p>
     * 检查结果会回调至入参{@link android.app.Activity}页面的{@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * <br/>
     * 回调方法的requestCode为{@code requestCode}参数
     *
     * @param act         调用页面
     * @param requestCode 请求码
     */
    public static void requestLocationPermission(Activity act, int requestCode) {
        requestPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION, requestCode);
    }

    /**
     * 检查外置存储权限
     *
     * @return true 有、false 没有
     */
    @CheckResult
    public static boolean checkExternalStoragePermission(Context context) {
        return checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 申请外置存储权限
     * <p>
     * 检查结果会回调至入参{@link android.app.Activity}页面的{@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * <br/>
     * 回调方法的requestCode为{@link com.hkllzh.weather.C.Permissions#STORAGE_REQUEST_CODE}
     *
     * @param act 调用页面
     */
    public static void requestExternalStoragePermission(Activity act) {
        requestExternalStoragePermission(act, C.Permissions.STORAGE_REQUEST_CODE);
    }

    /**
     * 申请外置存储权限
     * <p>
     * 检查结果会回调至入参{@link android.app.Activity}页面的{@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * <br/>
     * 回调方法的requestCode为{@code requestCode}参数
     *
     * @param act         调用页面
     * @param requestCode 请求码
     */
    public static void requestExternalStoragePermission(Activity act, int requestCode) {
        requestPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE, requestCode);
    }

    /**
     * 检查权限
     *
     * @param permission 要检查的权限
     * @return true 有、false 没有
     */
    @CheckResult
    private static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请权限
     * <p>
     * 检查结果会回调至入参{@link android.app.Activity}页面的{@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * <br/>
     * 回调方法的requestCode为{@code requestCode}参数
     *
     * @param activity    调用页面
     * @param requestCode 请求码
     */
    private static void requestPermission(Activity activity, String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }
}
