# component库
这个是基类的组件库，里面包含了Rxjava2、Retrofit2、Eventbus3、和对core库的依赖，小伙伴可以依赖这个库来进行敏捷开发

- 思考再三我还是没有使用MVP等架构来构建代码，我想将这个库定位为小型APP的开发组件。
- 如果小伙伴打算开发的app功能不太复杂，我建议可以尝试依赖这个库，直观的代码和常用方法的便捷封装有助于你快速完成功能开发。
- 如果小伙伴的app属于中大型，需要经常迭代或者维护人员更替较快的话，可能就不太适合这个库，可以寻求高解耦的组件库来满足你的需求。
- 当然如果你有兴趣基于这个库在上层继续扩展，我会非常开心，欢迎小伙伴们一起学习交流

## 目录
<!-- - [1、涉及的support依赖](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#1涉及的support依赖) -->
<!-- - [2、日志类PrintLog](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#2日志类printlog) -->
<!-- - [3、网络状态类NetworkManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#3网络状态类networkmanager) -->
<!-- - [4、加密相关](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#4加密相关) -->
<!-- - [5、线程池ThreadPoolManager](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#5线程池threadpoolmanager) -->
<!-- - [6、各种通用工具类](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#6各种通用工具类) -->
<!-- - [扩展](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#扩展) -->


## 1、涉及的依赖
该库已经引用了core、Rxjava2、Retrofit2、Rxlifecycle2以及Eventbus3.0，小伙伴不需要再重复引用，我会定期关注并更新版本，基本保证与最新版本一致
```
    dependencies {
        compile 'cn.lodz:core:1.0.10'
      
        compile 'io.reactivex.rxjava2:rxjava:2.0.8'
        compile 'io.reactivex.rxjava2:rxandroid:2.0.1'

        compile 'com.squareup.retrofit2:retrofit:2.2.0'
        compile 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'
        compile 'org.ligboy.retrofit2:converter-fastjson-android:2.1.0'
        compile 'com.alibaba:fastjson:1.1.56.android'

        compile 'com.trello.rxlifecycle2:rxlifecycle-android:2.0.1'
        compile 'com.trello.rxlifecycle2:rxlifecycle-components:2.0.1'

        compile 'org.greenrobot:eventbus:3.0.0'
    }
```




## 扩展
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md#core库)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)
