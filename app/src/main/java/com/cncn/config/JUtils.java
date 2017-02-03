package com.cncn.config;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * <请描述这个类是干什么的>
 *
 * @author lwli
 * @data: 2016/3/19 17:04
 * @version: V1.0
 */
public class JUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    // 手机网络类型
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
    private static Context mApplicationContent;

    public static void initialize(Application app){
        mApplicationContent = app.getApplicationContext();
    }

    public static void Toast(String text){
        android.widget.Toast.makeText(mApplicationContent, text, android.widget.Toast.LENGTH_SHORT).show();
    }
    public static void Toast(int text){
        android.widget.Toast.makeText(mApplicationContent, text, android.widget.Toast.LENGTH_SHORT).show();
    }
    public static void ToastLong(String text){
        android.widget.Toast.makeText(mApplicationContent, text, android.widget.Toast.LENGTH_LONG).show();
    }
    public static void ToastLong(int text){
        android.widget.Toast.makeText(mApplicationContent, text, android.widget.Toast.LENGTH_LONG).show();
    }

    /**
     * dp转px
     *
     */
    public static int dip2px(float dpValue) {
        final float scale = mApplicationContent.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     *	px转dp
     */
    public static int px2dip(float pxValue) {
        final float scale = mApplicationContent.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        DisplayMetrics dm = mApplicationContent.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        DisplayMetrics dm = mApplicationContent.getResources().getDisplayMetrics();
        return dm.heightPixels-getStatusBarHeight();
    }

    /**
     * 取屏幕高度包含状态栏高度
     * @return
     */
    public static int getScreenHeightWithStatusBar(){
        DisplayMetrics dm = mApplicationContent.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 取导航栏高度
     * @return
     */
    public static int getNavigationBarHeight() {
        int result = 0;
        int resourceId = mApplicationContent.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mApplicationContent.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public static boolean checkDeviceHasNavigationBar() {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(mApplicationContent)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }
    public static void showSoftKeyboard(View view) {
        ((InputMethodManager) mApplicationContent.getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }
    /**
     * Get dir on disk for cache.
     * Check if media is mounted or storage is built-in, if so, try and use external cache dir
     * otherwise use internal cache dir
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        if (context == null) {
            throw new IllegalArgumentException("Context is required.");
        }

        String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() :
                context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }
    public static File getOwnCacheDirectory(String cacheDir) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(mApplicationContent)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = mApplicationContent.getCacheDir();
        }
        return appCacheDir;
    }
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
    /**
     * If SDK API > 8. Android 2.2
     *
     * @return
     */
    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }
    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
        if (hasFroyo()) {
            File file = null;
            if (context == null) {

            } else {
                file = context.getExternalCacheDir();
            }
            return file;
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     * otherwise.
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }
    /**
     * If SDK API > 9. Android 2.3
     *
     * @return
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }
    /**
     * 取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = mApplicationContent.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mApplicationContent.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getActionBarHeight() {
        int actionBarHeight = 0;

        final TypedValue tv = new TypedValue();
        if (mApplicationContent.getTheme()
                .resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data, mApplicationContent.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }
    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) mApplicationContent
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * 关闭输入法
     * @param act
     */
    public static void closeInputMethod(Activity act){
        View view = act.getCurrentFocus();
        if(view!=null){
            ((InputMethodManager)mApplicationContent.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public static void hideSoftKeyboard(View view) {
        if (view == null) return;
        ((InputMethodManager) mApplicationContent.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    /**
     * 判断应用是否处于后台状态
     * @return
     */
    public static boolean isBackground() {
        ActivityManager am = (ActivityManager) mApplicationContent.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mApplicationContent.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制文本到剪贴板
     * @param text
     */
    public static void copyToClipboard(String text){
        ClipboardManager cbm = (ClipboardManager) mApplicationContent.getSystemService(Activity.CLIPBOARD_SERVICE);
        cbm.setPrimaryClip(ClipData.newPlainText(mApplicationContent.getPackageName(), text));
    }
    public static void copyTextToBoard(String string) {
        if (TextUtils.isEmpty(string))
            return;
        ClipboardManager clip = (ClipboardManager) mApplicationContent
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(string);
    }

    /**
     * 获取SharedPreferences
     * @return SharedPreferences
     */
    public static SharedPreferences getSharedPreference() {
        return mApplicationContent.getSharedPreferences(mApplicationContent.getPackageName(), Activity.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferences
     * @return SharedPreferences
     */
    public static SharedPreferences getSharedPreference(String name) {
        return mApplicationContent.getSharedPreferences(name, Activity.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferences
     * @return SharedPreferences
     */
    public static SharedPreferences getSharedPreference(String name,int mode) {
        return mApplicationContent.getSharedPreferences(name, mode);
    }

    /**
     * 经纬度测距
     * @param jingdu1
     * @param weidu1
     * @param jingdu2
     * @param weidu2
     * @return
     */
    public static double distance(double jingdu1, double weidu1, double jingdu2,   double weidu2) {
        double a, b, R;
        R = 6378137; // 地球半径
        weidu1 = weidu1 * Math.PI / 180.0;
        weidu2 = weidu2 * Math.PI / 180.0;
        a = weidu1 - weidu2;
        b = (jingdu1 - jingdu2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(weidu1)
                * Math.cos(weidu2) * sb2 * sb2));
        return d;
    }

    /**
     * 是否有网络
     * @return
     */
    public static boolean isNetWorkAvilable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mApplicationContent
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 取APP版本号
     * @return
     */
    public static int getAppVersionCode(){
        try {
            PackageManager mPackageManager = mApplicationContent.getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(mApplicationContent.getPackageName(),0);
            return _info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 取APP版本名
     * @return
     */
    public static String getAppVersionName(){
        try {
            PackageManager mPackageManager = mApplicationContent.getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(mApplicationContent.getPackageName(),0);
            return _info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
    /**
     * 检查手机上是否安装了指定的软件
     * @param packageName：应用包名
     * @return
     */
    public static  boolean isAvilible(String packageName){
        //获取packagemanager
        final PackageManager packageManager = mApplicationContent.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * @param context
     * @param gd_lon
     * @param gd_lat
     * @param title
     * @param address
     */
    public static void gotoMap(Context context,double gd_lon, double gd_lat,String title,String address){
        if(JUtils.isAvilible("com.autonavi.minimap")){
            JUtils.openGaoDeMap(context,gd_lon,gd_lat,title,address);
        }else if(JUtils.isAvilible("com.baidu.BaiduMap")){
            double[]lonlat=gaoDeToBaidu(gd_lon,gd_lat);
            JUtils.openBaiduMap(context,lonlat[0],lonlat[1],title,address);
        }else{
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }
    public static Bitmap BitmapZoom(Bitmap b, float x, float y)
    {
        int w=b.getWidth();
        int h=b.getHeight();
        float sx=x/w;
        float sy=y/h;
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w,
                h, matrix, true);
        return resizeBmp;
    }


    public static String MD5(byte[] data) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(data);
        byte[] m = md5.digest();//加密
        return Base64.encodeToString(m, Base64.DEFAULT);
    }

    public static String getStringFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(mApplicationContent.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Uri getUriFromRes(int id){
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + mApplicationContent.getResources().getResourcePackageName(id) + "/"
                + mApplicationContent.getResources().getResourceTypeName(id) + "/"
                + mApplicationContent.getResources().getResourceEntryName(id));
    }

    /**
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }
    public static void openBaiduMap(Context context,double lon, double lat, String title, String describle) {
        try {
            StringBuilder loc = new StringBuilder();
            loc.append("intent://map/direction?origin=latlng:");
            loc.append(lat);
            loc.append(",");
            loc.append(lon);
            loc.append("|name:");
            loc.append("我的位置");
            loc.append("&destination=latlng:");
            loc.append(lat);
            loc.append(",");
            loc.append(lon);
            loc.append("|name:");
            loc.append(describle);
            loc.append("&mode=driving");
            loc.append("&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            Intent intent = Intent.getIntent(loc.toString());
            context.startActivity(intent); //启动调用
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**打开高德地图
     * @param context
     * @param lon
     * @param lat
     * @param title
     * @param describle
     */
    public static void openGaoDeMap(Context context,double lon, double lat, String title, String describle) {
//        try {
            StringBuilder loc = new StringBuilder();
//            loc.append("intent://map/direction?origin=latlng:")
//                    .append(lat)
//                    .append(",")
//                    .append(lon)
//                    .append("|name:")
//                    .append(title)
//                    .append("&destination=")
//                    .append(describle)
//                    .append("mode=driving");
//            Location location=LocationModel.getInstance().getCurLocation();
//            loc.append("androidamap://route?sourceApplication=softname");
//            loc.append("&poiname=");
//            loc.append(describle);
//            loc.append("&dlat=");
//            loc.append(lat);
//            loc.append("&dlon=");
//            loc.append(lon);
//            loc.append("&slat=");
//            loc.append(location.getLatitude());
//            loc.append("&slon=");
//            loc.append(location.getLongitude());
//            loc.append("&dev=0");
//            loc.append("&t=2");
//            loc.append("&m=2");
//            loc.append("&dname=").append(title);
//            loc.append("&pkg=com.autonavi.minimap");
//            Intent intent = Intent.getIntent(loc.toString());
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    /**
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /***********************2.4版本 笨游 攻略详情页 从一个景点到另外一个景点的驾车路线规划 **********************************************/
    /**
     * 上一个景点到下一个景点的路线规划；跳转 高德地图 或 百度地图
     * @param context
     * @param start_lon
     * @param start_lat
     * @param start_title
     * @param gd_lon
     * @param gd_lat
     * @param title
     * @param address
     */
    public static void gotoMapRoutePlanning(Context context,double start_lon, double start_lat,String start_title, double gd_lon, double gd_lat,String title,String address){
        if(JUtils.isAvilible("com.autonavi.minimap")){
            JUtils.openGaoDeMap(context, start_lon, start_lat, start_title,gd_lon, gd_lat, title, address);
//            JUtils.openGaoDeMap(context,gd_lon,gd_lat,title,address);
        }else if(JUtils.isAvilible("com.baidu.BaiduMap")){
            double[]lonlat_start=gaoDeToBaidu(start_lon,start_lat);
            double[]lonlat=gaoDeToBaidu(gd_lon,gd_lat);
            JUtils.openBaiduMap(context,lonlat_start[0], lonlat_start[1], start_title,lonlat[0],lonlat[1],title,address);
//            JUtils.openBaiduMap(context,lonlat[0],lonlat[1],title,address);
        }else{
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    //高德地图路线规划， 从起点到终点，搜索驾车路线 URI　API;
    public static void openGaoDeMap(Context context,double start_lon, double start_lat,String start_title,double lon, double lat, String title, String describle) {
        try {
            StringBuilder loc = new StringBuilder();
            loc.append("androidamap://route?sourceApplication=softname");
            loc.append("&slat=" + start_lat);
            loc.append("&slon=" + start_lon);
            loc.append("&sname=" + start_title);

            loc.append("&dlat=" + lat);
            loc.append("&dlon=" + lon);
            loc.append("&dname=" + title);

            loc.append("&dev=0");
            loc.append("&m=2");
            loc.append("&t=2");
            loc.append("&pkg=com.autonavi.minimap");
            Intent intent = Intent.getIntent(loc.toString());
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //移动APP调起Android百度地图方式 URI API;
    public static void openBaiduMap(Context context,double start_lon, double start_lat, String start_title, double lon, double lat, String title, String describle) {
        try {
            StringBuilder loc = new StringBuilder();
            loc.append("intent://map/direction?origin=latlng:");
            loc.append(start_lat);
            loc.append(",");
            loc.append(start_lon);
            loc.append("|name:");
            loc.append(start_title);

            loc.append("&destination=latlng:");
            loc.append(lat);
            loc.append(",");
            loc.append(lon);
            loc.append("|name:");
            loc.append(title);

            loc.append("&mode=driving");
            loc.append("&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            Intent intent = Intent.getIntent(loc.toString());
            context.startActivity(intent); //启动调用
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String intConvert2String(int value){
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(value);
    }

    /**
     * 将数值按照 通常的金额显示方式，每个三位有个逗号； 如  32,222,324;
     * @param
     * @return
     */
    public static String numConcertMoney(double data) {
        DecimalFormat df  = new DecimalFormat("#,###");
        String format = df.format(data);
        return format;
    }
    /*
   * Date格式化输出string
   * @param Demo
   * @return
   */
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    public static Date dateFromString(String date) {
//        if (TextUtils.isEmpty(date)|| !DateUtils.isDateFormat(date)) {
//            return new Date(System.currentTimeMillis());
//        } else {
//            try {
//                mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//                return mSimpleDateFormat.parse(date);
//            } catch (ParseException e) {
//                return new Date(System.currentTimeMillis());
//            }
//        }
        return  null;
    }

    /**
     * 动态修改： TextView 控件的 drawable图片
     * @param tvName
     * @param drawableRes
     * @param where       1 left ,2 top ,3 right, 4bottom
     */
    public static void setTextDrawable(TextView tvName ,int drawableRes, int where) {
        if (drawableRes == 0) {
            return;
        }
        Drawable drawableTop = mApplicationContent.getResources().getDrawable(drawableRes);
        // 必须设置图片大小，否则不显示
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
                drawableTop.getMinimumHeight());
        switch (where) {
            case 1:     //left
                tvName.setCompoundDrawables(drawableTop, null, null, null);
                break;
            case 2:     //top
                tvName.setCompoundDrawables(null, drawableTop, null, null);
                break;
            case 3:      //right;
                tvName.setCompoundDrawables(null, null, drawableTop, null);
                break;
            case 4:      //bottom
                tvName.setCompoundDrawables(null, null, null, drawableTop);
                break;
            default:
                tvName.setCompoundDrawables(null, null, null, null);
                break;
        }
    }

    /**
     *  同一个文本，不同字号的显示；
     * @param str 文本
     * @param tv  控件
     * @param dp  需要改变的字号大小 ：px ； 改变位置为 从第一个字符，到倒数第二个字符；
     */
    public static void setTextSize (String str , TextView tv, int dp) {
        SpannableString spannableString = new SpannableString(str);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dp, true);
        spannableString.setSpan(absoluteSizeSpan,0,str.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannableString);
    }
}
