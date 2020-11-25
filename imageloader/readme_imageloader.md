# imageloader库
图片加载库支持Glide v4以上版本的引用，小伙伴可以根据自己的需要在自己的工程加入V4版本的引用来使用

## 目录
 - [1、使用Glide](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#1使用glide)
 - [1.1、外部引用](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#11外部引用)
 - [1.2、AndroidManifest.xml配置](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#12androidmanifestxml配置)
 - [1.3、初始化](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#13初始化)
 - [1.4、缓存操作](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#14缓存操作)
 - [1.5、使用方法](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#15使用方法)
 - [扩展](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#扩展)

## 1、使用Glide
### 1.1、内部引用
我将Glide内置在包内，支持androidx，图片库里已经集成了图片变换，请畅快使用
```
    dependencies {
        implementation 'androidx.appcompat:appcompat:1.2.0'
        implementation 'androidx.annotation:annotation:1.1.0'

        // glide
        api 'com.github.bumptech.glide:glide:4.11.0'
        annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
        implementation "com.github.bumptech.glide:okhttp3-integration:4.11.0"
    }
```

### 1.2、AppGlideModule实现
我帮大家简单的配置了一个CacheGlideModule，如果小伙伴需要自定义可以继承CacheGlideModule来进行扩展。

### 1.3、初始化
请在Application里面对图片库进行初始化，你可以根据自己的需要配置默认占位符、失败图、缓存路径等等。
```
    ImageloaderManager.get().newBuilder()
        .setPlaceholderResId(R.drawable.ic_launcher)//设置默认占位符
        .setErrorResId(R.drawable.ic_launcher)// 设置加载失败图
        .setDirectoryFile(this.getApplicationContext().getCacheDir())// 设置缓存路径
        .setDirectoryName("image_cache")// 缓存文件夹名称
        .build();
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
        .load("http://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg")// 设置加载路径（String/Uri/File/Integer/byte[]），请使用细类的load方法进行加载，比如：loadUrl、loadUri、loadFile等等
        .setPlaceholder(R.drawable.ic_launcher)// 设置加载图
        .setError(R.drawable.ic_launcher)// 设置加载失败图
        .setImageSize(100, 100)// 设置图片宽高
        .useCircle()// 使用圆形图片
        .useBlur()// 使用高斯模糊
        .setBlurRadius(10)// 设置高斯模糊
        .useRoundCorner()// 使用圆角
        .setRoundCorner(10)// 设置圆角
        .diskCacheStrategy(GlideBuilderBean.DiskCacheStrategy.AUTOMATIC)//设置磁盘缓存方式
        .skipMemoryCache()// 跳过图片缓存入内存
        .setCenterCrop()// 设置居中裁切
        .setFitCenter()// 设置居中自适应
        .setCenterInside()// 设置内部居中
        .dontAnimate()// 设置不使用动画
        .userCrossFade()// 使用默认渐变效果
        .setAnimResId(R.anim.anim_bottom_in)// 设置动画资源id
        .setAnim(new ViewPropertyTransition.Animator() {// 设置动画
            @Override
            public void animate(View view) {

            }
        })
        .setRequestListener(new RequestListener() {//请求监听器
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        })
        .useFilterColor()// 使用覆盖颜色
        .setFilterColor(ContextCompat.getColor(getContext(), R.color.color_60ea413c))// 设置覆盖颜色
        .setRoundedCornersMargin(5)// 设置圆角图片的Margin
        .setRoundedCornerType(RoundedCornersTransformation.CornerType.LEFT)// 设置圆角图片的位置参数
        .useGrayscale()// 使用灰度化
        .useCropSquare()// 切正方形图
        .useMask()// 使用蒙板图片
        .setVideo()// 展示本地视频第一帧
        .setMaskResId(R.drawable.mask)// 设置蒙板图片资源id
        .into(imageView);
```

## 扩展

- [更新记录](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader_update.md)
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md#imageloader库)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 core](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
