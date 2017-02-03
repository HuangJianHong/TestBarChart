package com.cncn.config;

import android.content.Context;

import java.io.File;

/**
 * <请描述这个类是干什么的>
 *
 * @author lwli
 * @data: 2016/2/17 10:25
 * @version: V1.0
 */
public class AppDir {
    private static AppDir sInstance = null;
    public enum AppDirEnum {

        ROOT_DIR("TestBarChar"), IMAGE("ImgCache"), CACHE("cache"), VOLLEY("volley"),
        UPLOAD_IMAGE_TEMP("ImgUploadTemp"), DOWNLOAD("download"), CRASH_LOGS("Log"),MAP_CACHE("mapCache")
        ,HTML_FILE("m"),OFFLINE_CACEH("tmp"),APATCH("apatch");

        private String value;
        AppDirEnum(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    private AppDir() {
    }
    public static AppDir getInstance() {
        if (sInstance == null) {
            sInstance = new AppDir();
        }
        return sInstance;
    }
    public void initCacheDir(Context context) {
    }
    public static File getRootDir() {
        File file= JUtils.getOwnCacheDirectory(AppDirEnum.ROOT_DIR.getValue());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getDir(AppDirEnum dirEnum) {
        File file = new File(getRootDir(), dirEnum.getValue());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }
}
