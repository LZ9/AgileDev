# component库更新记录

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
