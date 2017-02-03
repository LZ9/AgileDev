package com.lodz.android.imageloader;

import com.lodz.android.imageloader.contract.ImageLoaderContract;
import com.lodz.android.imageloader.fresco.impl.FrescoImageLoader;

/**
 * 图片加载器
 * Created by zhouL on 2016/11/18.
 */

public class ImageLoader {
    public static ImageLoaderContract create(){
        return FrescoImageLoader.create();
    }
}
