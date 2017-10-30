package com.lodz.android.core.album;

import android.text.TextUtils;

/**
 * 图片文件夹实体
 * Created by zhouL on 2016/12/5.
 */
public class ImageFolder {

    /** 图片的文件夹路径 */
    private String dir;
    /** 文件夹内第一张图片的路径 */
    private String firstImagePath;
    /** 文件夹的名称 */
    private String name;
    /** 文件夹图片的数量 */
    private int count;
    /** 是否所有图片 */
    private boolean isAllPicture = false;

    void setDir(String dir) {
        this.dir = dir;
        if (dir.contains("/")) {
            int lastIndexOf = dir.lastIndexOf("/");
            this.name = dir.substring(lastIndexOf + 1 < dir.length() ? lastIndexOf + 1 : lastIndexOf);
        }
    }

    void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setAllPicture(boolean isAllPicture) {
        this.isAllPicture = isAllPicture;
    }

    public String getDir() {
        return dir;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }
    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public boolean isAllPicture() {
        return isAllPicture;
    }

    public boolean isDirectory() {
        return !TextUtils.isEmpty(dir);
    }
}