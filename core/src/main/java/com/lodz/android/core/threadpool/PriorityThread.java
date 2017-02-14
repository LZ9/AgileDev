package com.lodz.android.core.threadpool;

/**
 * 优先级线程
 * Created by zhouL on 2016/11/17.
 */

public class PriorityThread extends Thread{

    /** android下线程优先级  */
    private int mOSPriority = android.os.Process.THREAD_PRIORITY_DEFAULT;

    public PriorityThread(Runnable runnable) {
        super(runnable);
    }

    /**
     * 设置android下线程优先级，【-20， 19】，高优先级 到 低优先级.
     * @param priority 优先级
     */
    public void setOSPriority(int priority) {
        this.mOSPriority = priority;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(mOSPriority);
        super.run();
    }
}