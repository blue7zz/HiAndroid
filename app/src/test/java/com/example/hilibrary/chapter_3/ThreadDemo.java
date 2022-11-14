//package com.example.hilibrary.chapter_3;
//
//import com.example.hilibrary.Log;
//
//import org.junit.Test;
//
//import java.util.concurrent.FutureTask;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//public class ThreadDemo {
//
//    @Test
//    public void extendThreadInit() throws InterruptedException {
//        new MyThread().start();
//        Thread.sleep(10000);
//    }
//
//
//    @Test
//    public void join() throws Exception {
//        Thread main = Thread.currentThread();
//        Log.info("{} is run。", main.getName());
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.info("{} begin run", Thread.currentThread().getName());
//                try {
//                    Thread.sleep(30000L);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.info("{} end run", Thread.currentThread().getName());
//            }
//        });
//        // 开一个子线程去执行
//        thread.start();
//        // 当前主线程等待子线程执行完成之后再执行
//        thread.join();
//        Log.info("{} is end", Thread.currentThread().toString());
//    }
//
//    //底层原码
////    // 该方法可以创建一个新的线程出来
////    public synchronized void start() { // 如果没有初始化，抛异常
////        if (threadStatus != 0)
////            throw new IllegalThreadStateException();
////        group.add(this);
////        // started 是个标识符，我们在做一些事情的时候，经常这么写
////        // 动作发生之前标识符是 false，发生完成之后变成 true
////        boolean started = false;
////        try {
////            // 这里会创建一个新的线程，执行完成之后，新的线程已经在运行了，
////            // 既 target 的内容已经在运行了
////            start0();
////            // 这里执行的还是主线程
////            started = true;
////        } finally {
////            try {
////                // 如果失败，把线程从线程组中删除
////                if (!started) {
////                    group.threadStartFailed(this);q
////                }
////                // Throwable 可以捕捉一些 Exception 捕捉不到的异常，比如说子线程抛出的异常
////            } catch (Throwable ignore) { /* do nothing. If start0 threw a Throwable then it will be passed up the call stack */ }
////        }
////    }
////
////    // 开启新线程使用的是 native 方法
////    private native void start0();
//
//
//    // 继承 Thread，实现其 run 方法
//    class MyThread extends Thread {
//        @Override
//        public void run() {
//            while (true) {
//                System.out.println(Thread.currentThread().getName());
//            }
//        }
//
//
//        // 无参构造器，线程名字自动生成
//        public MyThread() {
//            init(null, null, "Thread-" + nextThreadNum(), 0);
//        }
//
//        /**
//         * @param g         代表线程组，线程组可以对组内的线程进行批量的操作，比如批量的打断 interrupt
//         * @param target    是我们要运行的对象
//         * @param name      我们可以自己传，如果不传默认是 "Thread-" + nextThreadNum()，nextThreadNum 方法返回的是自增的数字
//         * @param stackSize 可以设置堆栈的大小
//         * @param acc       注：该参数表示访问控制权限，了解 AccessControlContext 参考 Java安全模式 （暂不考虑）
//         */
//        private void init(ThreadGroup g, Runnable target, String name, long stackSize, AccessControlContext acc) {
//            if (name == null) {
//                throw new NullPointerException("name cannot be null");
//            }
//            this.name = name.toCharArray();
//
//            // 当前线程作为父线程
//            Thread parent = currentThread();
//            this.group = g;
//
//            // 子线程会继承父线程的守护属性
//            this.daemon = parent.isDaemon();
//
//            // 子线程继承父线程的优先级属性
//            this.priority = parent.getPriority();
//
//            // classLoader
//            if (security == null || isCCLOverridden(parent.getClass()))
//                this.contextClassLoader = parent.getContextClassLoader();
//            else this.contextClassLoader = parent.contextClassLoader;
//
//            this.inheritedAccessControlContext = acc != null ? acc : AccessController.getContext();
//
//            this.target = target;
//            setPriority(priority);
//            // 当父线程的 inheritableThreadLocals 的属性值不为空时 会把 inheritableThreadLocals 里面的值全部传递给子线程
//            //inheritableThreadLocals
//            if (parent.inheritableThreadLocals != null)
//                this.inheritableThreadLocals = ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
//            //堆栈大小
//            this.stackSize = stackSize; /* Set thread ID */
//            // 线程 id 自增
//            tid = nextThreadID();
//        }
//    }
//
//
//    // 首先我们创建了一个线程池
//    ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
//
//    // futureTask 我们叫做线程任务，构造器的入参是Callable
//    FutureTask futureTask =new FutureTask(new Callable<String> () {
//        @Override public String call () throws Exception {
//            Thread.sleep(3000);
//            // 返回一句话
//            return "我是子线程" + Thread.currentThread().getName();
//        }
//    });
//    // 把任务提交到线程池中，线程池会分配线程帮我们执行任务
//    executor.submit(futureTask);
//    // 得到任务执行的结果
//    String result = (String) futureTask.get();
//    log.info("result is {}",result);
//
//
//}
//
////Callable 是一个接口，约定了线程要做的事情，和 Runnable 一样，不过这个线程任务是有返回值的，我们来看下接口定义：
////public interface Callable<V> { V call() throws Exception; }
//
//
////FutureTask 我们可以当做是线程运行的具体任务，从上图中，我们可以看到 FutureTask 实现了 RunnableFuture 接口，源码如下：
////public class FutureTask<V> implements RunnableFuture<V> { }
////而 RunnableFuture 又实现了 Runnable, Future 两个接口，接下来我们先看 Future，再看 RunnableFuture，最后看 FutureTask。
//
//
//
//// 调用 start 方法即可，会自动调用到 run 方法的
//
////// 如果任务已经成功了，或已经取消了，是无法再取消的，会直接返回取消成功(true)
////// 如果任务还没有开始进行时，发起取消，是可以取消成功的。
////// 如果取消时，任务已经在运行了，mayInterruptIfRunning 为 true 的话，就可以打断运行中的线程
////// mayInterruptIfRunning 为 false，表示不能打断直接返回
//
////boolean cancel(boolean mayInterruptIfRunning);
////// 返回线程是否已经被取消了，true 表示已经被取消了
////// 如果线程已经运行结束了，isCancelled 和 isDone 返回的都是 true
////boolean isCancelled();
////// 线程是否已经运行结束了boolean
////isDone();
////// 等待结果返回
////// 如果任务被取消了，抛 CancellationException 异常
////// 如果等待过程中被打断了，抛 InterruptedException 异常
////V get() throws InterruptedException, ExecutionException;// 等待，但是带有超时时间的，如果超时时间外仍然没有响应，抛 TimeoutException 异常
/////V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
//
//
//
//
//// 任务状态
//// private volatile int state;
//// private static final int NEW = 0;
//// 线程任务创建 private static final int COMPLETING = 1;
//// 任务执行中 private static final int NORMAL = 2;
//// 任务执行结束 private static final int EXCEPTIONAL = 3;
//// 任务异常 private static final int CANCELLED = 4;
//// 任务取消成功 private static final int INTERRUPTING = 5;
//// 任务正在被打断中 private static final int INTERRUPTED = 6;
//// 任务被打断成功
//// 组合了 Callable private Callable<V> callable;
//// 异步线程返回的结果 private Object outcome;
//// 当前任务所运行的线程 private volatile Thread runner;
//// 记录调用 get 方法时被等待的线程 private volatile WaitNode waiters;
//
//
