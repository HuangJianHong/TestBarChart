package com.cncn.permiss;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cncn.www.testbarchart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T048 on 2016/9/7.
 * activity中申请权限工具类，andorid 6.0以上系统;
 */
public class PermissionUtil {
    public static final int REQUEST_CODE_REQUEST_PERMISSION = 1027;
    private static final String PACKAGE_URL_SCHEME = "package:";
    private static final int REQUEST_CODE_REQUEST_SETTING = 1028;

    public interface PermissionListener {
        void allGranted();                                          //申请的权限已全部被允许;
        void partiallyGranted(String... notGrantedPermissions);     //未被允许的权限;
    }
    public static PermissionTool getPermissionTool(PermissionListener listener) {
        return new PermissionTool(listener);
    }

    public static class PermissionTool {
        /**
         * 需要进行检测的权限数组
         */
        public String[] needPermissions = {       //SD卡权限，定位权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        private PermissionListener mListener;
        private String[] lacksPermissions;

        private PermissionTool(PermissionListener listener) {
            mListener = listener;
        }

        public void checkAndRequestPermission(Activity activity, String... permissions) {
            List<String> list = new ArrayList<>();
            for (String permission : permissions) {
                if (lacksPermission(activity, permission)) {
                    list.add(permission);
                }
            }
            if (list.size() > 0) {
                lacksPermissions = list.toArray(new String[list.size()]);
                ActivityCompat.requestPermissions(activity,
                        lacksPermissions,
                        REQUEST_CODE_REQUEST_PERMISSION);
            } else {
                mListener.allGranted();
            }
        }

        public void onRequestPermissionResult(final Activity activity,
                                              int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == REQUEST_CODE_REQUEST_PERMISSION) {
                boolean allGrant = true;
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        allGrant = false;
                        list.add(permissions[i]);
                        //Log.d("bin-->", "PermissionTool#onRequestPermissionResult(): permissionDie"+permissions[i]);
                    }
                }

                if (allGrant) {
                    mListener.allGranted();
                } else {
                    if (list.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        MaterialDialog dialog = new MaterialDialog.Builder(activity)  //被禁止,显示弹窗给用户;
                                .title(activity.getResources().getString(R.string.permission_deny))
                                .content(activity.getResources().getString(R.string.no_permission_for_sdcard))
                                .cancelable(false)
                                .negativeText(activity.getResources().getString(R.string.cancel))
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                        System.exit(0);   //强制退出应用;
                                        activity.finish();    //直接关闭掉首页;
                                    }
                                })
                                .positiveText(R.string.setting_title)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        startAppSettings(activity);
                                    }
                                }).show();
                    }else if(list.contains(Manifest.permission.ACCESS_FINE_LOCATION)){
                        MaterialDialog dialog = new MaterialDialog.Builder(activity)                  //被禁止,显示弹窗给用户;
                                .title(activity.getResources().getString(R.string.permission_deny))
                                .content(activity.getResources().getString(R.string.no_permission_for_location))
                                .negativeText(R.string.cancel)
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        mListener.partiallyGranted(list.toArray(new String[list.size()]));
                                    }
                                })
                                .positiveText(R.string.setting_title)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        startAppSettings(activity);
                                    }
                                }).show();
                    }
                }
            }
        }

        public void startAppSettings(Activity activity) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse(PACKAGE_URL_SCHEME + activity.getPackageName()));
            activity.startActivityForResult(intent, REQUEST_CODE_REQUEST_SETTING);
        }

        public void onActivityResult(Activity activity, int requestCode, int result) {
            List<String> list = new ArrayList<>();
            if (requestCode == REQUEST_CODE_REQUEST_SETTING) {
                for (int i = 0; i < lacksPermissions.length; i++) {
                    if (lacksPermission(activity, lacksPermissions[i])) {
                        list.add(lacksPermissions[i]);
                    }
                }
            }
            if (list.size() > 0) {
                mListener.partiallyGranted(list.toArray(new String[list.size()]));
            } else {
                mListener.allGranted();
            }
        }
    }


    private static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }

}
