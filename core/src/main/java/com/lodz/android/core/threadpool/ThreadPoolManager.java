package com.lodz.android.core.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理器
 * Created by zhouL on 2016/11/17.
 */
public class ThreadPoolManager {

    private static ThreadPoolManager mInstance;

    public static ThreadPoolManager get() {
        if (mInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }

    private ThreadPoolManager() {
    }

    /** 最高优先级线程池（界面数据加载） */
    private ExecutorService mHighestExecutor;

    /** 正常优先级线程池（下载等） */
    private ExecutorService mNormalExecutor;

    /** 最低优先级线程池（数据同步等） */
    private ExecutorService mLowestExecutor;

    /**
     * 执行一个高优先级线程
     * @param runnable 线程体
     */
    public void executeHighest(Runnable runnable){
        if(mHighestExecutor == null){
            mHighestExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createHighestPriorityThread());
        }
        mHighestExecutor.execute(runnable);
    }

    /**
     * 执行一个普通优先级线程
     * @param runnable 线程体
     */
    public void executeNormal(Runnable runnable){
        if(mNormalExecutor == null){
            mNormalExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createNormPriorityThread());
        }
        mNormalExecutor.execute(runnable);
    }

    /**
     * 执行一个低优先级线程
     * @param runnable 线程体
     */
    public void executeLowest(Runnable runnable){
        if(mLowestExecutor == null){
            mLowestExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createLowestPriorityThread());
        }
        mLowestExecutor.execute(runnable);
    }


}

