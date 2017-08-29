package com.lodz.android.core.threadpool;

import android.os.Process;
import android.support.annotation.Nullable;

import java.util.concurrent.ThreadFactory;

/**
 * 优先级线程工厂
 * Created by zhouL on 2016/11/17.
 */

public class PriorityThreadFactory {

    /** 创建一个优先级最高线程 */
    public static ThreadFactory createHighestPriorityThread(){
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
    public static ThreadFactory createNormPriorityThread(){
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
    public static ThreadFactory createLowestPriorityThread(){
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
