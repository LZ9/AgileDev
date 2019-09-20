# 敏捷开发解决方案
这是一套基础敏捷开发架构，适用于快速开发一个中小型APP，可基于该架构再进行上层扩展

目前支持简单的继承基类开发，也支持MVP模式开发，小伙伴可以根据需要自行选择。

目前处于稳定阶段，基本只会修改一些小BUG大家可以使用当前的稳定版本，感兴趣的小伙伴也可以下载源码查看。

每一块内容都有对应的描述文档和升级日志，方便小伙伴们了解和查看。
> 啦啦桑

<(￣︶￣)>

## Kotlin版本
 - 我已将Java版本转为Kotlin版本，包含更多的语法糖和更丰富的方法调用，感兴趣的小伙伴可以到[AgileDevKt](https://github.com/LZ9/AgileDevKt)工程查看。

## 1、添加Gradle依赖：
#### 1) support包版本（停止维护，请使用androidx包版本）
```
    implementation 'cn.lodz:core:1.2.14'
```
```
    implementation 'cn.lodz:Component:1.5.7'
```
```
    implementation 'cn.lodz:ImagerLoader:1.2.3'
```

#### 2) androidx包版本
```
    implementation 'cn.lodz:core:2.0.7'
```
```
    implementation 'cn.lodz:Component:2.1.0'
```
```
    implementation 'cn.lodz:ImagerLoader:2.0.4'
```
## 2、详细了解
- [了解 core](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)
## 3、关于Issues
如果小伙伴发现BUG或者有任何的建议，都欢迎到 [Github Issues](https://github.com/LZ9/AgileDev/issues) 上留言，我会定期查看回复哒

## License
- [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

Copyright 2019 Lodz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.