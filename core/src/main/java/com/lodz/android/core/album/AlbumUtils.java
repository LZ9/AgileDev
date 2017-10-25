package com.lodz.android.core.album;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.lodz.android.core.utils.ArrayUtils;

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
    public static List<String> getAllImages(Context context) {
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
        do {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            if (TextUtils.isEmpty(path)){
                continue;
            }

            File file = new File(path);
            if (file.exists() && file.length() > 0) {
                imageList.add(path);
            }

        }while(cursor.moveToNext());

        cursor.close();
        Collections.reverse(imageList);// 按时间降序
        return imageList;
    }

    /**
     * 获取所有图片的文件夹（包括所有图片文件夹）
     * @param context 上下文
     */
    public static List<ImageFolder> getAllImageFolders(Context context) {
        List<String> list = getAllImages(context);
        if (ArrayUtils.isEmpty(list)){
            return new LinkedList<>();
        }
        List<ImageFolder> folders = new LinkedList<>();
        folders.add(getAllImageFolder(context));
        folders.addAll(getImageFolders(list));
        return folders;
    }

    /**
     * 获取所有图片的文件夹（不包括所有图片文件夹）
     * @param pictures 所有图片数据
     */
    public static List<ImageFolder> getImageFolders(List<String> pictures) {
        List<ImageFolder> list = new LinkedList<>();
        if (ArrayUtils.isEmpty(pictures)){
            return list;
        }

        List<String> directoryList = new LinkedList<>();
        for (String path : pictures) {
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
     * 获取所有图片文件夹信息
     * @param context 上下文
     */
    public static ImageFolder getAllImageFolder(Context context) {
        List<String> list = getAllImages(context);

        ImageFolder imageFolder = new ImageFolder();
        imageFolder.setName("所有图片");
        imageFolder.setAllPicture(true);
        imageFolder.setCount(ArrayUtils.getSize(list));
        imageFolder.setFirstImagePath(ArrayUtils.isEmpty(list) ? "" : list.get(0));

        return imageFolder;
    }

    /**
     * 获取指定目录下的图片文件夹信息
     * @param file 目录
     * @param coverImgPath 封面图片路径
     */
    public static ImageFolder getImageFolder(File file, String coverImgPath) {
        String[] fileList = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return (filename != null && (filename.endsWith(".jpg")
                        || filename.endsWith(".gif")
                        || filename.endsWith(".png")
                        || filename.endsWith(".jpeg")));
            }
        });

        if (fileList == null || fileList.length == 0){
            return null;
        }

        ImageFolder imageFolder = new ImageFolder();
        imageFolder.setCount(fileList.length);
        imageFolder.setFirstImagePath(coverImgPath);
        imageFolder.setDir(file.getAbsolutePath());
        return imageFolder;
    }

    /**
     * 获取指定目录下的图片数据列表
     * @param context 上下文
     * @param imageFolder 文件夹目录
     */
    public static List<String> getImageListOfFolder(Context context, ImageFolder imageFolder) {
        if (imageFolder == null){
            return new ArrayList<>();
        }
        if (imageFolder.isAllPicture()){
            return getAllImages(context);
        }

        if (!imageFolder.isDirectory()){
            return new ArrayList<>();
        }

        File directoryFile = new File(imageFolder.getDir());
        File[] files = directoryFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return (filename.endsWith(".jpg")
                        || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"));
            }
        });

        List<String> imageList = new ArrayList<>();
        if (files == null || files.length == 0) {
            return imageList;
        }

        for (File file : files) {
            if (file.exists() && file.length() > 0){
                imageList.add(file.getAbsolutePath());
            }
        }

        return imageList;
    }

    /**
     * 通知刷新相册
     * @param context 上下文
     * @param imagePath 图片地址
     */
    public static void notifyScanImage(Context context, String imagePath) {
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(new File(imagePath));
//        intent.setData(uri);
//        context.getApplicationContext().sendBroadcast(intent);
        MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{imagePath}, new String[]{"image/jpeg"}, null);
    }
}
