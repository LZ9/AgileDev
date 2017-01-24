package com.snxun.core.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        return isFileExists(newFile) && file.renameTo(newFile);
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
            if (childFile.toString().endsWith("."+suffix.toLowerCase())
                    || childFile.toString().endsWith("."+suffix.toUpperCase())){
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
     * 安装akp文件
     * @param context 上下文
     * @param apkPath apk路径
     */
    public void installApk(Context context, @NonNull String apkPath) {
        try {
            File file = createFile(apkPath);
            if (!isFileExists(file)){
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            File folder = createFile(filePath);
            return folder != null && !folder.exists() && folder.createNewFile();
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
        try {
            File fromFile = createFile(fromPath);
            File toFile = createFile(toPath);
            if (fromFile == null || toFile == null){
                return false;
            }
            FileInputStream inStream = new FileInputStream(fromFile);
            FileOutputStream outStream = new FileOutputStream(toFile);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
            inChannel.close();
            outChannel.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取该路径下的文件总长度（返回结果携带单位）
     * @param filePath 文件路径
     */
    public static String getFileTotalLengthUnit(String filePath) {
        return FormetFileSize(getFileTotalLength(filePath));
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
     * 转换文件大小（单位KB、MB、GB）
     * @param fileSize 文件大小
     */
    public static String FormetFileSize(long fileSize) {
        String fileSizeString = "0KB";
        if (fileSize == 0) {
            return fileSizeString;
        }
        DecimalFormat df = new DecimalFormat("##0.0");
        if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "GB";
        }
        return fileSizeString;
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


}