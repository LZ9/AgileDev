# imageloader库
图片加载库目前支持两个主流的图片加载框架Glide和Fresco，小伙伴可以根据自己的需要在自己的工程加入对应的引用来使用

## 目录
<!-- - [1、涉及的support依赖](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#1涉及的support依赖) -->
<!-- - [2、日志类PrintLog](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#2日志类printlog) -->
<!-- - [3、网络状态类NetworkManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#3网络状态类networkmanager) -->
<!-- - [4、加密相关](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#4加密相关) -->
<!-- - [5、线程池ThreadPoolManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#5线程池threadpoolmanager) -->
<!-- - [6、各种通用工具类](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#6各种通用工具类) -->
<!-- - [扩展](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#扩展) -->

## 1、使用Glide
### 1.1、外部引用
使用Glide的小伙伴可以加入下面两个依赖，其中第2个为使用okhttp3的依赖，如果小伙伴们不使用可以去掉，我在图片库里已经集成了图片变换
```
    dependencies {
        compile 'com.github.bumptech.glide:glide:3.7.0'
        compile 'com.github.bumptech.glide:okhttp3-integration:1.4.0@aar'
    }
```

### 1.2、AndroidManifest.xml配置
如果小伙伴希望使用自设的缓存路径，那么需要在AndroidManifest里面配置meta-data信息。
我帮大家简单的配置了一个CacheGlideModule，如果小伙伴需要自定义可以继承GlideModule来自定义GlideModule。
```
    <application
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
    
        <meta-data
            android:name="com.lodz.android.imageloader.glide.config.CacheGlideModule"
            android:value="GlideModule" />
            
            .....
    
    </application>
```

### 1.3、初始化
请在Application里面对图片库进行初始化，你可以根据自己的需要配置默认占位符、失败图、缓存路径等等。
```
    ImageloaderManager.get().newBuilder()
        .setPlaceholderResId(R.drawable.ic_launcher)//设置默认占位符
        .setErrorResId(R.drawable.ic_launcher)// 设置加载失败图
        .setDirectoryFile(this.getApplicationContext().getCacheDir())// 设置缓存路径
        .setDirectoryName("image_cache")// 缓存文件夹名称
        .build(this);
```

### 1.4、缓存操作
1）清除内存缓存
```
    ImageloaderManager.get().clearMemoryCaches(context);
```
2）清除内存缓存（包括GC内存）
```
    ImageloaderManager.get().clearMemoryCachesWithGC(context);
```
3）清除磁盘缓存
```
    ImageloaderManager.get().clearDiskCaches(context);
```
4）清除所有缓存（内存+磁盘）
```
    ImageloaderManager.get().clearCaches(context);
```
5）暂停加载
```
    ImageloaderManager.get().pauseLoad(context);
```
6）恢复加载
```
    ImageloaderManager.get().resumeLoad(context);
```
7）是否暂停加载
```
    ImageloaderManager.get().isPaused(context);
```

### 1.5、使用方法
下面为基本的使用方法，你可以根据自己的需要链上对应的方法，对图片进行控制。
```
    ImageLoader.create(this)
        .load("http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg")// 设置加载路径（String/Uri/File/Integer/byte[]）
        .setPlaceholder(R.drawable.ic_launcher)// 设置加载图
        .setError(R.drawable.ic_launcher)// 设置加载失败图
        .setImageSize(100, 100)// 设置图片宽高
        .useCircle()// 使用圆形图片
        .useBlur()// 使用高斯模糊
        .setBlurRadius(10)// 设置高斯模糊
        .useRoundCorner()// 使用圆角
        .setRoundCorner(10)// 设置圆角
        .joinGlide()// 进入Glide特性
        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.SOURCE)//设置磁盘缓存方式
        .skipMemoryCache()// 跳过图片缓存入内存
        .setCenterCrop()// 设置居中裁切
        .setFitCenter()// 设置居中自适应
        .useAnimate()// 设置使用动画
        .userCrossFade()// 使用默认渐变效果
        .setAnimResId(R.anim.anim_bottom_in)// 设置动画资源id
        .setAnim(new ViewPropertyAnimation.Animator() {// 设置动画
            @Override
            public void animate(View view) {

            }
        })
        .useFilterColor()// 使用覆盖颜色
        .setFilterColor(ContextCompat.getColor(getContext(), R.color.color_60ea413c))// 设置覆盖颜色
        .setRoundedCornersMargin(5)// 设置圆角图片的Margin
        .setRoundedCornerType(RoundedCornersTransformation.CornerType.LEFT)// 设置圆角图片的位置参数
        .useGrayscale()// 使用灰度化
        .useCropSquare()// 切正方形图
        .useMask()// 使用蒙板图片
        .setMaskResId(R.drawable.mask_starfish)// 设置蒙板图片资源id
        .into(imageView);
```

## 2、使用Fresco
### 2.1、外部引用
使用Fresco的小伙伴可以加入下面这些依赖，其中第2个为使用okhttp3的依赖，3-6个为可选依赖，小伙伴根据自己的实际情况选择
```
    dependencies {
        compile 'com.facebook.fresco:fresco:1.2.0'
        compile 'com.facebook.fresco:imagepipeline-okhttp3:1.2.0'// 支持 OkHttp3，需要添加
        
        compile 'com.facebook.fresco:animated-gif:1.2.0'// 支持 GIF 动图，需要添加
        compile 'com.facebook.fresco:animated-webp:1.2.0'// 在 API < 14 上的机器支持 WebP 时，需要添加
        compile 'com.facebook.fresco:animated-base-support:1.2.0'// 支持 WebP 动图，需要添加
        compile 'com.facebook.fresco:webpsupport:1.2.0'// 支持 WebP 静态图，需要添加
    }
```

### 2.2、初始化
请在Application里面对图片库进行初始化，你可以根据自己的需要配置默认占位符、失败图、重试图片、缓存路径等等。
```
    ImageloaderManager.get().newBuilder()
        .setPlaceholderResId(R.drawable.ic_launcher)//设置默认占位符
        .setErrorResId(R.drawable.ic_launcher)// 设置加载失败图
        .setFrescoRetryResId(R.drawable.ic_launcher)// 设置默认重载图片
        .setFrescoTapToRetryEnabled(false)// 开启加载失败重试
        .setFrescoAutoPlayAnimations(true)// 开启GIF自动播放
        .setDirectoryFile(this.getApplicationContext().getCacheDir())// 设置缓存路径
        .setDirectoryName("image_cache")// 缓存文件夹名称
        .build(this);
```

### 2.3、缓存操作
1）清除内存缓存
```
    ImageloaderManager.get().clearMemoryCaches(context);
```
2）清除内存缓存（包括GC内存）
```
    ImageloaderManager.get().clearMemoryCachesWithGC(context);
```
3）清楚磁盘缓存
```
    ImageloaderManager.get().clearDiskCaches(context);
```
4）清除所有缓存（内存+磁盘）
```
    ImageloaderManager.get().clearCaches(context);
```
5）暂停加载
```
    ImageloaderManager.get().pauseLoad(context);
```
6）恢复加载
```
    ImageloaderManager.get().resumeLoad(context);
```
7）是否暂停加载
```
    ImageloaderManager.get().isPaused(context);
```

### 2.4、使用操作
下面为基本的使用方法，你可以根据自己的需要链上对应的方法，对图片进行控制。
```
    ImageLoader.create(this)
        .load(UriUtils.parseUrl("http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg"))// 设置加载路径，请使用UriUtils将图片地址或资源id转为uri
        .setPlaceholder(R.drawable.ic_launcher)// 设置加载图
        .setError(R.drawable.ic_launcher)// 设置加载失败图
        .setImageSize(100, 100)// 设置图片宽高
        .useCircle()// 使用圆形图片
        .useBlur()// 使用高斯模糊
        .setBlurRadius(10)// 设置高斯模糊
        .useRoundCorner()// 使用圆角
        .setRoundCorner(10)// 设置圆角
        .joinFresco()// 进入Fresco特性
        .setPlaceholderScaleType(ScalingUtils.ScaleType.CENTER)// 设置加载图的缩放类型
        .setErrorScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)// 设置加载失败图的缩放类型
        .serRetry(R.drawable.ic_launcher)// 设置重试图片
        .serRetryScaleType(ScalingUtils.ScaleType.CENTER_CROP)// 设置重试图片的缩放类型
        .setImageScaleType(ScalingUtils.ScaleType.CENTER)// 设置图片的缩放类型
        .setResizeOptions(100, 100)// 设置图片在内存的大小，类似分辨率，如果已经设置了setImageSize()方法则不需要再重复设置该方法
        .setAspectRatio(1.2f)// 设置固定宽高比(w/h)
        .setProgressBarImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher))// 设置加载进度图
        .setBackgroundImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher))// 设置背景图
        .setOverlayImage(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher))// 设置叠加图
        .setFadeDuration(1200)// 设置淡入展示的时间
        .setBorder(Color.GREEN, 5)// 设置描边
        .setLocalThumbnailPreviews(true)// 加载本地图片时使用缩略图预览（多图预览时建议开启，否则会导致卡顿）
        .setControllerListener(new BaseControllerListener())// 设置控制监听器
        .wrapImageHeight(200)// 指定宽度，高度自适应图片
        .wrapImageWidth(120)// 指定高度，宽度自适应图片
        .wrapImage()// 宽、高都自适应图片，宽 >= 高：图片宽超过屏幕时，以屏幕的宽作为宽度来换算高度，宽 < 高：图片高超过屏幕时，以屏幕的高作为高度来换算宽度
        .into(simpleDraweeView);
```

## 扩展

- [更新记录](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader_update.md)
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#imageloader库)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 core](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
