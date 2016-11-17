package com.chenhao.musicplayer.messagemgr;

import android.os.Handler;

import java.util.ArrayList;

// by海平
public final class MessageManager {
    private static final String TAG = "MessageManager";

    // 用于notify observers
    public abstract static class Caller<T extends IObserverBase> implements Runnable {
        // 使用者请覆盖Call函数
        public abstract void call();

        // 使用者请忽略此函数
        public void run() {
            if (!silence) {
                int processNotifyID = __id.ordinal();
                ArrayList<IObserverBase> list = obLists.get(processNotifyID);
                ProcessingNotifyStack.ProcessingItem processingItem = ProcessingNotifyStack.push(processNotifyID, list.size());
                while (processingItem.pos < processingItem.total) {
                    @SuppressWarnings("unchecked")
                    T item = (T) list.get(processingItem.pos);
                    ob = item;
                    call();
                    ++processingItem.pos;
                }
                ob = null;
                ProcessingNotifyStack.pop();
            }
            notifyFinish();
        }

        // 使用者严禁修改这几个变量
        protected T ob;
        public MessageID __id = MessageID.OBSERVER_ID_RESERVE;
        public boolean __sync = false;                            // 为了尽量避免与用户参数重名，这里不遵守命名规范

        protected final void notifyFinish() {
            if (__sync) {
                synchronized (this) {
                    notify(); // 请忽略findbugs提示，notify不会发生在wait之前
                }
            }
        }
    }

    // 用于run
    public abstract static class Runner extends Caller<IObserverBase> {

        // 异步通知有时候需要一个版本号，用来响应的时候根据版本决定是否取消通知
        public Runner(int ver) {
            this();
            callVersion = ver;
        }

        // 使用者请覆盖Call函数
        public abstract void call();

        public void setSuccess(final boolean success) {
            this.success = success;
        }

        // 使用者请忽略此函数
        public final void run() {
            call();
            notifyFinish();
        }

        protected int callVersion;
        protected boolean success = true;

        // >>性能检查相关，请忽略，好想要一个hong
        public Runner() {
//			int num = count.addAndGet(1);
//			int numAlive = alive.addAndGet(1);
//			if (AppInfo.IS_DEBUG) {
//				LogMgr.d("MessageManager", "Runner num:" + num + " alive:" + numAlive);
//			}
        }

//		protected void finalize() {
//			int numAlive = alive.decrementAndGet();
//			if (AppInfo.IS_DEBUG) {
//				LogMgr.d("MessageManager", "GC,Runner alive:" + numAlive);
//			}
//		}
//
//		private static AtomicInteger	count	= new AtomicInteger(0);
//		private static AtomicInteger	alive	= new AtomicInteger(0);
        // <<
    }

    // 单例
    public static MessageManager getInstance() {
        return instance;
    }

    // 注册消息响应
    public void attachMessage(final MessageID id, final IObserverBase ob) {
        ArrayList<IObserverBase> list = obLists.get(id.ordinal());
        if (!list.contains(ob)) {
            list.add(ob);
            ProcessingNotifyStack.doAttach(id.ordinal());
        } else {
        }
    }

    // 取消注册
    public void detachMessage(final MessageID id, final IObserverBase ob) {
        ArrayList<IObserverBase> list = obLists.get(id.ordinal());
        int size = list.size();
        for (int i = 0; i < size; ++i) {
            IObserverBase item = list.get(i);
            if (item == ob) {
                list.remove(ob);
                ProcessingNotifyStack.doDetach(id.ordinal(), i);
                return;
            }
        }
    }

    // 同步
    public <T extends IObserverBase> void syncNotify(final MessageID id, Caller<T> r) {
//        if (App.isExiting()) {
//            //KwDebug.classicAssert(false, "模块release函数不允许发通知了，需要的话应该在IAppObserver_PrepareExitApp里做");
//            return;
//        }
        r.__id = id;
        syncRunTargetHandler(mainThreadMsgHandler, r);
    }

    // 异步延时
    public <T extends IObserverBase> void asyncNotify(final MessageID id, final int delayMiliseconds, final Caller<T> r) {
//        if (App.isExiting()) {
//            //KwDebug.classicAssert(false, "模块release函数不允许发通知了，需要的话应该在IAppObserver_PrepareExitApp里做");
//            return;
//        }
        r.__id = id;
        asyncRunTargetHandler(mainThreadMsgHandler, delayMiliseconds, r);
    }

    // 异步0延时，该死的java没有参数默认值
    public <T extends IObserverBase> void asyncNotify(final MessageID id, final Caller<T> r) {
//        if (App.isExiting()) {
//            //KwDebug.classicAssert(false, "模块release函数不允许发通知了，需要的话应该在IAppObserver_PrepareExitApp里做");
//            return;
//        }
        r.__id = id;
        asyncRunTargetHandler(mainThreadMsgHandler, 0, r);
    }

    // 同步主线程执行
    public boolean syncRun(final Runner r) {
        syncRunTargetHandler(mainThreadMsgHandler, r);
        return r.success;
    }

    // 异步主线程执行
    public void asyncRun(final int delayMiliseconds, final Runner r) {
        asyncRunTargetHandler(mainThreadMsgHandler, delayMiliseconds, r);
    }

    // 异步0延时主线程执行
    public void asyncRun(final Runner r) {
        asyncRunTargetHandler(mainThreadMsgHandler, r);
    }

    // 在某个handler里同步执行
    public <T extends IObserverBase> void syncRunTargetHandler(final Handler handler, final Caller<T> r) {
        long startTime = System.currentTimeMillis();
        if (handler.getLooper().getThread().getId() == Thread.currentThread().getId()) {
            r.run();
        } else {
            r.__sync = true;
            try {
                synchronized (r) {
                    handler.post(r);
//                    if (handler == mainThreadMsgHandler && App.isExiting()) {
////						KwDebug.classicAssert(false, "程序退出时候收到非主线程发向主线程的同步通知");
//                    } else {
                        r.wait(); // 请忽略findbugs提示，post在synchronized段里保证了notify不会在wait前执行
//                    }
                }
                r.__sync = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long t = System.currentTimeMillis() - startTime;
//        if (t > 150 && Thread.currentThread().getId() == App.getMainThreadID()) {
////			LogMgr.w(TAG, KwDebug.createStackInfo());
////			LogMgr.w(TAG, "同步消息执行超时，time=" + t);
//            // KwDebug.classicAssert(false, "同步消息执行超时"); //
//            // 开发后期调试少了再打开，否则下断点容易触发
//        }
    }

    // 在某个handler里异步执行
    public <T extends IObserverBase> void asyncRunTargetHandler(final Handler handler, final int delayMiliseconds,
                                                                final Caller<T> r) {
        handler.postDelayed(r, delayMiliseconds);
    }

    // 在某个handler里异步0延时执行
    public void asyncRunTargetHandler(final Handler handler, final Runner r) {
        asyncRunTargetHandler(handler, 0, r);
    }

    public void silence() {
        silence = true;
    }

    MessageManager() {
    }

    static final MessageManager instance = new MessageManager();
    static final long mainThreadId = Thread.currentThread().getId();
    static final Handler mainThreadMsgHandler = new Handler();
    static boolean silence;
    static ArrayList<ArrayList<IObserverBase>> obLists;

    static {
        obLists = new ArrayList<ArrayList<IObserverBase>>(MessageID.values().length);
        for (int i = 0; i < MessageID.values().length; ++i) {
            obLists.add(new ArrayList<IObserverBase>());
        }
    }

}
