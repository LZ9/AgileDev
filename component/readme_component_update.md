# component库更新记录

##### 2018/09/13
1. 更新core版本
2. 更新Rxjava版本
3. 发布1.5.6版本

##### 2018/08/17
1. 优化加载库订阅者代码
2. RV基类适配器增加获取数据列表方法
3. 优化图片选择器提示逻辑
4. 更新依赖库
5. 兼容API28
6. 发布1.5.5版本

##### 2018/07/05
1. 优化RxUtils文本变化延迟回调方法
2. 发布1.5.4版本

##### 2018/07/04
1. 照片选择控件修复回调接口空指针BUG
2. 修改基类缓存临时数据方式
3. 更改组件库图片命名
4. 添加可不定入参的RxObservableOnSubscribe
5. 发布1.5.2版本

##### 2018/06/19
1. 修复RxUtils里图片编码base64不能静态调用的BUG
2. 发布1.5.1版本

##### 2018/06/08
1. 增加TransitionHelper，支持共享元素动画
2. 优化网格分割线和外围分割线的装饰器方法调用方式
3. 增加分组列表装饰器（固定和非固定数据）
4. 增加粘黏标签分组列表装饰器（固定和非固定数据）
5. 增加索引栏控件，支持横向索引和纵向索引
6. 增加侧边栏的默认宽度和默认head高度值
7. 增加TabPagerSnapHelper，支持与TabLayout联动
8. 更新RxJava和FastJson的引用版本
9. 发布1.5.0版本

##### 2018/05/22
1. 增加搜索标题栏控件
2. RV适配器在封装的getViewHolder位置传入ViewType
3. 发布1.4.15版本

##### 2018/05/17
1. RxUtils增加Single的线程切换方法
2. Sandwich基类增加获取顶部和底部布局方法
3. 发布1.4.13版本

##### 2018/05/11
1. 更新targetSdkVersion版本号
2. 发布1.4.12版本

##### 2018/05/03
1. 修改detach的调用位置
2. 修复titlebar没有阴影的BUG
3. 增加titlebar高度的dimens
4. 发布1.4.11版本

##### 2018/05/02
1. 修复标题栏控件背景设置问题
2. 增加解决横向嵌套冲突的RV控件
3. 更新RxJava版本
4. 发布1.4.10版本

##### 2018/04/28
1. 将基类适配器的onBind方法修改为public
2. 发布1.4.9版本

##### 2018/04/26
1. 状态控件增加ColorInt的设置方法
2. 发布1.4.8版本

##### 2018/04/25
1. BottomSheetDialog基类增加设置背景蒙版方法
2. MVP的ViewContract增加常用UI调用方法
3. 发布1.4.7版本

##### 2018/04/24
1. 拖拽帮助类增加震动开关
2. 增加BottomSheetDialog和BottomSheetDialogFragment基类
3. 修复BaseApplication里退出应用时没有先关闭activity的bug
4. 发布1.4.6版本

##### 2018/04/18
1. RxUtils增加线程调度方法
2. MVC增加BaseSandwichActivity、BaseSandwichFragment基类，适用于中部状态控件刷新和顶部/底部界面扩展
3. MVP增加MvpBaseSandwichActivity、MvpBaseSandwichFragment基类，用法与MVC一致
4. MVP的Presenter都统一继承BasePresenter
5. 发布1.4.5版本

##### 2018/04/02
1. 九宫格增加拖拽方法
2. 修改MVP接口基类，在Presenter里绑定Rx的生命周期
3. 增加DialogFragment基类
4. 修改引用关键字
5. 发布1.4.4版本

##### 2018/03/15
1. 修复图片选择器默认拍照图标丢失的BUG
2. RxUtils增加图片路径转BASE64的方法、增加背压和非背压的io_main方法、增加防抖点击和文本变化监听
3. RV适配器基类优化，增加获取数据长度的方法
4. 增加2种RV的item装饰器实现类（自定义线条型和网格型）
5. 优化RV侧滑适配器的使用方法
6. 更新引用库的版本号
7. 优化背压和非背压订阅者的实现类
8. 增加九宫格控件和简单实现的九宫格控件
9. 发布1.4.3版本

