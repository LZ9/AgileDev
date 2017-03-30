package com.lodz.android.core.album;

/**
 * 图片文件夹实体
 * Created by zhouL on 2016/12/5.
 */
public class ImageFolder {
    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片的数量
     */
    private int count;

    /**
     * 是否文件夹，或者是"所有图片"
     */
    private boolean isDirectory = true;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        if (dir.contains("/")) {
            int lastIndexOf = dir.lastIndexOf("/");
            this.name = dir.substring(lastIndexOf);
        }
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }
}