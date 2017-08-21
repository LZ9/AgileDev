# component库更新记录

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
