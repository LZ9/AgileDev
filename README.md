# 敏捷开发架构
这是一套基础敏捷开发架构，目前还只是使用lib的形式，之后会慢慢改为依赖，如果大家在使用的过程中发现BUG或者觉得不合理的地方欢迎留言告诉我~
> 啦啦桑

> E-mail：<lz.mms@foxmail.com>

<(￣︶￣)>

## 更新日志：
### 2017/01/24
上传基础工程
### 2017/02/03
1、core库是开发基础库（第一层），包括了android的support包引用、日志类、加密类和其他通用帮助类

2、component库是基类库（第二层），包括了RxJava相关引用、Activity/Fragment/Application的基类、Adapter的基类等等

3、imageloader库（第三层），目前该库只实现了Fresco，之后会支持Glide等更多加载库，改为配置切换。
