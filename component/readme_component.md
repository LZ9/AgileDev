# component库
这个是基类的组件库，里面包含了Rxjava2、Retrofit2、Eventbus3、Fastjson和对core库的依赖，小伙伴可以依赖这个库来进行敏捷开发

- 我想将这个库定位为中小型APP的开发组件，支持简单的基类继承和简单MVP模式开发。
- 如果小伙伴打算开发的app功能不太复杂，我建议可以尝试依赖这个库，直观的代码和常用方法的便捷封装有助于你快速完成功能开发。
- 如果小伙伴的app属于中大型，需要经常迭代或者维护人员更替较快的话，可能就不太适合这个库，可以寻求高解耦的组件库来满足你的需求。
- 当然如果你有兴趣基于这个库在上层继续扩展，我会非常开心，欢迎小伙伴们一起学习交流

## 目录
 - [1、涉及的依赖](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#1涉及的依赖)
 - [2、Application基类BaseApplication](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#2application基类baseapplication)
 - [3、Activity基类](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#3activity基类)
 - [4、Fragment基类](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#4fragment基类)
 - [5、Rx相关](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#5rx相关)
 - [6、RecyclerView相关](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#6recyclerview相关)
 - [7、Dialog相关](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#7dialog相关)
 - [8、自定义widget](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#8自定义widget)
 - [9、MVP相关](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#9MVP相关)
 - [扩展](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#扩展)

## 1、涉及的依赖
该库已经引用了core、Rxjava2、Retrofit2、Rxlifecycle2以及Eventbus3.0，小伙伴不需要再重复引用，我会定期关注并更新版本，基本保证与最新版本一致
```
    dependencies {
        compile 'cn.lodz:core:1.0.24'
    
        compile 'io.reactivex.rxjava2:rxjava:2.1.2'
        compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
        
        compile 'com.squareup.retrofit2:retrofit:2.3.0'
        compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
        
        compile 'com.alibaba:fastjson:1.2.35'
        
        compile 'com.trello.rxlifecycle2:rxlifecycle-android:2.1.0'
        compile 'com.trello.rxlifecycle2:rxlifecycle-components:2.1.0'
        
        compile 'org.greenrobot:eventbus:3.0.0'
    }
```

## 2、Application基类BaseApplication
1）BaseApplication内部已经实现了单例模式，可以用下面的方法来获取application单例
```
    BaseApplication.get();
```
如果你希望获取自定义的Application子类对象，你可以尝试使用下方的方式
```
    public class App extends BaseApplication {
    
        public static App getInstance(){
            return (App) get();
        }
        .....
    }
```

2）BaseApplication目前有两个抽象方法，分别是在onCreate()里回调的 **afterCreate()** 以及在退出应用时回调的 **beforeExit()** ，
你可以在顶层app包里自定义一个Application继承BaseApplication，并实现这两个方法（例如初始化某些资源以及退出时释放某些资源）

3）如果你希望退出整个应用，可以调用下方的方法，它会关闭你所有的Activity（前提是这些Activity继承自基类库），调用该方法后会回调 **beforeExit()**
```
    BaseApplication.get().exit();
```

4）如果你希望在app进入后台时保存关键数据，你可以重写下方的方法，将你需要保存的数据放在Bundle中传给父类
```
    @Override
    public Bundle getSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString("key", value);
        return bundle;
    }
```
当你的app在后台被系统回收，并又重新回到前台时，你可以重写下方的方法，在该方法中获取你之前保存的关键数据
```
    @Override
    public void getRestoreInstanceState(Bundle bundle) {
        super.getRestoreInstanceState(bundle);
        if (bundle != null){
            value = bundle.getString("key", "");
        }
    }
```
5）你可以在通过 **getBaseLayoutConfig()** 获取到基类的配置对象，自由的对基类的状态控件进行统一配置

## 3、Activity基类
### 1）AbsActivity
a）AbsActivity是最底层的Activity，如果你不需要用到数据加载状态界面的话，可以选择继承这个Activity

b）我在这个Activity里注册了EventBus，因此只要是继承它的Activity都不需要再重复注册和解注册，只需要订阅即可

c）我将 **onCreate(@Nullable Bundle savedInstanceState)** 分为6个方法调用顺序分别如下
```
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        .....
        startCreate();
        setContentView(getAbsLayoutId());
        findViews(savedInstanceState);
        setListeners();
        initData();
        endCreate();
    }
```
- 重写 **startCreate()** 方法，可以在里面获取Intent的数据
- 实现 **getAbsLayoutId()** 方法，返回layout的资源id
- 实现 **findViews(savedInstanceState)** 方法，你可以在该方法里进行资源id获取对象的操作
- 重写 **setListeners()** 方法，你可以在该方法里对需要的对象设置各类监听器回调
- 重写 **initData()** 方法，你可以在该方法里初始化界面的业务数据
- 重写 **endCreate()** 方法，你可以在该方法里处理不属于上述方法分类的数据

d）你可以直接调用下面的方法来获取当前Activity的上下文
```
    getContext()
```
e）你可以重写下面的方法来拦截用户点击返回按钮的事件，返回true表示消耗掉，返回false表示继续传递事件
```
    onPressBack()
```
f）你可以直接通过下面的方法将订阅绑定生命周期，避免内存泄漏
```
    .compose(this.<T>bindUntilEvent(ActivityEvent.DESTROY))
```
g）如果你需要再Activity里直接添加Fragment，你可以直接调用下面封装好的方法，帮助你简单快速的使用
```
    /** 添加fragment */
    addFragment(@IdRes int containerViewId, Fragment fragment, @Nullable String tag)
    
    /** 替换fragment */
    replaceFragment(@IdRes int containerViewId, Fragment fragment, @Nullable String tag)
    
    /** 显示fragment */
    showFragment(Fragment fragment)
    
    /** 隐藏fragment */
    hideFragment(Fragment fragment)
    
    等等....（详见代码）
```

### 2）BaseActivity
a）BaseActivity继承自AbsActivity，并在内部增加了数据加载状态界面，如果你需要用到界面级别的数据加载状态UI可以选择继承这个Activity

b） **TitleBarLayout** 为界面顶部的标题栏，如果你不希望显示TitleBarLayout，可以调用下方的方法隐藏
```
    goneTitleBar()
```
如果你希望对TitleBarLayout做一些定制，你可以调用下面的方法来获取TitleBarLayout的对象
```
    getTitleBarLayout()
```
你可以重写下面的方法，在方法内实现用户点击标题栏的返回按钮的操作
```
    clickBackBtn()
```
c） **LoadingLayout** 为加载控件，如果你希望在异步获取数据时将界面显示为加载状态，可以调用下面的方法
```
    showStatusLoading()
```
如果你希望对LoadingLayout做一些轻量级的定制，你可以调用下面的方法获取LoadingLayout的对象
```
    getLoadingLayout()
```
d） **NoDataLayout** 为无数据控件，如果你在获取数据后需要展示无数据界面，可以调用下面的方法
```
    showStatusNoData()
```
如果你希望对NoDataLayout做一些轻量级的定制，你可以调用下面的方法获取NoDataLayout的对象
```
    getNoDataLayout()
```
e） **ErrorLayout** 为界面异常控件，如果你在获取数据后发现获取的数据不正确，可以调用下列方法显示
```
    showStatusError()
```
如果你希望对ErrorLayout做一些轻量级的定制，你可以调用下面的方法获取ErrorLayout的对象
```
    getErrorLayout()
```
如果你希望用户点击该界面可以重新加载数据的话，你可以重写下面的方法，并在方法里实现数据的加载
```
    clickReload()
```

### 3）BaseRefreshActivity
a）BaseRefreshActivity继承自AbsActivity，内部除了包含数据加载的状态界面外，还包括了一个下拉刷新控件，如果你的界面是需要下拉刷新数据的，可以选择继承这个Activity

b） **SwipeRefreshLayout** 为下拉刷新控件，你可以实现下面的方法，在方法里执行数据的刷新逻辑
```
    onDataRefresh()
```
如果你希望设置下拉进度的切换颜色，你可以调用下面的方法
```
    setSwipeRefreshColorScheme(@ColorRes int... colorResIds)
```
如果你希望设置下拉进度的背景颜色，你可以调用下面的方法
```
    setSwipeRefreshBackgroundColor(@ColorRes int colorResId)
```
**当你结束下拉刷新时（不论获取数据成功还是失败），切记要调用下面的方法，否则进度条不会隐藏**
```
    setSwipeRefreshFinish()
```
如果你希望在某些条件下禁用刷新控件，某些条件下启用的话，可以调用下面的方法
```
    setSwipeRefreshEnabled(boolean enabled)
```
c）加载状态界面的使用方式与BaseActivity一致

## 4、Fragment基类
### 1）LazyFragment
a）LazyFragment是最底层的Fragment，如果你不需要用到数据加载状态界面的话，可以选择继承这个Fragment。

b）这个Fragment实现了懒加载，即当这个Fragment显示的时候再加载数据。LazyFragment默认进行懒加载，如果你不希望懒加载可以重写下面的方法，取消懒加载设置
```
    @Override
    protected boolean configIsLazyLoad() {
        return false;
    }
```

c）同AbsActivity一样，我也将 **onViewCreated(View view, @Nullable Bundle savedInstanceState)** 分为6个方法，具体调用顺序和使用方法与AbsActivity一致，这里就不再赘述，顺序和方法名如下
```
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        .....
        startCreate();
        findViews(view, savedInstanceState);
        setListeners(view);
        initData(view);
        endCreate();
    }

    .....

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getAbsLayoutId(), container, false);
    }

    @LayoutRes
    protected abstract int getAbsLayoutId();
```

d）你可以重写下面的方法来拦截用户点击返回按钮的事件，返回true表示在当前的fragment里消耗掉返回事件，返回false则向上传递给父类
```
    onPressBack()
```
e）如果你需要在fragment里实现Activity里的OnResume的生命周期，你可以重写下面的方法，该方法的回调时机与Activity里的OnResume保持一致
```
    onFragmentResume()
```
f）如果你需要在fragment里实现Activity里的onPause的生命周期，你可以重写下面的方法，该方法的回调时机与Activity里的onPause保持一致
```
    onFragmentPause()
```
g）你可以直接通过下面的方法将订阅绑定生命周期，避免内存泄漏
```
    .compose(this.<T>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
```

### 2）BaseFragment
BaseFragment继承自LazyFragment，同样在内部增加了数据加载状态界面，用法与BaseActivity保持一致，这里不再赘述。

### 3）BaseRefreshFragment
BaseRefreshFragment继承自LazyFragment，和BaseRefreshActivity一样，包含了加载状态界面和下拉刷新，使用方法和BaseRefreshActivity里的一样。

## 5、Rx相关
RxJava2.0区分了背压概念，将订阅者分为Observer和Subscriber，我这边以Observer为例来讲解我对订阅者做的基础封装。（Subscriber与Observer的封装基本一致，不再赘述）
### 1）BaseObserver
a）BaseObserver实现Observer，主要是对Disposable做了基础封装，如果你需要获取这个订阅者的Disposable，你可以调用下面的方法
```
    getDisposable()
```
b）如果你希望取消订阅，你可以调用下面的方法来取消
```
    dispose()
```
c）如果你希望清除内部封装的Disposable对象，可以调用下面的方法
```
    clearDisposable()
```
d）如果你希望能在指定的标签下打印onError()里出来的错误信息，可以在app里的AndroidManifest.xml里的application标签内配置meta-data信息

```
    <application
        .....
        android:icon="@mipmap/ic_launcher" >

        .....

        <meta-data android:value="yourTag" android:name="error_tag"/>

        .....

    </application>
```

### 2）RxObserver
a）RxObserver继承BaseObserver，主要是对接口数据校验进行封装，适用于对接口数据的订阅。
只需要在你所封装的最外层的Bean里实现ResponseStatus接口里的isSuccess()方法，
RxObserver在获取数据时会根据isSuccess()方法的返回值来进行数据校验，如果isSuccess()返回的是false则会抛出DataException，
你可以从DataException里获取实现了ResponseStatus的Bean对象，对数据进行相应的展示。

b）RxObserver会抛出的异常包括4类：
- 基础异常RxException：RxException是这4类异常的父类，它包裹着Exception的所有信息
- 网络未连接NetworkNoConnException：当用户未连接网络调用接口时，会抛出这个异常
- 网络超时NetworkTimeOutException：当用户请求的数据超时时会抛出这个异常
- 数据异常DataException：对象的isSuccess()返回false时，会抛出该异常

### 3）ProgressObserver
ProgressObserver继承RxObserver，增加了一个加载等待框的封装，如果小伙伴在跑接口时需要一个加载框，那可以利用这个类来订阅。
简单的调用如下所示，create方法有多个，可以根据实际需要选择一个创建 **（如果不调用create()是不会显示加载框的）** 。用户取消订阅时会回调onPgCancel()方法
```
    Observable.just(1)
        .subscribe(new ProgressObserver<Integer>() {

            @Override
            public void onPgSubscribe(Disposable d) {

            }

            @Override
            public void onPgNext(Integer integer) {

            }

            @Override
            public void onPgError(Throwable e, boolean isNetwork) {

            }

            @Override
            public void onPgComplete() {

            }

            @Override
            protected void onPgCancel() {
                super.onPgCancel();

            }

    }.create(getContext()));
```

### 4）RxUtils
目前RxUtils只收录了异步线程发起主线程订阅的方法，即
```
    .compose(RxUtils.<T>io_main())
```

## 6、RecyclerView相关
### 1）BaseRecyclerViewAdapter
这个是RecyclerView适配器的基类adapter，继承这个基类，
实现onCreateViewHolder(ViewGroup parent, int viewType)和onBind(RecyclerView.ViewHolder holder, int position)两个抽象方法，
来实现适配器逻辑
```
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(getLayoutView(parent, R.layout.item_view_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        ....
    }
```

### 2）BaseHeadRecyclerViewAdapter
带头布局的RecyclerView适配器基类，接触这个基类，
实现getHeadViewHolder(ViewGroup parent)、getGridListViewHolder(ViewGroup parent)和onBind(RecyclerView.ViewHolder holder, int position)方法，
实现头布局和item列表逻辑
```
 @Override
    protected RecyclerView.ViewHolder getHeadViewHolder(ViewGroup parent) {
        return new HeadViewHolder(getLayoutView(parent, R.layout.item_head_layout));
    }

    @Override
    protected RecyclerView.ViewHolder getGridListViewHolder(ViewGroup parent) {
        return new ItemViewHolder(getLayoutView(parent, R.layout.item_view_layout));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        ....
    }
```

### 3）BaseLoadMoreRecyclerViewAdapter
a）加载更多适配器基类，如果你希望深度定制（定制加载更多/失败/完成的界面）可以直接继承改类，实现下面的方法
```
  @Override
    protected int getLoadFinishLayoutId() {
        return R.layout.item_load_finish_layout;
    }

    @Override
    protected int getLoadingMoreLayoutId() {
        return R.layout.item_loading_more_layout;
    }

    @Override
    protected int getLoadFailLayoutId() {
        return R.layout.item_load_fail_layout;
    }

    @Override
    protected void showLoadFinish(RecyclerView.ViewHolder holder) {
        .....
    }

    @Override
    protected void showLoadFail(RecyclerView.ViewHolder holder) {
        .....
    }

    @Override
    protected void showLoadingMore(RecyclerView.ViewHolder holder) {
        .....
    }
    
    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent) {
        return new ItemViewHolder(getLayoutView(parent, R.layout.item_view_layout));
    }
    
    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        .....
    }
```
b）我已经为小伙伴提供了一个简单界面实现BaseSimpleLoadMoreRecyclerViewAdapter，如果你觉得这些简单的界面已经满足你的基本需求，你可以选择直接继承这个类。
如果你希望替换这个界面的字体颜色或大小可以在继承类的构造函数中调用配置方法，我为小伙伴提供了以下几个简单的配置方法：
- 设置完成加载时的提示语：setFinishText(String text)
- 设置完成加载提示语大小：setFinishTextSizeSp(int sizeSp)
- 设置完成加载提示语颜色：setFinishTextColor(int textColor)
- 设置加载完成背景色：setFinishBackgroundColor(int backgroundColor)
- 设置正在加载提示语：setLoadingMoreText(String text)
- 设置正在加载文字大小：setLoadingMoreTextSizeSp(int sizeSp)
- 设置正在加载文字颜色：setLoadingMoreTextColor(int textColor)
- 设置正在加载背景色：setLoadingMoreBackgroundColor(int backgroundColor)
- 设置加载失败提示语：setLoadFailText(String text)
- 设置加载失败提示语大小：setLoadFailTextSizeSp(int sizeSp)
- 设置加载失败提示语文字颜色：setLoadFailTextColor(int textColor)
- 设置加载失败提示语背景大小：setLoadFailBackgroundColor(int backgroundColor)

c）加载更多适配器的使用方法很简单，结合RecyclerLoadMoreHelper帮助类来使用即可

- 初始化RecyclerView及加载帮助类，init传入的适配器必须继承BaseLoadMoreRecyclerViewAdapter。
如果要用网格布局加载更多，一定需要设置onAttachedToRecyclerView方法，线性布局则不需要
```
    private void initRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mAdapter = new RefreshAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mLoadMoreHelper = new RecyclerLoadMoreHelper<>();
        mLoadMoreHelper.init(mAdapter);
    }
```
- 设置监听器，onLoadMore(int currentPage, int nextPage, int size, int position)方法在到达加载位置时回调，
onClickLoadFail(int reloadPage, int size)方法在用户点击底部失败提示时回调
```
    loadMoreHelper.setListener(new RecyclerLoadMoreHelper.Listener() {
        @Override
        public void onLoadMore(int currentPage, int nextPage, int size, int position) {
            
        }

        @Override
        public void onClickLoadFail(int reloadPage, int size) {
            
        }
    });
```
- 获取首次数据时请调用以下方法对帮助类进行设置
```
    /**
     * 配置加载更多适配器（请在获得数据后进行初始化）
     * @param list 数据
     * @param sumSize 总条数
     * @param size 每页条数
     * @param isShowBottomLayout 是否显示底部提示界面
     * @param index 预加载偏移量，滑动到倒数第index个item时就回调加载接口（默认值为3）
     */
    loadMoreHelper.config(List<T> list, int sumSize, int size, boolean isShowBottomLayout, int index);
```
- 加载失败时请调用下面的方法，会显示加载失败页面
```
    loadMoreHelper.loadMoreFail();
```
- 加载成功获得下一页数据时可以调用下面的方法，将数据放入适配器中
```
    loadMoreHelper.loadMoreSuccess(List<T> list);
```
- 需要手动设置加载完成界面时，可以调用下面的方法
```
    loadMoreHelper.loadComplete();
```

- 需要隐藏某个item时，可以调用下面的方法，可以设置适配器的 **setOnAllItemHideListener(OnAllItemHideListener listener)** 方法来监听item全隐藏的回调
```
    loadMoreHelper.hideItem(position);
```

### 4）RecyclerViewDragHelper
该帮助类实现了RecyclerView的拖拽功能，如果小伙伴需要使用拖拽功能，可以用这个帮助来实现
- 初始化拖拽帮助类，根据自己的需要配置拖拽或侧滑
```
    RecyclerViewDragHelper<String> recyclerViewDragHelper = new RecyclerViewDragHelper<>();
    recyclerViewDragHelper
        .setUseDrag(false)// 设置是否允许拖拽
        .setUseLeftToRightSwipe(true)// 设置允许从左往右滑动
        .setUseRightToLeftSwipe(false)// 设置允许从右往左滑动
        .setEnabled(true)// 是否启用
        .setDragingColor(Color.GRAY)// 设置拖拽时的背景颜色
        .setDraggedColor(Color.RED)// 设置拖拽完成的背景颜色
        .build(mRecyclerView, mAdapter);
```
- 设置拖拽列表数据
```
    recyclerViewDragHelper.setList(mList);
```
- 设置监听器，列表在拖拽后会将当前的list顺序通过这个接口回调出来
```
    recyclerViewDragHelper.setListener(new RecyclerViewDragHelper.Listener<String>() {
        @Override
        public void onListChanged(List<String> list) {
            .....
        }
    });
```

### 5）RecyclerBinder
RecyclerBinder适用于不同类型的长页面，或者需要根据数据展示部分模块的滚动页面。
通过用Binder解耦不同模块，使复杂的逻辑分块处理而不是杂糅在一起
a）使用BaseRecyclerViewBinderAdapter适配器来存放Binder，BaseRecyclerViewBinderAdapter提供了下面几个方法来对Binder进行控制
- 添加一个RecyclerBinder
```
    addBinder(RecyclerBinder binder)
```
- 根据Binder的type来删除对应的Binder
```
    removeBinder(int viewType)
```
- 清除所有的Binder
```
    clearBinder()
```
b）使用自定义的TestBinder继承RecyclerBinder，如下图所示：
- 构造函数传入的binderType在同一个BaseRecyclerViewBinderAdapter里要保证唯一性，否则会出现冲突。
- 重写getItemCount()方法可以指明这个Binder占多少个item，如果只占1个item则直接返回1，如果数据进来的是一个列表则可以返回列表的长度。
- 其他使用方法与适配器保持一致
```
    public class TestBinder extends RecyclerBinder<String>{
        
        public TestBinder(Context context, int binderType) {
            super(context, binderType);
        }
    
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
            return null;
        }
    
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    
        }
    
        @Override
        public String getData(int position) {
            return null;
        }
    
        @Override
        public int getItemCount() {
            return 0;
        }
    }
```

## 7、Dialog相关
### 1）BaseDialog
a）BaseDialog继承自Dialog，小伙伴继承BaseDialog后可以实现下面两个方法，分别传入布局layout和获取控件id
```
    @Override
    protected int getLayoutId() {
        return dialog_view_layout;
    }

    @Override
    protected void findViews() {
        .....
    }
```
b）重写设置监听器和设置数据方法，可以在里面设置控件的监听器和初始化数据
```
    protected void setListeners() {}
    
    protected void initData() {}
```

### 2）BaseRightDialog
从右侧滑出的Dialog，使用方法和BaseDialog一致

### 3）BaseBottomDialog
从底部滑出的Dialog，使用方法和BaseDialog一致

## 8、自定义widget
### 1）MmsTabLayout
自定义的TabLayout，可以通过设置下面的方法来控制底线的宽度
```
    setTabIndicatorMargin(int leftDp, int rightDp)
```

### 2）NoScrollViewPager
自定义的ViewPager，默认用户通过滑动来切换，如果需要动态设置滑动拦截，可以调用下面的方法
```
    setScroll(boolean isScroll)
```

## 9、MVP相关
### 1）基础的Activity实现
- 定义一个接口 **VC** 继承 **ViewContract** ，在 **VC** 中定义你的UI更新接口
- 定义一个类 **PC** 继承 **AbsPresenter< VC >** ，在 **PC** 中实现你的业务逻辑方法
- 定义一个 **Activity** 继承 **MvpAbsActivity<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestActivity extends MvpAbsActivity<PC, VC> implements VC
```
- 在 **AbsPresenter** 里面已经为小伙伴们实现了 **onCreate()、onPause()、onResume()、onResume()、onDestroy()** 这些生命周期的回调，大家只需要重写该方法就可以了
- 在 **AbsPresenter** 里面可以直接调用 **getViewContract()** 方法获取你定义的 **VC** 接口对象，对UI进行更新
- 在 **AbsPresenter** 里需要用到 **Context** 的话可以直接调用 **getContext()** 获取
- 在 **AbsPresenter** 内回调 **onDestroy()** 时，会将 **VC** 对象和 **Context** 对象置空，请勿在 **onDestroy()** 内使用这两个对象的方法
- 继承 **MvpAbsActivity<PC, VC>** 后需要实现 **createMainPresenter()** 方法，创建对应的 **PC** 对象

### 2）基础的Fragment实现
- 定义一个 **Fragment** 继承 **MvpLazyFragment<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestFragment extends MvpLazyFragment<PC, VC> implements VC
```
- 其余使用方式同：[基础的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#1基础的Activity实现)

### 3）带基础控件的Activity实现
- 定义一个接口 **VC** 继承 **BaseViewContract** ，在 **VC** 中定义你的UI更新接口
- 定义一个类 **PC** 继承 **BasePresenter< VC >** ，在 **PC** 中实现你的业务逻辑方法
- 定义一个 **Activity** 继承 **MvpBaseActivity<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestActivity extends MvpBaseActivity<PC, VC> implements VC
```
- **BaseViewContract** 里面已经定义了基础控件的调用方法，小伙伴们可以直接使用
- **BasePresenter** 继承自 **AbsPresenter** ，里面实现了 **clickBackBtn()、clickReload()** 方法，有需要在这两个方法内处理业务逻辑的小伙伴直接重写就OK了
- 其余使用方式同：[基础的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#1基础的Activity实现)

### 4）带基础控件的Fragment实现
- 定义一个 **Fragment** 继承 **MvpBaseFragment<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestFragment extends MvpBaseFragment<PC, VC> implements VC
```
- 其余使用方式同：[带基础控件的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#3带基础控件的Activity实现)

### 5）带基础控件和刷新控件的Activity实现
- 定义一个接口 **VC** 继承 **BaseRefreshViewContract** ，在 **VC** 中定义你的UI更新接口
- 定义一个类 **PC** 继承 **BaseRefreshPresenter< VC >** ，在 **PC** 中实现你的业务逻辑方法
- 定义一个 **Activity** 继承 **MvpBaseRefreshActivity<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestActivity extends MvpBaseRefreshActivity<PC, VC> implements VC
```
- **BaseRefreshViewContract** 继承自 **BaseViewContract** ，除了基础控件的调用方法外，还多了 **setSwipeRefreshFinish()** 和 **setSwipeRefreshEnabled()** 这两个控制刷新控件的方法，一样可以直接调用
- **BaseRefreshPresenter** 继承自 **BasePresenter** ，里面多了 **onDataRefresh()** 方法，小伙伴可以重写该方法，然后实现下拉刷新的业务逻辑
- 其余使用方式同：[带基础控件的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#3带基础控件的Activity实现)

### 6）带基础控件和刷新控件的Fragment实现
- 定义一个 **Fragment** 继承 **MvpBaseRefreshFragment<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestFragment extends MvpBaseRefreshFragment<PC, VC> implements VC
```
- 其余使用方式同：[带基础控件和刷新控件的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#5带基础控件和刷新控件的Activity实现)

## 扩展

- [更新记录](https://github.com/LZ9/AgileDev/blob/master/component/readme_component_update.md)
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#component库)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 core](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)

