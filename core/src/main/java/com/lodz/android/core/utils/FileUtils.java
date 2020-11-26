package com.lodz.android.core.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * 文件操作帮助类
 * Created by zhouL on 2016/12/6.
 */
public class FileUtils {

    /**
     * 设置当前路径拥有最高权限
     * @param filePath 文件路径
     */
    public static boolean setFileRoot(@NonNull String filePath){
        try {
            String permission="777";
            String command = "chmod " + permission + " " + filePath;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件
     * @param filePath 文件路径
     */
    public static File createFile(@NonNull String filePath) {
        try {
            return new File(filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 该路径下的文件是否存在
     * @param filePath 文件路径
     */
    public static boolean isFileExists(@NonNull String filePath) {
        return isFileExists(createFile(filePath));
    }

    /**
     * 判断文件是否存在
     * @param file 文件
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 重命名文件
     * @param filePath 文件路径
     * @param newName 新名称
     */
    public static boolean renameFile(@NonNull String filePath, @NonNull String newName) {
        return renameFile(createFile(filePath), newName);
    }

    /**
     * 重命名文件
     * @param file 文件
     * @param newName 新名称
     */
    public static boolean renameFile(File file, @NonNull String newName) {
        newName = newName.replaceAll(" ", "");//去掉新名称中的空格
        if (!isFileExists(file) || TextUtils.isEmpty(newName)){//旧文件不存在或者新名称为空
            return false;
        }
        if (newName.equals(file.getName())) {// 新名称与旧名称一致
            return true;
        }

        File newFile = createFile(file.getParent() + File.separator + newName);
        return file.renameTo(newFile);
    }

    /**
     * 删除指定路径下以.xxx结尾的文件
     * @param filePath 文件路径
     * @param suffix 后缀名(例如apk、png等)
     */
    public static void deleteFileWithSuffix(@NonNull String filePath, @NonNull String suffix) {
        File file = createFile(filePath);
        if (!isFileExists(file)){// 文件不存在
            return;
        }
        File[] files = file.listFiles();
        if (files == null || files.length == 0){// 该路径下没有其他文件
            return;
        }
        for (File childFile : files) {
            if (childFile.getName().toLowerCase().endsWith("."+suffix.toLowerCase())){
                //noinspection ResultOfMethodCallIgnored
                childFile.delete();
            }
        }
    }

    /**
     * 获取文件的后缀
     * @param fileName 文件名
     */
    public static String getSuffix(@NonNull String fileName) {
        String subffix = "";
        int startCharindex = fileName.lastIndexOf('.');
        if (startCharindex != -1) {//存在后缀
            subffix = fileName.substring(startCharindex);
        }
        return subffix;
    }

    /**
     * 创建文件夹
     * @param filePath 文件夹路径
     */
    public static boolean createFolder(@NonNull String filePath){
        File folder = createFile(filePath);
        return folder != null && !folder.exists() && folder.mkdirs();
    }

    /**
     * 创建一个NewFile
     * @param filePath 文件路径
     */
    public static boolean createNewFile(@NonNull String filePath){
        try {
            File file = createFile(filePath);
            return file != null && !file.exists() && file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移动指定文件到指定的路径
     * @param fromPath 当前路径
     * @param toPath 目的路径
     */
    public static boolean moveFile(@NonNull String fromPath, @NonNull String toPath) {
        File fromFile = createFile(fromPath);
        if (!isFileExists(fromFile)){
            return false;
        }
        File toFile = createFile(toPath);
        return toFile != null && !toFile.exists() && fromFile.renameTo(toFile);
    }

    /**
     * 复制文件到指定路径
     * @param fromPath 源文件路径
     * @param toPath 指定复制路径
     */
    public static boolean copyFile(@NonNull String fromPath, @NonNull String toPath) {
        File fromFile = createFile(fromPath);
        File toFile = createFile(toPath);
        if (fromFile == null || toFile == null){
            return false;
        }

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            inStream = new FileInputStream(fromFile);
            outStream = new FileOutputStream(toFile);
            inChannel = inStream.getChannel();
            outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);

            inStream.close();
            outStream.close();
            inChannel.close();
            outChannel.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            try {
                if (inStream != null){
                    inStream.close();
                }
                if (outStream != null){
                    outStream.close();
                }
                if (inChannel != null){
                    inChannel.close();
                }
                if (outChannel != null){
                    outChannel.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 获取该路径下的文件总长度（返回结果携带单位）
     * @param filePath 文件路径
     */
    public static String getFileTotalLengthUnit(String filePath) {
        return formetFileSize(getFileTotalLength(filePath));
    }

    /**
     * 获取该路径下的文件总长度
     * @param filePath 文件路径
     */
    public static long getFileTotalLength(String filePath) {
        File file = new File(filePath);
        long total = 0;
        try {
            if (file.isDirectory()) {
                total = getFileSizes(file);
            } else {
                total = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 获取指定文件大小
     * @param file 文件
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            return file.length();
        }
        return size;
    }

    /**
     * 计算文件夹大小
     * @param file 文件夹文件
     */
    private static long getFileSizes(File file) throws Exception {
        long size = 0;
        File files[] = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                size = size + getFileSizes(f);
            } else {
                size = size + getFileSize(f);
            }
        }

        return size;
    }

    /**
     * 转换文件大小（单位B、KB、MB、GB、TB）
     * @param fileSize 文件大小
     */
    public static String formetFileSize(long fileSize) {
        if (fileSize <= 0) {
            return "0KB";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(fileSize) / Math.log10(1024));
        return new DecimalFormat("##0.0").format(fileSize / Math.pow(1024, digitGroups)) + units[digitGroups];
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void delFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File item : files) {
                    if (item.isFile()) {
                        item.delete();
                    } else if (item.isDirectory()) {
                        delFile(item.getAbsolutePath());
                    }
                }
                file.delete();
            } else if (file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件路径转byte数组
     * @param filePath 文件路径
     */
    public static byte[] fileToByte(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;

        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                baos.write(b, 0, n);
            }
            buffer = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (baos != null){
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return buffer;
    }

    /**
     * byte数组保存为文件
     * @param bytes byte数组
     * @param filePath 文件路径
     * @param fileName 文件名字
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void byteToFile(byte[] bytes, String filePath, String fileName) {
        if (bytes == null || bytes.length == 0 || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)) {
            return;
        }
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            File file = new File(filePath + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将bitmap保存为图片文件
     * @param bitmap 图片
     * @param filePath 目录
     * @param fileName 名称（可以不需要后缀）
     * @param quality 质量 0-100
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void bitmapToPath(Bitmap bitmap, String filePath, String fileName, @IntRange(from = 0, to = 100) int quality) {
        if (bitmap == null || TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileName)) {
            return;
        }
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }

        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            dir.mkdirs();
        }

        FileOutputStream fos = null;
        try {
            File file = new File(filePath + fileName + ".jpg");
            if (file.exists()) {//删除旧照片
                file.delete();
            }
            file.createNewFile();
            fos= new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 把文件的path转为uri
     * @param context 上下文
     * @param path 文件路径
     */
    public static Uri filePath2Uri(Context context, String path) {
        try {
            Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = context.getContentResolver().query(mediaUri,
                    null,
                    MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                    new String[]{path.substring(path.lastIndexOf("/") + 1)},
                    null);
            Uri uri = null;
            if (cursor.moveToFirst()) {
                uri = ContentUris.withAppendedId(mediaUri,
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
            }
            cursor.close();
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.EMPTY;
    }

    /**
     * 将uri对象的图片文件复制到指定的目录
     * @param context 上下文
     * @param uri Uri对象
     * @param toPath 指定目录
     * @param fileName 文件名
     */
    public boolean copyFileFromUri(Context context, Uri uri, String toPath, String fileName){
        ParcelFileDescriptor fd = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            if (uri == Uri.EMPTY || TextUtils.isEmpty(toPath) || TextUtils.isEmpty(fileName)) {
                return false;
            }
            String newToPath = toPath.endsWith(File.separator) ? toPath : toPath + File.separator;
            File toDirectoryFile = new File(newToPath);
            if (!toDirectoryFile.exists()) {
                boolean isSuccess = toDirectoryFile.mkdirs();
                if (!isSuccess) {
                    return false;
                }
            }
            if (!toDirectoryFile.isDirectory()) {
                return false;
            }
            File toFile = new File(newToPath + fileName);
            if (toFile.exists()) {
                toFile.delete();
            }
            if (!toFile.createNewFile()) {
                return false;
            }
            fd = context.getContentResolver().openFileDescriptor(uri, "r");
            fis = new FileInputStream(fd.getFileDescriptor());
            fos = new FileOutputStream(toFile);
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            if (inChannel != null && outChannel != null) {
                inChannel.transferTo(0, inChannel.size(), outChannel);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (outChannel != null){
                    outChannel.close();
                }
                if (inChannel != null){
                    inChannel.close();
                }
                if (fos != null){
                    fos.close();
                }
                if (fis != null){
                    fis.close();
                }
                if (fd != null){
                    fd.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}