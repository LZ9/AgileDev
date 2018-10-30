package com.lodz.android.core.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.IntRange;

/**
 * 线程池管理器
 * Created by zhouL on 2016/11/17.
 */
public class ThreadPoolManager {

    private static ThreadPoolManager mInstance = new ThreadPoolManager();

    public static ThreadPoolManager get() {
        return mInstance;
    }

    /** 最高优先级线程池（界面数据加载） */
    private ExecutorService mHighestExecutor;

    /** 正常优先级线程池（下载等） */
    private ExecutorService mNormalExecutor;

    /** 最低优先级线程池（数据同步等） */
    private ExecutorService mLowestExecutor;

    /** 配置内容 */
    private Builder mBuilder;

    private ThreadPoolManager() {
        mBuilder = new Builder();
    }

    /** 重新配置 */
    public Builder newBuilder(){
        return mBuilder;
    }

    /**
     * 执行一个高优先级线程
     * @param runnable 线程体
     */
    public void executeHighest(Runnable runnable){
        if(mHighestExecutor == null){
            mHighestExecutor= new ThreadPoolExecutor(mBuilder.getCorePoolSize(), mBuilder.getMaximumPoolSize(), mBuilder.getKeepAliveTime(),
                    mBuilder.getKeepAliveTimeUnit(), new LinkedBlockingQueue<Runnable>(), PriorityThreadFactory.createHighestPriorityThread(),
                    mBuilder.getRejectedExecutionHandler());
        }
        mHighestExecutor.execute(runnable);
    }

    /**
     * 执行一个普通优先级线程
     * @param runnable 线程体
     */
    public void executeNormal(Runnable runnable){
        if(mNormalExecutor == null){
            mNormalExecutor= new ThreadPoolExecutor(mBuilder.getCorePoolSize(), mBuilder.getMaximumPoolSize(), mBuilder.getKeepAliveTime(),
                    mBuilder.getKeepAliveTimeUnit(), new LinkedBlockingQueue<Runnable>(), PriorityThreadFactory.createNormPriorityThread(),
                    mBuilder.getRejectedExecutionHandler());
        }
        mNormalExecutor.execute(runnable);
    }

    /**
     * 执行一个低优先级线程
     * @param runnable 线程体
     */
    public void executeLowest(Runnable runnable){
        if(mLowestExecutor == null){
            mLowestExecutor= new ThreadPoolExecutor(mBuilder.getCorePoolSize(), mBuilder.getMaximumPoolSize(), mBuilder.getKeepAliveTime(),
                    mBuilder.getKeepAliveTimeUnit(), new LinkedBlockingQueue<Runnable>(), PriorityThreadFactory.createLowestPriorityThread(),
                    mBuilder.getRejectedExecutionHandler());
        }
        mLowestExecutor.execute(runnable);
    }

    /** 释放所有线程池 */
    public void releaseAll(){
        releaseHighestExecutor();
        releaseNormalExecutor();
        releaseLowestExecutor();
    }

    /** 释放高优先级线程池 */
    public void releaseHighestExecutor() {
        if(mHighestExecutor == null){
            return;
        }
        shutdown(mHighestExecutor);
        mHighestExecutor = null;
    }

    /** 释放普通优先级线程池 */
    public void releaseNormalExecutor() {
        if(mNormalExecutor == null){
            return;
        }
        shutdown(mNormalExecutor);
        mNormalExecutor = null;
    }

    /** 释放低优先级线程池 */
    public void releaseLowestExecutor() {
        if(mLowestExecutor == null){
            return;
        }
        shutdown(mLowestExecutor);
        mLowestExecutor = null;
    }

    /** 结束线程池 */
    private void shutdown(ExecutorService executorService){
        try {
            // 发出结束通知
            executorService.shutdown();
            // 设置等待时间，所有的任务都结束的时候，返回TRUE
            if(!executorService.awaitTermination(mBuilder.getAwaitTime(), mBuilder.getAwaitTimeUnit())){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                executorService.shutdownNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            executorService.shutdownNow();
        }
    }

    /** 队列满时线程池的拒绝策略（自定义） */
    private class DefaultRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (executor.isShutdown()) {
                return;
            }
            //移除队头元素
            executor.getQueue().poll();
            //再尝试入队
            executor.execute(r);
        }
    }

    public class Builder {

        /** 线程数 */
        private int corePoolSize = 0;
        /** 最大线程数 */
        private int maximumPoolSize = 0;
        /** 线程数空闲时间 */
        private long keepAliveTime = 0;
        /** 线程数空闲时间单位 */
        private TimeUnit keepAliveTimeUnit;
        /** 拒绝策略 */
        private RejectedExecutionHandler handler;
        /** 线程结束等待时间 */
        private long awaitTime = 0;
        /** 线程结束等待时间单位 */
        private TimeUnit awaitTimeUnit;

        private int getCorePoolSize() {
            if (corePoolSize <= 0){
                int processors = Runtime.getRuntime().availableProcessors();
                corePoolSize = processors > 0 ? processors : 0;
            }
            return corePoolSize;
        }

        private int getMaximumPoolSize() {
            if (maximumPoolSize <= 0 || maximumPoolSize < getCorePoolSize()){
                if (getCorePoolSize() > 0){
                    maximumPoolSize = getCorePoolSize() * 2;
                    return maximumPoolSize;
                }
                maximumPoolSize = 2;
                return maximumPoolSize;
            }
            return maximumPoolSize;
        }

        private long getKeepAliveTime() {
            if (keepAliveTime == 0){
                keepAliveTime = 1;
            }
            return keepAliveTime;
        }

        private TimeUnit getKeepAliveTimeUnit() {
            if (keepAliveTimeUnit == null){
                keepAliveTimeUnit = TimeUnit.SECONDS;
            }
            return keepAliveTimeUnit;
        }

        private RejectedExecutionHandler getRejectedExecutionHandler() {
            if (handler == null){
                handler = new ThreadPoolExecutor.DiscardPolicy();
            }
            return handler;
        }

        private long getAwaitTime() {
            if (awaitTime == 0){
                awaitTime = 50;
            }
            return awaitTime;
        }

        private TimeUnit getAwaitTimeUnit() {
            if (awaitTimeUnit == null){
                awaitTimeUnit = TimeUnit.MILLISECONDS;
            }
            return awaitTimeUnit;
        }

        /** 设置线程数 */
        public Builder setCorePoolSize(@IntRange(from = 0) int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }

        /** 设置最大线程数 */
        public Builder setMaximumPoolSize(@IntRange(from = 1)int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
            return this;
        }

        /** 设置线程数空闲时间 */
        public Builder setKeepAliveTime(@IntRange(from = 0) long keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        /** 设置线程数空闲时间单位 */
        public Builder setKeepAliveTimeUnit(TimeUnit unit) {
            this.keepAliveTimeUnit = unit;
            return this;
        }

        /** 设置拒绝策略 */
        public Builder setRejectedExecutionHandler(RejectedExecutionHandler handler) {
            this.handler = handler;
            return this;
        }

        /** 设置线程结束等待时间 */
        public Builder setAwaitTime(long awaitTime) {
            this.awaitTime = awaitTime;
            return this;
        }

        /** 设置线程结束等待时间单位 */
        public Builder setAwaitTimeUnit(TimeUnit unit) {
            this.awaitTimeUnit = unit;
            return this;
        }

        /** 设置完成配置 */
        public ThreadPoolManager build(){
            return ThreadPoolManager.this;
        }
    }
}

