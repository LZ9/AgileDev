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
 - [9、MVP相关](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#9mvp相关)
 - [10、PopupWindow基类](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#10popupwindow基类)
 - [11、图片选择和预览](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#11图片选择和预览)
 - [12、侧滑菜单](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#12侧滑菜单)
 - [扩展](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#扩展)

## 1、涉及的依赖
该库已经引用了core、Rxjava2、Retrofit2、Rxlifecycle2以及Eventbus3.0，小伙伴不需要再重复引用，我会定期关注并更新版本，基本保证与最新版本一致
```
    dependencies {
        compile 'cn.lodz:core:1.1.8'
    
        compile 'io.reactivex.rxjava2:rxjava:2.1.10'
        compile 'io.reactivex.rxjava2:rxandroid:2.0.2'
        
        compile 'com.squareup.retrofit2:retrofit:2.4.0'
        compile 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'
        
        compile 'com.alibaba:fastjson:1.2.46'
        
        compile 'com.trello.rxlifecycle2:rxlifecycle-android:2.2.1'
        compile 'com.trello.rxlifecycle2:rxlifecycle-components:2.2.1'
        
        compile 'org.greenrobot:eventbus:3.1.1'
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
a）异步线程发起主线程订阅的方法
```
    .compose(RxUtils.<T>ioToMainObservable())
    .compose(RxUtils.<T>ioToMainFlowable())
```
b）在订阅者的onError中去获取提示语
```
    RxUtils.getExceptionTips(throwable, isNetwork, defaultTips)
    RxUtils.getNetworkExceptionTips(throwable, isNetwork, defaultTips)//只获取网络提示语
```
c）把图片路径转为base64
```
    Observable<String> decodePathToBase64(final String path, final int widthPx, final int heightPx)//转单张
    Observable<List<String>> decodePathToBase64(final List<String> paths, final int widthPx, final int heightPx)//转多张
```
d）控件防抖点击
```
    Observable<View> viewClick(View view)
    Observable<View> viewClick(View view, long windowDuration, TimeUnit unit)
```
e）文本变动
```
    Observable<CharSequence> textChanges(TextView textView)
```

## 6、RecyclerView相关
### 1）BaseRecyclerViewAdapter
a）这个是RecyclerView适配器的基类adapter，继承这个基类，
实现onCreateViewHolder(ViewGroup parent, int viewType)
和onBind(RecyclerView.ViewHolder holder, int position)两个抽象方法
```
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(getLayoutView(parent, R.layout.xxx));
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        ....
    }
```

b）适配器内集成了动画开关和动画配置方法，可以根据自己的需要来配置动画
```
    adapter.setOpenItemAnim(isOpen);//开启动画
    adapter.setItemAnimStartPosition(n);//设置动画起始位置
    adapter.setAnimationType(@AnimationType);//设置已有的动画类型
    adapter.setBaseAnimation(BaseAnimation);// 设置自定义动画
```

### 2）BaseHeaderFooterRVAdapter
a）带头布局和底布局的RecyclerView适配器基类，继承这个基类，
实现getHeaderViewHolder(ViewGroup parent)、getItemViewHolder(ViewGroup parent)、
getFooterViewHolder(ViewGroup parent)和onBind(RecyclerView.ViewHolder holder, int position)
方法
```
    @Override
    protected RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup parent) {
        return new HeadViewHolder(getLayoutView(parent, R.layout.xxx));
    }
    
    @Override
    protected RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent) {
        return new ItemViewHolder(getLayoutView(parent, R.layout.xxx));
    }
    
    @Override
    protected RecyclerView.ViewHolder getFooterViewHolder(ViewGroup parent) {
        return new FooterViewHolder(getLayoutView(parent, R.layout.xxx));
    }
    
    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        ....
    }
```

b）假设你只需要头布局不需要底布局，那你在getFooterViewHolder的方法里返回null即可
```
    @Override
    protected RecyclerView.ViewHolder getFooterViewHolder(ViewGroup parent) {
        return null;
    }
```

### 3）BaseLoadMoreRVAdapter
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
b）我已经为小伙伴提供了一个简单界面实现SimpleLoadMoreRVAdapter，如果你觉得这些简单的界面已经满足你的基本需求，你可以选择直接继承这个类。
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

- 初始化RecyclerView及加载帮助类，init传入的适配器必须继承BaseLoadMoreRVAdapter。
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
        .setUseDrag(true)// 设置是否允许拖拽
        .setLongPressDragEnabled(true)// 是否启用长按拖拽效果
        .setUseLeftToRightSwipe(true)// 设置允许从左往右滑动
        .setUseRightToLeftSwipe(true)// 设置允许从右往左滑动
        .setSwipeEnabled(false)// 设置是否允许滑动
        .build(recyclerView, adapter);
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
b）使用自定义的TestBinder继承RecyclerBinder，如下所示：
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

### 6）装饰器ItemDecoration
#### a)装饰器基类BaseItemDecoration
自定义装饰器时可以继承该基类

#### b)外围分割线装饰器RoundItemDecoration
该装饰器可以独立在item四周添加对应颜色的分割线，具体使用方法
- 独立设置四周分割线，例如setTopDividerRes(dp,  color, bgColor, lrPaddingDp)，
 1. dp是分割线宽度（单位dp）
 2. color为分割线颜色
 3. bgColor为背景颜色（如果有设置lrPaddingDp时建议设置，不需要传0即可）
 4. lrPaddingDp是分割线与item两侧的间距
```
    RoundItemDecoration.create(getContext())
        .setTopDividerRes(1, R.color.black, R.color.white, 15)
        .setBottomDividerRes(1, R.color.black, R.color.white, 15)
        .setLeftDividerRes(1, R.color.black, R.color.white, 5)
        .setRightDividerRes(1, R.color.black, R.color.white, 5);
```
- 我增加了快速配置底部分割线的方法，如果只需要配置底部分割线可以调用
```
    RoundItemDecoration.createBottomDivider(getContext(), 1, R.color.black, 0, 0);
```

#### c)网格分割线装饰器GridItemDecoration
1)该装饰器能在item四周加上均匀的网格分割线，具体使用方法
```
    GridItemDecoration.createDividerRes(context, dp, color)
    GridItemDecoration.createDividerInt(context, dp, color)
```
- dp是分割线宽度（单位dp）
- color为分割线颜色

## 7、Dialog相关
### 1）BaseDialog
a）BaseDialog继承自Dialog，小伙伴继承BaseDialog后可以实现下面两个方法，分别传入布局layout和获取控件id
```
    @Override
    protected int getLayoutId() {
        return R.layout.xxx;
    }

    @Override
    protected void findViews() {
        .....
    }
    
```
b）重写设置监听器和设置数据方法，可以在里面设置控件的监听器和初始化数据
```
    @Override
    protected void setListeners() {
        .....
    }
    
    @Override 
    protected void initData() {
        .....    
    }
```
c）初始化的代码都写在构造函数内，如果你的数据是通过构造函数传入，请务必在super()方法之后再将数据赋值给UI，避免不必要的异常

### 2）BaseRightDialog
从右侧滑出的Dialog，使用方法和BaseDialog一致

### 3）BaseBottomDialog
从底部滑出的Dialog，使用方法和BaseDialog一致

### 4）BaseCenterDialog
从中间缩放显示的Dialog，使用方法和BaseDialog一致

### 5）BaseLeftDialog
从左侧滑出的Dialog，使用方法和BaseDialog一致

### 6）BaseTopDialog
从顶部滑出的Dialog，使用方法和BaseDialog一致

### 7）ProgressDialogHelper
一个加载框的帮助类，帮助你快速获取一个加载框，可以通过不同的创建函数来进行简单的订制

## 8、自定义widget
### 1）MmsTabLayout
- 自定义的TabLayout，可以通过设置下面的方法来控制底线的宽度
```
    setTabIndicatorMargin(int leftDp, int rightDp)
```
- xml调用方法
```
    <com.lodz.android.component.widget.MmsTabLayout
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       app:tabIndicatorColor="@color/color_00a0e9"
       app:tabIndicatorHeight="2dp"
       app:tabMargin="20dp"
       app:tabMode="fixed"
       app:tabTextAppearance="@style/TabLayoutTextStyle"
       app:tabSelectedTextColor="@color/color_00a0e9"
       app:tabTextColor="@color/black">
```
- 自定义属性列表

属性|描述|参数用例
:---|:---|:---
app:tabMargin|设置每个tab的左右间距|10dp

### 2）NoScrollViewPager
自定义的ViewPager，默认用户不能通过滑动来切换，可以通过下面的方法来设置是否滑动
```
    setScroll(boolean isScroll)
```
- xml调用方法
```
    <com.lodz.android.component.widget.NoScrollViewPager
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    
    </com.lodz.android.component.widget.NoScrollViewPager>
```

### 3）PhotoView
由于引用还需要配置maven地址，所以就直接把view集成进来了，有需要用到的可以直接调用

### 4）TitleBarLayout
- 自定义的标题栏TitleBarLayout，可以通过下面的方法来订制
```
    // 是否需要显示返回按钮
    needBackButton(boolean isNeed)
    // 请重写实现返回按钮监听
    setOnBackBtnClickListener(OnClickListener listener)
    // 替换默认的返回按钮
    replaceBackBtn(View view)
    // 设置返回按钮文字
    setBackBtnName(String str)
    // 设置返回按钮文字
    setBackBtnName(@StringRes int strResId)
    // 设置返回按钮文字颜色
    setBackBtnTextColor(@ColorRes int colorRes)
    // 设置返回按钮文字颜色
    setBackBtnTextColor(ColorStateList colorStateList)
    // 设置返回按钮文字大小
    setBackBtnTextSize(float size)
    // 设置标题名
    setTitleName(String title)
    // 设置标题名
    setTitleName(@StringRes int strResId)
    // 设置标题文字颜色
    setTitleTextColor(@ColorRes int colorRes)
    // 设置文字颜色
    setTitleTextColor(ColorStateList colorStateList)
    // 设置标题文字大小
    setTitleTextSize(float size)
    // 需要右侧扩展区
    needExpandView(boolean isNeed)
    // 添加扩展区域的View
    addExpandView(View view)
    // 获取扩展区域的View
    getExpandView()
    // 隐藏分割线
    goneDivideLine()
    // 设置分割线颜色
    setDivideLineColor(@ColorRes int colorRes)
    // 设置分割线颜色
    setDivideLineColor(Drawable drawable)
    // 设置分割线高度
    setDivideLineHeight(float height)
```

- xml调用方法
```
    <com.lodz.android.component.widget.base.TitleBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:backDrawable="@drawable/ic_launcher"
        app:backText="返回"
        app:backTextColor="@color/white"
        app:backTextSize="16sp"
        app:divideLineColor="@color/color_d28928"
        app:divideLineHeight="10dp"
        app:elevationVale="15dp"
        app:expandViewId="@layout/view_config_expand_layout"
        app:isNeedBackBtn="true"
        app:isNeedElevation="true"
        app:isNeedExpandView="false"
        app:isShowDivideLine="false"
        app:titleBarBackground="@color/color_ea8380"
        app:titleText="测试"
        app:titleTextColor="@color/color_00a0e9"
        app:titleTextSize="15sp">

    </com.lodz.android.component.widget.base.TitleBarLayout>
```

- 自定义属性列表

属性|描述|参数用例
:---|:---|:---
app:isNeedBackBtn|是否需要显示返回按钮|true
app:backDrawable|返回按钮的资源图片|@drawable/ic_launcher
app:backText|返回按钮文字|@string/test
app:backTextColor|返回按钮文字颜色|@color/white
app:backTextSize|返回按钮文字大小|16sp
app:titleText|标题文字|@string/test
app:titleTextColor|标题文字颜色|@color/color_00a0e9
app:titleTextSize|标题文字大小|15sp
app:isNeedElevation|是否需要阴影|true
app:elevationVale|显示阴影|6dp
app:isShowDivideLine|是否显示底部分割线|false
app:divideLineColor|底部分割线颜色|@color/color_d28928
app:divideLineHeight|底部分割线高度|10dp
app:titleBarBackground|标题栏背景色|@color/color_ea8380
app:isNeedExpandView|是否需要右侧扩展布局|false
app:expandViewId|右侧扩展布局资源id|@layout/view_expand_layout

### 5）LoadingLayout
- 数据加载页LoadingLayout，可以通过下面的方法来订制
```
    // 需要提示文字
    needTips(boolean isNeed)
    // 设置提示文字
    setTips(String str)
    // 设置提示文字
    setTips(@StringRes int strResId)
    // 设置文字颜色
    setTipsTextColor(@ColorRes int colorRes)
    // 设置文字颜色
    setTipsTextColor(ColorStateList colorStateList)
    // 设置文字大小
    setTipsTextSize(float size)
    // 设置进度条控件
    setProgressBar(@NonNull ProgressBar progressBar)
    // 获取进度条控件
    getProgressBar()
    // 设置加载页面的布局方向
    setLayoutOrientation(@BaseLayoutConfig.OrientationType int orientation)
```

- xml调用方法
```
    <com.lodz.android.component.widget.base.LoadingLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone"
        app:contentBackground="@color/color_ff4081"
        app:contentOrientation="horizontal"
        app:indeterminateDrawable="@drawable/anims_custom_progress"
        app:isIndeterminate="true"
        app:isNeedTips="true"
        app:pbHeight="50dp"
        app:pbWidth="50dp"
        app:tips="@string/config_base_loading_tips"
        app:tipsColor="@color/white"
        app:tipsSize="15sp">
    
    </com.lodz.android.component.widget.base.LoadingLayout>

```

- 自定义属性列表

属性|描述|参数用例
:---|:---|:---
app:contentOrientation|内容的方向|horizontal
app:contentBackground|内容的背景色|@color/color_ff4081
app:isNeedTips|是否需要展示提示语|true
app:tips|提示语文字|@string/tips
app:tipsColor|提示语文字颜色|@color/white
app:tipsSize|提示语文字大小|15sp
app:indeterminateDrawable|进度动画资源|@drawable/anims_custom_progress
app:isIndeterminate|是否持续进度|true
app:pbHeight|进度条的高度|50dp
app:pbWidth|进度条的宽度|50dp

### 6）NoDataLayout
- 无数据页面NoDataLayout，可以通过下面的方法来订制
```
    // 需要提示图片
    needImg(boolean isNeed)
    // 需要提示文字
    needTips(boolean isNeed)
    // 设置无数据图片
    setImg(@DrawableRes int drawableResId)
    // 设置无数据图片
    setImg(Drawable drawable)
    // 设置提示文字
    setTips(String str)
    // 设置提示文字
    setTips(@StringRes int strResId)
    // 设置文字颜色
    setTipsTextColor(@ColorRes int colorRes)
    // 设置文字颜色
    setTipsTextColor(ColorStateList colorStateList)
    // 设置文字大小
    setTipsTextSize(float size)
    // 设置无数据页面的布局方向
    setLayoutOrientation(@BaseLayoutConfig.OrientationType int orientation)
```

- xml调用方法
```
<com.lodz.android.component.widget.base.NoDataLayout
     android:id="@+id/no_data_layout"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:visibility="gone"
     app:contentBackground="@color/color_ea8380"
     app:contentOrientation="horizontal"
     app:isNeedImg="true"
     app:isNeedTips="true"
     app:src="@drawable/ic_launcher"
     app:tips="@string/config_base_no_data_tips"
     app:tipsColor="@color/color_ffa630"
     app:tipsSize="22sp">

</com.lodz.android.component.widget.base.NoDataLayout>
```

- 自定义属性列表

属性|描述|参数用例
:---|:---|:---
app:isNeedImg|是否需要图片|true
app:src|资源图片|@drawable/ic_launcher
app:isNeedTips|是否需要提示语|true
app:tips|提示语文字|@string/tips
app:tipsColor|提示语文字颜色|@color/color_ffa630
app:tipsSize|提示语文字大小|22sp
app:contentOrientation|内容的方向|horizontal
app:contentBackground|内容背景色|@color/color_ea8380


### 7）ErrorLayout
- 错误页面NoDataLayout，可以通过下面的方法来订制
```
    // 需要提示图片
    needImg(boolean isNeed)
    // 需要提示文字
    needTips(boolean isNeed)
    // 设置界面错误图片
    setImg(@DrawableRes int drawableResId)
    // 设置无数据图片
    setImg(Drawable drawable)
    // 设置提示文字
    setTips(String str)
    // 设置提示文字
    setTips(@StringRes int strResId)
    // 设置文字颜色
    setTipsTextColor(@ColorRes int colorRes)
    // 设置文字颜色
    setTipsTextColor(ColorStateList colorStateList)
    // 设置文字大小
    setTipsTextSize(float size)
    // 设置重载监听器
    setReloadListener(OnClickListener listener)
    // 设置错误页面的布局方向
    setLayoutOrientation(int orientation)
```

- xml调用方法
```
    <com.lodz.android.component.widget.base.ErrorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone"
        app:contentBackground="@color/color_ffa630"
        app:contentOrientation="vertical"
        app:isNeedImg="true"
        app:isNeedTips="true"
        app:src="@drawable/ic_launcher"
        app:tips="@string/config_base_fail_tips"
        app:tipsColor="@color/color_ea413c"
        app:tipsSize="18sp">
    
    </com.lodz.android.component.widget.base.ErrorLayout>
```

- 自定义属性列表

属性|描述|参数用例
:---|:---|:---
app:isNeedImg|是否需要图片|true
app:src|资源图片|@drawable/ic_launcher
app:contentOrientation|内容的方向|horizontal
app:contentBackground|内容背景色|@color/color_ea8380
app:isNeedTips|是否需要提示语|true
app:tips|提示语文字|@string/tips
app:tipsColor|提示语文字颜色|@color/color_ffa630
app:tipsSize|提示语文字大小|22sp

### 8）九宫格
#### <1>NineGridView
- 九宫格NineGridView，可以通过下面的方法来订制
```
    // 设置是否需要添加图标
    setNeedAddBtn(boolean needAddBtn)}
    // 设置添加图标
    setAddBtnDrawable(@DrawableRes int resId)
    // 设置是否显示删除按钮
    setShowDelete(boolean isShowDelete)
    // 设置删除图标
    setDeleteBtnDrawable(@DrawableRes int resId) 
    // 设置最大图片数
    setMaxPic(@IntRange(from = 1) int count)
    // 设置数据
    setData(@NonNull List<String> data)
    // 添加数据
    addData(@NonNull List<String> data)
    // 删除数据
    removeData(int position) 
    // 设置监听器
    setOnNineGridViewListener(OnNineGridViewListener listener)
    // 是否允许拖拽
    setNeedDrag(boolean isNeedDrag)
    // 是否允许拖拽震动提醒
    setNeedDragVibrate(boolean isNeedDragVibrate)
    // 获取图片数据
    getPicData()
