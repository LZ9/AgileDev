# core库
这个是最底层的核心依赖库，主要用来放support库和通用的帮助类
## 目录
- [1、涉及的support依赖](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#1涉及的support依赖)
- [2、日志类PrintLog](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#2日志类printlog)
- [3、网络状态类NetworkManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#3网络状态类networkmanager)
- [4、加密相关](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#4加密相关)
- [5、线程池ThreadPoolManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#5线程池threadpoolmanager)
- [6、各种通用工具类](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#6各种通用工具类)
- [扩展](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#扩展)
## 1、涉及的support依赖
该库引用了下方这些support库，如果您的app有重复引用可以选择去掉顶层引用或者保证版本一致
```
    dependencies {
        compile 'com.android.support:appcompat-v7:25.3.1'
        compile 'com.android.support:design:25.3.1'
        compile 'com.android.support:recyclerview-v7:25.3.1'
        compile 'com.android.support:support-annotations:25.3.1'
        compile 'com.android.support.constraint:constraint-layout:1.0.2'
        compile 'com.google.android:flexbox:0.2.6'
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
```
## 4、加密相关
目前只收入了3个加密相关类，后续如果有用到其他的或者小伙伴们有需要会再陆续补充
- AES
- MD5
- SHA1
## 5、线程池ThreadPoolManager
我为大家准备了3个优先级的线程池（高、中、低），当执行该线程池时才会创建对应的线程池对象，调用方法分别如下：
```
    ThreadPoolManager.get().executeHighest(Runnable);
    ThreadPoolManager.get().executeNormal(Runnable);
    ThreadPoolManager.get().executeLowest(Runnable);
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
14. StringUtils 字符串帮助类，目前仅收录对字符串进行UTF-8编码
15. ToastUtils Toast帮助类，显示简单的toast提示
16. UiHandler 把runnable post到UI线程执行的工具类，在app退出时调用UiHandler.destroy()释放资源
17. VibratorUtil 手机震动帮助类，手机震动帮助类

## 扩展
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#core库)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)
