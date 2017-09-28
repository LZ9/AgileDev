package com.lodz.android.core.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Assets工具类
 * Created by zhouL on 2017/9/28.
 */

public class AssetsUtils {

    /**
     * 获取Assets下的文件内容
     * @param context 上下文
     * @param fileName 文件内容
     */
    public static String getAssetsFileContent(@NonNull Context context, @NonNull String fileName) {
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open(fileName), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