```

- xml调用方法
```
    <com.lodz.android.component.widget.ninegrid.NineGridView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:isNeedAddBtn="true"
        app:isNeedDrag="false"
        app:isNeedDragVibrate="false"
        app:isShowDeleteBtn="true"
        app:itemHigh="100dp"
        app:maxPic="9"
        app:spanCount="3">

    </com.lodz.android.component.widget.ninegrid.NineGridView>
```

- 自定义属性列表

属性|描述|参数用例
:---|:---|:---
app:isNeedAddBtn|是否需要添加图片按钮|true
app:addBtnDrawable|资源图片|@drawable/ic_launcher
app:isShowDeleteBtn|是否显示删除图标|true
app:deleteDrawable|删除图标资源图片|@drawable/ic_launcher
app:maxPic|最大展示数量|9
app:spanCount|最大列数|3
app:itemHigh|图片的高度|100dp
app:isNeedDrag|是否允许拖拽|true
app:isNeedDragVibrate|是否允许拖拽震动提示|true

#### <2>SimpleNineGridView
- 简单实现的九宫格SimpleNineGridView，可以通过下面的方法来订制
```
    // 配置数据
    config(@NonNull String savePath, @NonNull String authority)
    // 设置监听器
    setOnSimpleNineGridViewListener(OnSimpleNineGridViewListener listener)
 ```
- 以下的方法已经在控件内部进行了实现，不建议外部再调用使用
```
    @Deprecated
    addData(@NonNull List<String> data)
    @Deprecated
    removeData(int position)
    @Deprecated
    setOnNineGridViewListener(OnNineGridViewListener listener)
