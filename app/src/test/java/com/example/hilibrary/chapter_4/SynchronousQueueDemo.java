//package com.example.hilibrary.chapter_4;
//
//import java.util.concurrent.SynchronousQueue;
//
///**
// * 类注释
// * 队列不存储数据，所以没有大小，也无法迭代；
// * 插入操作的返回必须等待另一个线程完成对应数据的删除操作，反之亦然
// * 队列由两种数据结构组成，分别是后入先出的堆栈和先入先出的队列，堆栈是非公平的，队列是公平的
// */
//public class SynchronousQueueDemo<E> {
//
//
//    SynchronousQueue queue;
//
//    // 堆栈和队列共同的接口
//    // 负责执行 put or take
//    abstract static class Transferer<E> {
//        // e 为空的，会直接返回特殊值，不为空会传递给消费者
//        // timed 为 true，说明会有超时时间
//        abstract E transfer(E e, boolean timed, long nanos);
//    }
//
//    // 堆栈 后入先出 非公平
//    // Scherer-Scott 算法
//    static final class TransferStack<E> extends Transferer<E> {
//
//        @Override
//        E transfer(E e, boolean timed, long nanos) {
//            return null;
//        }
//    }
//
//    // 队列 先入先出 公平
//    static final class TransferQueue<E> extends Transferer<E> {
//        @Override
//        E transfer(E e, boolean timed, long nanos) {
//            return null;
//        }
//    }
//
//    private transient volatile Transferer<E> transferer;
//
//    // 无参构造器默认为非公平的
//    public SynchronousQueueDemo(boolean fair) {
//        transferer = fair ? new TransferQueue<E>() : new TransferStack<E>();
//    }
//
//
//    static final class SNode {
//        // 栈的下一个，就是被当前栈压在下面的栈元素
//        volatile SNode next;
//        // 节点匹配，用来判断阻塞栈元素能被唤醒的时机
//        // 比如我们先执行 take，此时队列中没有数据，take 被阻塞了，栈元素为 SNode1
//        // 当有 put 操作时，会把当前 put 的栈元素赋值给 SNode1 的 match 属性，并唤醒 take 操作
//        // 当 take 被唤醒，发现 SNode1 的 match 属性有值时，就能拿到 put 进来的数据，从而返回
//        volatile SNode match;
//        // 栈元素的阻塞是通过线程阻塞来实现的，waiter 为阻塞的线程
//        volatile Thread waiter;
//        // 未投递的消息，或者未消费的消息
//        Object item;
//    }
//
//
//    //入栈和出栈
//    // transfer 方法思路比较复杂，因为 take 和 put 两个方法都揉在了一起
//    @SuppressWarnings("unchecked")
//    E transfer(E e, boolean timed, long nanos) {
//        SNode s = null;
//        // constructed/reused as needed
//        // e 为空，说明是 take 方法，不为空是 put 方法
//        int mode = (e == null) ? REQUEST : DATA;
//        // 自旋
//        for (; ; ) {
//            // 拿出头节点，有几种情况
//            // 1：头节点为空，说明队列中还没有数据
//            // 2：头节点不为空，并且是 take 类型的，说明头节点线程正等着拿数据。
//            // 3：头节点不为空，并且是 put 类型的，说明头节点线程正等着放数据。
//            SNode h = head;
//            // 栈头为空，说明队列中还没有数据。
//            // 栈头不为空，并且栈头的类型和本次操作一致，比如都是 put，那么就把
//            // 本次 put 操作放到该栈头的前面即可，让本次 put 能够先执行
//            if (h == null || h.mode == mode) {
//                // empty or same-mode
//                // 设置了超时时间，并且 e 进栈或者出栈要超时了，
//                // 就会丢弃本次操作，返回 null 值。
//                // 如果栈头此时被取消了，丢弃栈头，取下一个节点继续消费
//                if (timed && nanos <= 0) {
//                    // can't wait 栈头操作被取消
//                    if (h != null && h.isCancelled())
//                        // 丢弃栈头，把栈头后一个元素作为栈头
//                        casHead(h, h.next);
//                        // pop cancelled node
//                        // 栈头是空的，直接返回 null
//                    else return null;
//                    // 没有超时，直接把 e 作为新的栈头
//                } else if (casHead(h, s = snode(s, e, h, mode))) {
//                    // e 等待出栈，一种是空队列 take，一种是 put
//                    SNode m = awaitFulfill(s, timed, nanos);
//                    if (m == s) {
//                        // wait was cancelled
//                        clean(s);
//                        return null;
//                    }
//                    // 本来 s 是栈头的，现在 s 不是栈头了，s 后面又来了一个数，把新的数据作为栈头
//                    if ((h = head) != null && h.next == s) casHead(h, s.next);
//                    // help s's fulfiller
//                    return (E) ((mode == REQUEST) ? m.item : s.item);
//                } // 栈头正在等待其他线程 put 或 take
//
//                // 比如栈头正在阻塞，并且是 put 类型，而此次操作正好是 take 类型，走此处
//            } else if (!isFulfilling(h.mode)) {
//                // try to fulfill
//                // 栈头已经被取消，把下一个元素作为栈头
//                if (h.isCancelled())
//                    // already cancelled
//                    casHead(h, h.next);
//                    // pop and retry
//                    // snode 方法第三个参数 h 代表栈头，赋值给 s 的 next 属性
//                else if (casHead(h, s = snode(s, e, h, FULFILLING | mode))) {
//                    for (; ; ) {
//                        // loop until matched or waiters disappear
//                        // m 就是栈头，通过上面 snode 方法刚刚赋值
//                        SNode m = s.next;
//                        // m is s's match
//                        if (m == null) {
//                            // all waiters are gone
//                            casHead(s, null);
//                            //pop fulfill
//                            node s = null;
//                            // use new node next time break;
//                            // restart main loop
//                        }
//                        SNode mn = m.next;
//                        // tryMatch 非常重要的方法，两个作用：
//                        // 1 唤醒被阻塞的栈头 m，2 把当前节点 s 赋值给 m 的 match 属性
//                        // 这样栈头 m 被唤醒时，就能从 m.match 中得到本次操作 s
//                        // 其中 s.item 记录着本次的操作节点，也就是记录本次操作的数据
//                        if (m.tryMatch(s)) {
//                            casHead(s, mn);
//                            // pop both s and m
//                            return (E) ((mode == REQUEST) ? m.item : s.item);
//                        } else
//                            // lost match
//                            s.casNext(m, mn);
//                        // help unlink
//                    }
//                }
//            } else {
//                // help a fulfiller
//                SNode m = h.next;
//                // m is h's match
//                if (m == null) // waiter is gone
//                    casHead(h, null);
//                    // pop fulfilling node
//                else {
//                    SNode mn = m.next;
//                    if (m.tryMatch(h))
//                        // help match
//                        casHead(h, mn);
//                    // pop both h and m else
//                    // lost match
//                    h.casNext(m, mn);
//                    // help unlink
//                }
//            }
//        }
//    }
//
//
//    SNode awaitFulfill(SNode s, boolean timed, long nanos) {
//        // deadline 死亡时间，如果设置了超时时间的话，死亡时间等于当前时间 + 超时时间，否则就是 0
//        final long deadline = timed ? System.nanoTime() + nanos : 0L;
//        Thread w = Thread.currentThread();
//        // 自旋的次数，如果设置了超时时间，会自旋 32 次，否则自旋 512 次。
//        // 比如本次操作是 take 操作，自选次数后，仍没有其他线程 put 数据进来
//        // 就会阻塞，有超时时间的，会阻塞固定的时间，否则一致阻塞下去
//        int spins = (shouldSpin(s) ? (timed ? maxTimedSpins : maxUntimedSpins) : 0);
//        for (; ; ) {
//            // 当前线程有无被打断，如果过了超时时间，当前线程就会被打断
//            if (w.isInterrupted()) s.tryCancel();
//            SNode m = s.match;
//            if (m != null) return m;
//            if (timed) {
//                nanos = deadline - System.nanoTime();
//                // 超时了，取消当前线程的等待操作
//                if (nanos <= 0L) {
//                    s.tryCancel();
//                    continue;
//                }
//            }
//            // 自选次数减少 1
//            if (spins > 0) spins = shouldSpin(s) ? (spins - 1) : 0;
//                // 把当前线程设置成 waiter，主要是通过线程来完成阻塞和唤醒
//            else if (s.waiter == null) s.waiter = w;
//                // establish waiter so can park next iter
//            else if (!timed)
//                // 通过 park 进行阻塞，这个我们在锁章节中会说明
//                LockSupport.park(this);
//            else if (nanos > spinForTimeoutThreshold) LockSupport.parkNanos(this, nanos);
//        }
//    }
//
//
//}
