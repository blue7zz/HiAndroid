//package com.example.hilibrary.chapter_4;
//
//import androidx.annotation.NonNull;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;
//import java.util.function.Consumer;
//
//public class LinkedBlockingQueueDemo<E> {
//
//    // 链表结构 begin
//    // 链表的元素
//    static class Node<E> {
//        E item;
//
//        //当前元素的下一个，为空表示当前节点是最后一个
//        Node<E> next;
//
//        Node(E x) {
//            item = x;
//        }
//    }
//
//    //链表的容量，默认 Integer.MAX_VALUE
//    private final int capacity;
//
//    //链表已有元素大小，使用 AtomicInteger，所以是线程安全的
//    private final AtomicInteger count = new AtomicInteger();
//
//
//    //链表头
//    transient Node<E> head;
//    //链表尾
//    private transient Node<E> last;
//    // 链表结构 end
//
//
//    // 锁 begin
//    // take 时的锁
//    private final ReentrantLock takeLock = new ReentrantLock();
//    // take 的条件队列，condition 可以简单理解为基于 ASQ 同步机制建立的条件队列
//    private final Condition notEmpty = takeLock.newCondition();
//
//    // put 时的锁，设计两把锁的目的，主要为了 take 和 put 可以同时进行
//    private final ReentrantLock putLock = new ReentrantLock();
//    // put 的条件队列
//    private final Condition notFull = putLock.newCondition(); // 锁 end
//
//    // 迭代器
//    // 实现了自己的迭代器
//    private class Itr implements Iterator<E> {
//        @Override
//        public boolean hasNext() {
//            return false;
//        }
//
//        @Override
//        public E next() {
//            return null;
//        }
//
//        @Override
//        public void remove() {
//            Iterator.super.remove();
//        }
//
//        @Override
//        public void forEachRemaining(@NonNull Consumer<? super E> action) {
//            Iterator.super.forEachRemaining(action);
//        } /*………………*/
//    }
//
//
//    // 不指定容量，默认 Integer 的最大值
//    public LinkedBlockingQueueDemo() {
//        this(Integer.MAX_VALUE);
//    }
//
//    // 指定链表容量大小，链表头尾相等，节点值（item）都是 null
//    public LinkedBlockingQueue(int capacity) {
//        if (capacity <= 0) throw new IllegalArgumentException();
//        this.capacity = capacity;
//        last = head = new Node<E>(null);
//    }
//
//    // 已有集合数据进行初始化
//    public LinkedBlockingQueueDemo(Collection<? extends E> c) {
//        this(Integer.MAX_VALUE); //指定链表容量大小
//
//        //put锁
//        final ReentrantLock putLock = this.putLock;
//        putLock.lock();
//        // Never contended, but necessary for visibility
//        try {
//            int n = 0;
//            for (E e : c) {
//                // 集合内的元素不能为空
//                if (e == null) throw new NullPointerException();
//                // capacity 代表链表的大小，在这里是 Integer 的最大值
//                // 如果集合类的大小大于 Integer 的最大值，就会报错
//                // 其实这个判断完全可以放在 for 循环外面，这样可以减少 Integer 的最大值次循环(最坏情况)
//                if (n == capacity) throw new IllegalStateException("Queue full");
//                enqueue(new Node<E>(e));
//                ++n;
//            }
//            count.set(n);
//        } finally {
//            putLock.unlock();
//        }
//    }
//
//
//    // 把e新增到队列的尾部。
//    // 如果有可以新增的空间的话，直接新增成功，否则当前线程陷入等待
//    public void put(E e) throws InterruptedException {
//        // e 为空，抛出异常
//        if (e == null) throw new NullPointerException();
//
//        // 预先设置 c 为 -1，约定负数为新增失败
//        int c = -1;
//
//
//        Node<E> node = new Node<E>(e);
//        final ReentrantLock putLock = this.putLock;
//        final AtomicInteger count = this.count;
//
//        // 设置可中断锁 lockInterruptibly
//        putLock.lockInterruptibly();
//        try {
//            // 队列满了
//            // 当前线程阻塞，等待其他线程的唤醒(其他线程 take 成功后就会唤醒此处被阻塞的线程)
//            while (count.get() == capacity) {
//                // await 无限等待
//                notFull.await();
//            }
//            // 队列没有满，直接新增到队列的尾部 enqueue(node);
//            // 新增计数赋值,注意这里 getAndIncrement 返回的是旧值
//            // 这里的 c 是比真实的 count 小 1 的
//            c = count.getAndIncrement();
//            // 如果链表现在的大小 小于链表的容量，说明队列未满
//            // 可以尝试唤醒一个 put 的等待线程
//            if (c + 1 < capacity) notFull.signal();
//        } finally {
//            // 释放锁
//            putLock.unlock();
//        }
//        // c==0，代表队列里面有一个元素
//        // 会尝试唤醒一个take的等待线程
//        if (c == 0) signalNotEmpty();
//    }
//
//
//    // 入队，把新元素放到队尾
//    private void enqueue(Node<E> node) {
//        last = last.next = node;
//    }
//
//    // ---------------------------------------------------阻塞删除--------------------------------------------------------------------
//
//
//    public E take() throws InterruptedException {
//        E x;
//        // 默认负数，代表失败
//        int c = -1;
//        // count 代表当前链表数据的真实大小
//        final AtomicInteger count = this.count;
//
//        final ReentrantLock takeLock = this.takeLock;
//
//        takeLock.lockInterruptibly();
//        try {
//            // 空队列时，阻塞，等待其他线程唤醒
//            while (count.get() == 0) {
//                notEmpty.await();
//            }
//            // 非空队列，从队列的头部拿一个出来
//            x = dequeue();
//            // 减一计算，注意 getAndDecrement 返回的值是旧值
//            // c 比真实的 count 大1
//            c = count.getAndDecrement();
//            // 如果队列里面有值，从 take 的等待线程里面唤醒一个。
//            // 意思是队列里面有值啦,唤醒之前被阻塞的线程
//            if (c > 1) notEmpty.signal();
//        } finally {
//            // 释放锁
//            takeLock.unlock();
//        }
//        // 如果队列空闲还剩下一个，尝试从 put 的等待线程中唤醒一个
//        if (c == capacity) signalNotFull();
//        return x;
//    }
//
//    // 队头中取数据
//    private E dequeue() {
//        Node<E> h = head;
//        Node<E> first = h.next;
//        h.next = h;  //help GC
//        head = first;
//        E x = first.item;
//        first.item = null;
//        // 头节点指向 null，删除
//        return x;
//    }
//
//
//    //删除
//
//    // 查看并不删除元素，如果队列为空，返回 null
//    public E peek() {
//        // count 代表队列实际大小，队列为空，直接返回 null
//        if (count.get() == 0) return null;
//        final ReentrantLock takeLock = this.takeLock;
//        takeLock.lock();
//        try {
//            // 拿到队列头
//            Node<E> first = head.next; // 判断队列头是否为空，并返回
//            if (first == null) return null;
//            else
//                return first.item;
//        } finally {
//            takeLock.unlock();
//        }
//    }
//
//
//}
