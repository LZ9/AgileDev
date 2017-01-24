package com.snxun.core.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.snxun.core.bean.ImageFolder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 系统相册工具类
 * Created by zhouL on 2016/12/5.
 */
public class AlbumUtils {

    /**
     * 获取相册中所有图片列表
     * @param context 上下文
     */
    public static List<String> getAllAlbumImages(Context context) {
        List<String> imageList = new LinkedList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=?";
        String[] selectionArgs = new String[]{"image/jpeg", "image/png", "image/gif"};
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED;

        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, sortOrder);
        if (cursor == null) {
            return imageList;
        }

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            if (TextUtils.isEmpty(path)){
                continue;
            }

            File file = new File(path);
            if (file.exists() && file.length() > 0) {
                imageList.add(path);
            }
        }

        cursor.close();
        Collections.reverse(imageList);
        return imageList;
    }


    /**
     * 获取相册文件夹列表
     * @param albumImages 所有图片文件列表
     */
    public static List<ImageFolder> getImageFolderList(List<String> albumImages) {
        List<ImageFolder> list = new LinkedList<>();
        ImageFolder totalImageFolder = getAllImageFolder(albumImages);
        list.add(totalImageFolder);
        if (albumImages == null || albumImages.isEmpty()) {
            return list;
        }

        List<String> directoryList = new LinkedList<>();
        for (String path : albumImages) {
            File file = new File(path);
            if (!file.exists()) {
                continue;
            }

            File parentFile = file.getParentFile();
            String parentPath = parentFile.getAbsolutePath();
            if (directoryList.contains(parentPath)) {
                continue;
            }

            directoryList.add(parentPath);
            ImageFolder imageFolder = getImageFolder(parentFile, path);
            if (imageFolder != null) {
                list.add(imageFolder);
            }
        }

        return list;
    }

    /**
     * 获取"所有图片"文件夹信息
     * @param albumImages 所有图片文件列表
     */
    public static ImageFolder getAllImageFolder(List<String> albumImages) {
        boolean isAlbumEmpty = (albumImages == null || albumImages.isEmpty());
        int count = isAlbumEmpty ? 0 : albumImages.size();
        String firstImagePath = isAlbumEmpty ? "" : albumImages.get(0);

        ImageFolder imageFolder = new ImageFolder();
        imageFolder.setDirectory(false);
        imageFolder.setCount(count);
        imageFolder.setFirstImagePath(firstImagePath);

        return imageFolder;
    }

    /**
     * 获取指定目录下的图片文件夹信息
     * @param file 目录
     * @param firstImagePath 第一张图片路径
     */
    public static ImageFolder getImageFolder(File file, String firstImagePath) {
        String[] fileList = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return (filename != null && (filename.endsWith(".jpg")
                        || filename.endsWith(".gif")
                        || filename.endsWith(".png")
                        || filename.endsWith(".jpeg")));
            }
        });
        int count = (fileList == null ? 0 : fileList.length);
        if (count == 0) {
            return null;
        }

        ImageFolder imageFolder = new ImageFolder();
        imageFolder.setCount(count);
        imageFolder.setFirstImagePath(firstImagePath);
        imageFolder.setDir(file.getAbsolutePath());

        return imageFolder;
    }

    /**
     * 获取指定目录下的图片文件列表
     * @param imageFolder 目录
     */
    public static List<String> getImageListOfFolder(ImageFolder imageFolder) {
        File directoryFile = new File(imageFolder.getDir());
        File[] files = directoryFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"));
            }
        });

        List<String> imageList = new ArrayList<>();
        if (files == null || files.length == 0) {
            return imageList;
        }

        for (File file : files) {
            imageList.add(file.getAbsolutePath());
        }

        return imageList;
    }
}
