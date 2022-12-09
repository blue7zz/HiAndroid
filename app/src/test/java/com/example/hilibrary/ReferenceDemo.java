package com.example.hilibrary;

import com.example.hilibrary.processor.User;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceDemo {


    //不会被回收，内存不足时候虚拟机会抛出OOM,崩溃，不会被回收
    private User user;
    //内存空间不足，就会回收对象。
    private SoftReference<User> sUser;
    //如果只具有若引用到对象，只要被垃圾回收器扫描到就会被回收。
    private WeakReference<User> wUser;
    //没啥作用，跟不存在一样，任何时候都可能被垃圾回收器回收
    //作用：跟做对象垃圾回收器都回收活动。 必须和ReferenceQueue使用？
    private PhantomReference<User> pUser;

    private ReferenceQueue s;


}
