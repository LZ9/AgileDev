# core库
这个是最底层的核心依赖库，主要用来放support库和通用的帮助类

- [回到主页](https://github.com/LZ9/AgileDev)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)
## 1、涉及的support依赖
该库引用了下方这些support库，如果您的app有重复引用可以选择去掉顶层引用或者保证版本一致
```
    dependencies {
        compile 'com.android.support:appcompat-v7:25.3.1'
        compile 'com.android.support:design:25.3.1'
        compile 'com.android.support:recyclerview-v7:25.3.1'
        compile 'com.android.support:support-annotations:25.3.1'
        compile 'com.android.support.constraint:constraint-layout:1.0.1'
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
### 1. 系统相册工具类 AlbumUtils

### 2. 动画帮助类 AnimUtils