##### 2018/01/26
1. 基础控件增加attrs和xml渲染
2. 图片选择器增加直接拍照功能
3. 发布1.4.2版本

##### 2017/12/20
1. 图片选择器增加FileProvider设置方法
2. 图片选择器增加预览开关设置
3. RxUtils增加获取异常提示语方法
4. 增加可内嵌在ScrollView里的EditText控件
5. 增加RV侧滑适配器基类和简单实现类
6. 发布1.3.9版本

##### 2017/10/27
1. Rx帮助类添加获取异常提示语方法
2. 修改基类订阅者的日志打印方法
3. 优化加载更多适配器
4. 添加图片选择器和图片预览器
5. 发布1.3.6版本

##### 2017/09/26
1. 添加动画dialog抽象类
2. 修复RecyclerView拖拽帮助类的BUG
3. 标题栏增加阴影
4. 封装PopupWindow基类
5. RecyclerView添加自定义动画
6. 优化RecyclerView添加头部/底部的适配器
7. 添加侧滑菜单控件
8. 修复MVP相关的BUG
9. 发布1.3.5版本

##### 2017/08/21
1. 对组件库里引用到BaseApplication的地方做判空处理
2. 基础控件增加布局方向配置
3. 发布1.3.1版本

##### 2017/08/02
1. AbsActivity添加对Fragment的添加、隐藏操作
2. 修复带Head的RecyclerView适配器总数回调异常的BUG
3. 增加可修改底线宽度的TabLayout
4. 重构BaseDialog的实现
5. 加载更多适配器增加数据隐藏
6. 在Application里增加对基类样式的统一配置
7. 增加MVP模式的Activity基类和Fragment基类，支持MVP模式开发
8. 增加禁止左右滑动的ViewPager
9. 基类Fragment添加onFragmentResume和onFragmentPause，回调周期与Activity一致
10. 修复基类Activity对Fragment返回键拦截判断错误的BUG
11. 发布1.3.0版本

##### 2017/04/06
1. 将Activity和Fragment基类里的加载状态控件全部改为ViewStub
2. 增加带Head的RecyclerView适配器
3. 发布1.2.0版本

##### 2017/03/30
1. BaseApplication增加应用进入后台时保存数据和从后台返回恢复数据的方法
2. 发布1.1.9版本

##### 2017/03/24
1. 增加RecyclerView的拖拽帮助类RecyclerViewDragHelper
2. 加载更多Adapter增加对网格布局的支持
3. 发布1.1.7版本

##### 2017/03/15
1. 加载更多Adapter增加手动设置加载完成
2. 更新RxJava和Retrofit的依赖版本
3. 发布1.1.5版本

##### 2017/03/02
1. 在基类的Activity里注册和解注册EventBus，通过EventBus来关闭所有Activity
2. 增加带下来刷新控件的BaseRefreshActivity
3. 增加对加载更多Adapter的简单实现BaseSimpleLoadMoreRecyclerViewAdapter
4. 修复加载更多帮助类的BUG
5. Fragment抽象出不带加载控件的LazyFragment
6. 增加带下拉刷新的BaseRefreshFragment
7. 增加背压订阅者的封装
8. 添加Rx帮助类
9. 发布1.1.1版本

##### 2017/02/23
1. 增加异常打印标签设置
2. 抽象出不带状态控件的基类AbsActivity
3. 扩展加载状态控件的定制方法
4. 订阅者在内部多增加一个网络异常判断
5. 发布1.1.0版本

##### 2017/02/17
1. 修复封装订阅者BUG
2. 添加加载更多帮助类，整合加载更多操作
3. 发布1.0.4版本

##### 2017/02/14
1. 提交初始版本
2. 增加RxJava和Retrofit相关依赖
3. 封装背压和无背压订阅者
4. 添加加载状态控件
5. 发布1.0.2版本

## 扩展
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/component/readme_component_update.md#component库更新记录)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 core](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md)
- [了解 component](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)
