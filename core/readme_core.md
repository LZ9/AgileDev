# core库
这个是最底层的核心依赖库，主要用来放support库和通用的帮助类

## 目录
- [1、涉及的support依赖](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#1涉及的support依赖)
- [2、日志类PrintLog](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#2日志类printlog)
- [3、网络状态类NetworkManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#3网络状态类networkmanager)
- [4、加密相关](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#4加密相关)
- [5、线程池ThreadPoolManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#5线程池threadpoolmanager)
- [6、各种通用工具类](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#6各种通用工具类)
- [7、缓存Cache](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#7缓存cache)
- [8、通知帮助类NotificationUtils](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#8通知帮助类notificationutils)
- [8、状态栏帮助类StatusBarUtil](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#9状态栏帮助类StatusBarUtil)
- [扩展](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#扩展)

## 1、涉及的support依赖
该库引用了下方这些support库，如果您的app有重复引用可以选择去掉顶层引用或者保证版本一致
```
    dependencies {
        api 'com.android.support:appcompat-v7:27.1.1'
        api 'com.android.support:design:27.1.1'
        api 'com.android.support:recyclerview-v7:27.1.1'
        api 'com.android.support:cardview-v7:27.1.1'
        api 'com.android.support:support-annotations:27.1.1'
        api 'com.android.support.constraint:constraint-layout:1.1.0'
        api 'com.google.android:flexbox:0.3.2'
    }
```

## 2、日志类PrintLog
PrintLog主要封装了日志的打印开关，小伙伴可以在app里的build.gradle文件里添加日志开关的变量，如下所示：
```
    android {
        ....
        
        def LOG_DEBUG = "LOG_DEBUG"
        defaultConfig {
            buildConfigField "boolean", LOG_DEBUG, "true"
        }
        
        buildTypes {
            debug {
                .....
            }
            release {
                buildConfigField "boolean", LOG_DEBUG, "false"
                .....
            }
        }
    }
```
在application的初始化中可以配置该变量，起到开关日志的效果
```
    PrintLog.setPrint(BuildConfig.LOG_DEBUG)
```

## 3、网络状态类NetworkManager
网络管理采用单例模式，需要在application里初始化该类，如下所示
```
    NetworkManager.get().init(Context);
```
在退出应用的时候释放资源并且清除添加的网络状态回调监听器
```
    NetworkManager.get().release(Context);
    NetworkManager.get().clearNetworkListener();
```
如果你需要监听网络状态变化，可以增加一个监听器，如下所示：
```
    NetworkManager.get().addNetworkListener(NetworkManager.NetworkListener);
```
如果不需要再使用了可以移除掉这个监听器，你可以选择用完就立刻移除，也可以选择在退出app时统一移除
```
    NetworkManager.get().removeNetworkListener(NetworkManager.NetworkListener);
```
以下为常用方法，具体可以参见代码注释
```
    NetworkManager.get().isNetworkAvailable()
    NetworkManager.get().isWifi()
    NetworkManager.get().getNetType()
    NetworkManager.get().getSimOperator()
    NetworkManager.get().getOperatorInfo()
```
记得加入下面两个权限，确保功能正常使用
```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
## 4、加密相关
目前只收入了3个加密相关类，后续如果有用到其他的或者小伙伴们有需要会再陆续补充
- AES
- MD5
- SHA1

## 5、线程池ThreadPoolManager
1)我为大家准备了3个优先级的线程池（高、中、低），当执行该线程池时才会创建对应的线程池对象，调用方法分别如下：
```
    ThreadPoolManager.get().executeHighest(Runnable);
    ThreadPoolManager.get().executeNormal(Runnable);
    ThreadPoolManager.get().executeLowest(Runnable);
```

2)如果希望订制线程池的配置，可以使用下面的方法（）
```
    ThreadPoolManager.get().newBuilder()
        .setAwaitTime(50)// 设置线程结束等待时间
        .setAwaitTimeUnit(TimeUnit.MILLISECONDS)// 设置线程结束等待时间单位
        .setKeepAliveTime(1)// 设置线程数空闲时间
        .setKeepAliveTimeUnit(TimeUnit.SECONDS)// 设置线程数空闲时间单位
        .setCorePoolSize(4)// 设置线程数
        .setMaximumPoolSize(8)// 设置最大线程数
        .setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy())// 设置拒绝策略
        .build();
```


## 6、各种通用工具类
1. AlbumUtils 系统相册工具类，你可以通过里面的方法获取系统相册的图片和图片的文件夹信息
2. AnimUtils 动画帮助类，可传入anim资源来对view进行动画控制
3. AppUtils 应用帮助类，可获取应用的名称、版本号等信息
4. DateUtils 时间格式化帮助类，可以将毫秒时间戳格式化为时间字符串，反之亦然
5. DensityUtils 单位转换类，dp、px、sp之间的相互转换
6. DeviceUtils 设备帮助类，可获取手机的IMEI、IMESI、UA信息，获取前请注意动态获取权限
7. FileUtils 文件操作帮助类，封装了对文件操作相关的方法
8. KeyBoardUtils 软键盘控制类，可帮助你显隐键盘
9. NumberFormatUtils 数字格式化帮助类，可将数字格式化为你需要的小数样式
10. ReflectUtils 反射帮助类，封装了反射相关的方法，已在内部进行trycatch，小伙伴对返回的参数记得判空
11. ScreenUtils 屏幕信息帮助类，获取屏幕的宽高
12. SnackbarUtils Snackbar帮助类，定制简单的Snackbar提示
13. StorageUtils 存储帮助类，可获取存储路径和存储大小
14. StringUtils 字符串帮助类，可以对字符串进行UTF-8编码，根据分隔符把字符串转为list
15. ToastUtils Toast帮助类，可以显示简单的toast提示，也可以自定义Toast提示
16. UiHandler 把runnable post到UI线程执行的工具类，在app退出时调用UiHandler.destroy()释放资源
17. VibratorUtil 手机震动帮助类
18. BitmapUtils 图片处理帮助类，压缩转码等等
19. IdCardUtils 身份证校验类
20. ArrayUtils 数组列表帮助类
21. AssetsUtils 获取Assets资源的工具类
22. DrawableUtils 创建Drawable帮助类
23. SelectorUtils 按钮和文字Selector状态帮助类
24. SettingUtils 系统设置帮助类


## 7、缓存Cache
缓存这块引用了ACache来做，我封装了ACacheUtils方便小伙伴进行缓存的配置和使用。

- 在你APP启动的位置对ACacheUtils进行配置，如下代码：
```
    ACacheUtils.get().newBuilder()
        .setCacheDir(dirPath)// 设置缓存路径，不设置则使用默认路径
        .setMaxSize(maxSize)// 自定义缓存大小，不设置则使用默认大小
        .setMaxCount(maxCount)// 自定义缓存数量，不设置则使用默认数量
        .build(this);// 完成构建
```
```
    // 如果你均使用默认配置，那么请这么写
    ACacheUtils.get().newBuilder().build(this);
```
- 如果你要保存一个String数据，你可以这么使用
```
    ACacheUtils.get().create().put("data", "萌萌哒");
```
- 当然，你可以为你的数据设置缓存时间，类似下面这样
```
    ACacheUtils.get().create().put("data", "萌萌哒", 10);//保存10秒，如果超过10秒去获取这个key，将为null
    ACacheUtils.get().create().put("data", "萌萌哒", 2 * ACache.TIME_DAY);//保存两天，如果超过两天去获取这个key，将为null
```
- 如果你想获取一个String数据你可以这么使用
```
    ACacheUtils.get().create().getAsString("data")
```
- 如果你想清空缓存数据，可以调用下面的方法
```
    ACacheUtils.get().create().clear();
```
- 如果你想删除某个key对应的缓存，你可以这么干
```
    ACacheUtils.get().create().remove("data");
```


## 8、通知帮助类NotificationUtils
### 1）创建通知组和频道【适配O（api26）】
在Android8.0里发送通知需要指定该通知的频道id。

<1> 如果你的targetSdkVersion小于26，那么在创建Notification的时候，频道id可以传null来避开适配问题

<2> 如果你的targetSdkVersion大于等于26，那么你在创建Notification的时候必须指定频道id，否则发送时会抛出异常，无法正常显示通知

- 创建一个通知组，设置该通知组的groupId和groupName，只要在改APP里设置过一次以后就无需再重复设置
```
    NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
    NotificationUtils.create(getContext()).createNotificationChannelGroup(group);// 设置通知组
```
- 创建一个通知频道，设置该频道的channelId和channelName，只要在改APP里设置过一次以后就无需再重复设置.
- 通知频道内有许多参数可以设置，大家可以根据自己的需要配置参数
- 一个组里可以有多个频道，通过setGroup(groupId)方法，将该频道配置进id对应的组里
```
    NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
    channel.enableLights(true);// 开启指示灯，如果设备有的话。
    channel.setLightColor(Color.GREEN);// 设置指示灯颜色
    channel.setDescription("应用主通知频道");// 通道描述
    channel.enableVibration(true);// 开启震动
    channel.setVibrationPattern(new long[]{100, 200, 400, 300, 100});// 设置震动频率
    channel.setGroup(groupId);
    channel.canBypassDnd();// 检测是否绕过免打扰模式
    channel.setBypassDnd(true);// 设置绕过免打扰模式
    channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
    channel.canShowBadge();// 检测是否显示角标
    channel.setShowBadge(true);// 设置是否显示角标
    NotificationUtils.create(getContext()).createNotificationChannel(channel);// 设置频道
```

### 2）发送通知
通过NotificationCompat.Builder方法来构建你的通知内容，在获得Notification后可以传入NotificationUtils里的send()方法来发送通知
```
    // channelId的设置方法请参考第一点
    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId);
    
    ......

    Notification notification = builder.build();//构建通知
    NotificationUtils.create(getContext()).send(notification);
    NotificationUtils.create(getContext()).send(notifiId, notification);
```
- 如果你希望随机生成id来发送，你可以在send()方法里直接传入Notification
- 如果你希望指定id来发送，你可以在send()方法里传入id和Notification

## 9、状态栏帮助类StatusBarUtil
我这里直接使用了[laobie](https://github.com/laobie)的[StatusBarUtil](https://github.com/laobie/StatusBarUtil)，主要是对状态栏的颜色进行控制。
有兴趣了解的小伙伴可以直接到他的github里围观。


## 扩展

- [更新记录](https://github.com/LZ9/AgileDev/blob/master/core/readme_core_update.md)
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#core库)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)
