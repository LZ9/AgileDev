package com.lodz.android.core.threadpool;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

import androidx.annotation.Nullable;

/**
 * 优先级线程工厂
 * Created by zhouL on 2016/11/17.
 */

class PriorityThreadFactory {

    /** 创建一个优先级最高线程 */
    static ThreadFactory createHighestPriorityThread(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(@Nullable Runnable runnable) {
                PriorityThread thread = new PriorityThread(runnable);
                thread.setOSPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        };
    }

    /** 创建一个普通线程 */
    static ThreadFactory createNormPriorityThread(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(@Nullable Runnable runnable) {
                PriorityThread thread = new PriorityThread(runnable);
                thread.setOSPriority(Process.THREAD_PRIORITY_DEFAULT);
                thread.setPriority(Thread.NORM_PRIORITY);
                return thread;
            }
        };
    }

    /** 创建一个优先级最低线程 */
    static ThreadFactory createLowestPriorityThread(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(@Nullable Runnable runnable) {
                PriorityThread thread = new PriorityThread(runnable);
                thread.setOSPriority(Process.THREAD_PRIORITY_LOWEST);
                thread.setPriority(Thread.MIN_PRIORITY);
                return thread;
            }
        };
    }

}