```

- xml调用方法
```
    <com.lodz.android.component.widget.ninegrid.SimpleNineGridView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:isNeedAddBtn="true"
        app:isNeedDrag="true"
        app:isNeedDragVibrate="true"
        app:isShowDeleteBtn="true"
        app:itemHigh="100dp"
        app:maxPic="9"
        app:spanCount="3">

    </com.lodz.android.component.widget.ninegrid.SimpleNineGridView>
```

- 自定义属性列表与NineGridView一致

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
- 其余使用方式同：[基础的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#1基础的activity实现)

### 3）带基础控件的Activity实现
- 定义一个接口 **VC** 继承 **BaseViewContract** ，在 **VC** 中定义你的UI更新接口
- 定义一个类 **PC** 继承 **BasePresenter< VC >** ，在 **PC** 中实现你的业务逻辑方法
- 定义一个 **Activity** 继承 **MvpBaseActivity<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestActivity extends MvpBaseActivity<PC, VC> implements VC
```
- **BaseViewContract** 里面已经定义了基础控件的调用方法，小伙伴们可以直接使用
- **BasePresenter** 继承自 **AbsPresenter** ，里面实现了 **clickBackBtn()、clickReload()** 方法，有需要在这两个方法内处理业务逻辑的小伙伴直接重写就OK了
- 其余使用方式同：[基础的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#1基础的activity实现)

### 4）带基础控件的Fragment实现
- 定义一个 **Fragment** 继承 **MvpBaseFragment<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestFragment extends MvpBaseFragment<PC, VC> implements VC
```
- 其余使用方式同：[带基础控件的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#3带基础控件的activity实现)

### 5）带基础控件和刷新控件的Activity实现
- 定义一个接口 **VC** 继承 **BaseRefreshViewContract** ，在 **VC** 中定义你的UI更新接口
- 定义一个类 **PC** 继承 **BaseRefreshPresenter< VC >** ，在 **PC** 中实现你的业务逻辑方法
- 定义一个 **Activity** 继承 **MvpBaseRefreshActivity<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestActivity extends MvpBaseRefreshActivity<PC, VC> implements VC
```
- **BaseRefreshViewContract** 继承自 **BaseViewContract** ，除了基础控件的调用方法外，还多了 **setSwipeRefreshFinish()** 和 **setSwipeRefreshEnabled()** 这两个控制刷新控件的方法，一样可以直接调用
- **BaseRefreshPresenter** 继承自 **BasePresenter** ，里面多了 **onDataRefresh()** 方法，小伙伴可以重写该方法，然后实现下拉刷新的业务逻辑
- 其余使用方式同：[带基础控件的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#3带基础控件的activity实现)

### 6）带基础控件和刷新控件的Fragment实现
- 定义一个 **Fragment** 继承 **MvpBaseRefreshFragment<PC, VC>** ，实现 **VC** 接口，如下
```
public class TestFragment extends MvpBaseRefreshFragment<PC, VC> implements VC
```
- 其余使用方式同：[带基础控件和刷新控件的Activity实现](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#5带基础控件和刷新控件的activity实现)

## 10、PopupWindow基类
### 1）基础使用
a）创建一个类继承BasePopupWindow，实现下面两个方法，分别传入布局layout和获取控件id
```
    @Override
    protected int getLayoutId() {
        return R.layout.xxx;
    }

    @Override
    protected void findViews(View view) {
        .....
    }
    
```
b）重写设置监听器和设置数据方法，可以在里面设置控件的监听器和初始化数据
```
    @Override
    protected void setListeners() {
        .....
    }
    
    @Override 
    protected void initData() {
        .....
    }
```
### 2）进阶使用
a）重写下面方法可以自己设置宽高
```
    @Override 
    protected int getWidth(){
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override 
    protected int getHeight(){
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
```
b）重写下面方法可以自己设置阴影（需要5.0以上）
```
    @Override 
    protected float getElevationValue(){
        return 12f;
    }
```
c）如果你需要使用PopupWindow的方法，请调用下面的方法，他会返回你创建的PopupWindow对象
```
    public PopupWindow getPopup();
```
### 3）创建调用
```
    XxxPopupWindow popupWindow = new XxxPopupWindow(context);
    popupWindow.getPopup().showAsDropDown(view, xoff, yoff);
```

## 11、图片选择和预览
### 1）感谢
我在 **YancyYe** 的开源项目 **ImageSelector** 中获得了灵感，解决了如何让开发者使用自定义图片加载器的问题，并且扩展了对UI的配置。
如果小伙伴有兴趣了解和关注 **ImageSelector** 这个开源项目可以[点我](https://github.com/YancyYe/ImageSelector)查看。

### 2）图片预览器
#### 构造方法：
```
    PreviewManager
        .<String>create()//创建构造器<>里的泛型一定要指定
        .setPosition(0)//设置进入展示的位置（从0开始）
        .setPageLimit(2)//设置ViewPager的缓存数
        .setScale(false)//是否允许图片缩放
        .setBackgroundColor(R.color.black)//配置背景色
        .setStatusBarColor(R.color.black)//配置状态栏颜色（sdk >= 5.0）
        .setNavigationBarColor(R.color.black)//配置导航栏颜色（sdk >= 5.0）
        .setPagerTextColor(R.color.white)//设置底部页码文字的颜色
        .setPagerTextSize(14)//设置底部页码文字的大小
        .setShowPagerText(true)//是否显示底部页码
        .setOnClickListener(new OnClickListener<String>() {//设置点击回调
            @Override
            public void onClick(Context context, String source, int position, PreviewController controller) {
                controller.close();//关闭预览器
            }
        })
        .setOnLongClickListener(new OnLongClickListener<String>() {//设置长按回调
            @Override
            public void onLongClick(Context context, String source, int position, PreviewController controller) {
                //如果有需要你可以弹框什么的
            }
        })
        .setImgLoader(new PhotoLoader<String>() {//设置图片加载器
            @Override
            public void displayImg(Context context, String source, ImageView imageView) {
                //可以使用你喜欢的图片加载库
            }
        })
        .build(URLS)//URLS可以是图片的list也可以是单张图片的地址，但是类型需要和create的泛型保持一致
        .open(getContext());// 打开预览器
```
 - 图片加载器必须设置
 - 图片数据列表长度不为0
 - 如果只有一张图片是不会显示页码的
 - 如果你设置的position大于数据列表的长度，默认会从第一张开始播放

### 3）图片选择器
#### a）构造方法：
```
    PickerManager
        .create()//创建构造器
        .setImgLoader(new PhotoLoader<String>() {//设置选择器的图片加载器
            @Override
            public void displayImg(Context context, String source, ImageView imageView) {
                //可以使用你喜欢的图片加载库
            }
        })
        .setPreviewImgLoader(new PhotoLoader<String>() {//设置预览器的图片加载器
            @Override
            public void displayImg(Context context, String source, ImageView imageView) {
                //可以使用你喜欢的图片加载库
            }
        })
        .setOnPhotoPickerListener(new OnPhotoPickerListener() {// 设置图片选择回调
            @Override
            public void onPickerSelected(List<String> photos) {
                //在这里获取用户选择的图片数据
            }
        })
        .setMaxCount(9)//图片最大可选数（n > 0）
        .setNeedCamera(true)// 设置是否展示拍照按钮
        .setNeedItemPreview(true)// 设置是否需要item点击预览
        .setCameraSavePath(path)//设置照片存储地址
        .setAuthority("packageName.fileprovider")// 设置你存储地址的fileprovider名称
        .setPickerUIConfig(PickerUIConfig.createDefault())//设置选择库的UI风格
        .build()//如果不传参则选择用户手机的图片库，如果传图片列表则让用户选择当前的图片列表
        .open(getContext());
```
 - 选择器的图片加载器必须设置
 - 预览器的图片加载器如果不设置会默认使用选择器的图片加载器
 - 如果用户手机没有图片，会toast提示：**您没有可以选择的图片**
 - 如果你传入build()方法里的列表长度为0，也会toast提示：**您没有可以选择的图片**
 - 我会对你传入build()方法里的数据列表进行去重
 - 如果你决定让用户选择你指定的图片，那么他是无法使用拍照功能的
 - 如果你未设置存储地址，我会将拍照的图片默认保存到DCIM文件夹里
 - 如果你未设置UI风格，选择器会使用默认的UI风格
 - 如果当前系统是7.0以上且没有配置FileProvider的名称，会toast提示：**您尚未配置FileProvider**
 
#### b）选择器UI订制
我为小伙伴们提供了丰富的UI订制方法，大家可以根据需要配置符合自己应用UI风格的选择器
```
    PickerUIConfig config = PickerUIConfig.createDefault()
        .setCameraImg(R.drawable.xx)//设置你自己的拍照按钮图标
        .setCameraBgColor(R.color.xx)//设置拍照按钮的背夹色
        .setItemBgColor(R.color.xx)//设置每个item的背景色
        .setBackBtnColor(R.color.xx)//设置返回按钮的颜色
        .setMainTextColor(R.color.xx)//设置主要文字的颜色
        .setMoreFolderImg(R.drawable.xx)//设置文件夹按钮的图标（默认是一个箭头）
        .setSelectedBtnUnselect(R.color.xx)//设置item上未选中标志的颜色
        .setSelectedBtnSelected(R.color.xx)//设置item上已选中标志的颜色
        .setMaskColor(R.color.xx)//设置选中后item遮罩层的颜色（建议带透明度）
        .setTopLayoutColor(R.color.xx)// 设置顶部栏的背景色
        .setBottomLayoutColor(R.color.xx)//设置底部栏的背景色
        .setPreviewBtnNormal(R.color.xx)//设置预览按钮的普通颜色
        .setPreviewBtnUnable(R.color.xx)//设置预览按钮的不可用颜色
        .setConfirmBtnNormal(R.color.xx)//设置确定按钮的普通颜色
        .setConfirmBtnPressed(R.color.xx)//设置确定按钮的按压颜色
        .setConfirmBtnUnable(R.color.xx)//设置确定按钮的不可用颜色
        .setConfirmTextNormal(R.color.xx)//设置确定文字的普通颜色
        .setConfirmTextPressed(R.color.xx)//设置确定文字的按压颜色
        .setConfirmTextUnable(R.color.xx)//设置确定文字的不可用颜色
        .setStatusBarColor(R.color.xx)//配置状态栏颜色（sdk >= 5.0）
        .setNavigationBarColor(R.color.xx)//配置导航栏颜色（sdk >= 5.0）
        .setFolderSelectColor(R.color.xx);//设置文件夹列表item的选择标志颜色
```
配置完后只需要把 **config** 放入 **setPickerUIConfig()** 方法里即可。

#### c）拍照权限
如果你需要使用拍照功能，请添加下方的权限
```
    <uses-permission android:name="android.permission.CAMERA" />
```

#### d）FileProvider配置
首先在res目录里新建一个xml的文件夹，创建一个provider_paths.xml文件（文件名你可以自定）
```
    <?xml version="1.0" encoding="utf-8"?>
    <paths xmlns:android="http://schemas.android.com/apk/res/android">
    
        <!--自定义地址-->
        <external-path
            name="app_provider_path"
            path="AgileDev" />
    
        <!--默认地址-->
        <external-path
            name="dcim_provider_path"
            path="DCIM" />
    </paths>
```
 - 自定义地址是指你要开放的文件夹路径，正常你可以填自己APP的目录，例如我测试APP的目录叫AgileDev
 - 默认地址是我将拍照照片默认存储的地方，如果你希望使用默认地址，则保留
 
然后在 **AndroidManifest.xml** 里配置FileProvider
```
    <application
        .... >
        
        ....
        
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="packageName.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
        </provider>
        
        ....
        
    </application>
```
 - android:resource=""里放你刚才在xml里创建的文件名称
 - android:authorities=""里放你自定义的FileProvider名称，正常是以你的包名命名**packageName.fileprovider**
 - 然后在选择器里配置你自定义的FileProvider名称就OK了 **setAuthority("packageName.fileprovider")**

## 12、侧滑菜单
### 1）感谢
我借鉴了 **yanzhenjie** 的开源项目 **SwipeRecyclerView**，并且基于这个开源项目的部分类进行了扩展和封装，
有兴趣了解和关注 **SwipeRecyclerView** 这个开源项目的小伙伴可以[点我](https://github.com/yanzhenjie/SwipeRecyclerView)查看。

### 2）侧滑菜单控件SwipeMenuLayout
a)直接使用SwipeMenuLayout控件时务必在布局内设置 **content_layout** 、 **left_layout** 和 **right_layout** 这几个id，
控件会根据id将布局放入对应的区域。

b）布局基本的使用方式如下：
```
    xmlns:swipe="http://schemas.android.com/apk/res-auto"
        
    <com.lodz.android.component.widget.adapter.swipe.SwipeMenuLayout
            android:layout_width="match_parent"
            android:layout_height="100dp"
            android:background="?selectableItemBackground"
            android:clickable="true"
            android:focusable="true"
            swipe:contentViewId="@+id/content_layout"
            swipe:leftViewId="@+id/left_layout"
            swipe:rightViewId="@+id/right_layout">
    
            <LinearLayout
                android:id="@id/content_layout"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
    
                ...
                
            </LinearLayout>
    
            <LinearLayout
                android:id="@id/left_layout"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
                
                ...
                   
            </LinearLayout>
    
            <LinearLayout
                android:id="@id/right_layout"
                android:layout_width="match_parent"
                android:layout_height="match_parent">
    
                ...
    
            </LinearLayout>
    
    </com.lodz.android.component.widget.adapter.swipe.SwipeMenuLayout>
```
### 3）在RecyclerView中使用SwipeMenuLayout
#### 1>使用SwipeMenuRecyclerView来替代原本的RecyclerView
 **SwipeMenuRecyclerView** 解决了内嵌 **SwipeMenuLayout** 导致的滑动冲突，使用SwipeMenuRecyclerView可以解决滑动冲突问题
#### 2>侧滑按钮适配器BaseSwipeRVAdapter<T, VH>
 - 如果你希望深度定制侧滑菜单的样式，可以继承BaseSwipeRVAdapter<T, VH>来开发
 - T为item里的数据类型，VH为继承SwipeViewHolder的ViewHolder对象
 - 重写getContentLayout()、getRightLayout()和getLeftLayout()方法将你自定义的主布局和左右菜单布局放入item，
 getContentLayout()必须要重写，getRightLayout()和getLeftLayout()根据你的需要选择性重写，有重写则有侧滑菜单，没有重写则没有
```
     @Override
     protected int getContentLayout() {
        return R.layout.content;
     }
     
     @Override
     protected int getRightLayout() {
         return R.layout.right;
     }
    
     @Override
     protected int getLeftLayout() {
        return R.layout.left;
    }
```
 - 你自定义的ViewHolder需要继承SwipeViewHolder，然后再重写bindView()方法，在方法里去获取你的控件对象。如果你使用ButterKnife，你也可以在该方法里去绑定控件。
```
    protected class DataViewHolder extends SwipeViewHolder{
 
        private View customView;
       
        public DataViewHolder(View itemView) {
            super(itemView);
        }
 
        @Override
        public void bindView(){
            super.bindView();
            customView = itemView.findViewById(R.id.custom_view);
            // ButterKnife.bind(this, itemView);
        }
    }
```
 - 重写getViewHolder()方法，new出你自定义的DataViewHolder对象，传入getSwipeItemView(parent)方法即可。
```
    @Override
    protected DataViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new DataViewHolder(getSwipeItemView(parent));
    }
```
 - 重写onBind()方法，对你的控件进行操作
```
    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        ...
    }
```
#### 3>实现基础功能的侧滑按钮适配器SimpleSwipeRVAdapter<T, VH>
 - 如果你只需要用到基础的侧滑菜单功能，可以继承SimpleSwipeRVAdapter<T, VH>来开发
 - T为item里的数据类型，VH为继承SimpleSwipeViewHolder的ViewHolder对象
 - 重写getContentLayout()方法，将内容布局放入item
```
     @Override
     protected int getContentLayout() {
        return R.layout.content;
     }
```
 - 你自定义的ViewHolder需要继承SimpleSwipeViewHolder，然后再重写bindView()方法，在方法里去获取你的控件对象。如果你使用ButterKnife，你也可以在该方法里去绑定控件。
```
    protected class DataViewHolder extends SimpleSwipeViewHolder{
 
        private View customView;
       
        protected DataViewHolder(View itemView) {
            super(itemView);
        }
 
        @Override
        protected void bindView(){
            super.bindView();
            customView = itemView.findViewById(R.id.custom_view);
            // ButterKnife.bind(this, itemView);
        }
    }
```
 - 重写getViewHolder()方法，new出你自定义的DataViewHolder对象，传入getSwipeItemView(parent)方法即可。
```
    @Override
    protected DataViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new DataViewHolder(getSwipeItemView(parent));
    }
```
 - 重写onBindContent()方法，对你的控件进行操作
```
    @Override
    protected void onBindContent(VH holder, int position) {
        ...
    }
```
 - SimpleSwipeRVAdapter提供了两个构造函数：
 
    如果你左右菜单都需要，则调用下面这个构造函数，传入左右菜单的数据列表
    > SimpleSwipeRVAdapter(Context context, List<SwipeMenuBean> leftList, List<SwipeMenuBean> rightList)

    如果你只需要一侧的菜单，则调用下面这个构造函数，传入一侧的菜单列表和左右标志位
    > SimpleSwipeRVAdapter(Context context, List<SwipeMenuBean> list, boolean isRight)

 - 构造一个菜单列表（一侧的菜单最多显示3个，如果你构造的列表数量大于3，也只会显示前三个），
 小伙伴可以根据自己的需要创建文字、图片和文字+图片的按钮类型
```
    private List<SwipeMenuBean> createMenu() {
        List<SwipeMenuBean> list = new ArrayList<>();
        // 创建一个带图片和文字的菜单按钮
        SwipeMenuBean bean1 = new SwipeMenuBean();
        bean1.id = 1; //id唯一识别这个按钮类型
        bean1.bgColor = R.color.xxx; //按钮背景色
        bean1.imgRes = R.drawable.xxx; // 图标
        bean1.text = "新增"; // 文字
        bean1.textColor = R.color.xxx; // 文字颜色
        bean1.textSizeSp = 16; // 文字大小（单位sp）
        list.add(bean1);

        // 创建一个只有图片的菜单按钮
        SwipeMenuBean bean2 = new SwipeMenuBean();
        bean2.id = 2;
        bean2.bgColor = R.color.xxx;
        bean2.imgRes = R.drawable.xxx;
        list.add(bean2);

        // 创建一个只有文字的菜单按钮
        SwipeMenuBean bean3 = new SwipeMenuBean();
        bean3.id = 3;
        bean3.bgColor = R.color.xxx;
        bean3.text = "删除";
        bean3.textColor = R.color.xxx;
        bean3.textSizeSp = 16;
        list.add(bean3);
        return list;
    }
```
 - 要监听菜单按钮的点击事件可以设置setOnMenuClickListener()方法，监听器会回调点击位置的item数据、菜单menu数据以及位置position
```
    mAdapter.setOnMenuClickListener(new SimpleSwipeRVAdapter.OnMenuClickListener<T>() {
        @Override
        public void onMenuClick(T item, SwipeMenuBean menu, int position) {
            ...
        }
    });
```

## 扩展

- [更新记录](https://github.com/LZ9/AgileDev/blob/master/component/readme_component_update.md)
- [回到顶部](https://github.com/LZ9/AgileDev/blob/master/component/readme_component.md#component库)
- [AgileDev 主页](https://github.com/LZ9/AgileDev)
- [了解 core](https://github.com/LZ9/AgileDev/blob/master/core/readme_core.md)
- [了解 imageloader](https://github.com/LZ9/AgileDev/blob/master/imageloader/readme_imageloader.md)

