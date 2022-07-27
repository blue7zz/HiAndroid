package com.example.hilibrary.chapter_1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class C_1_03 {
    /**
     * 静态属性
     * 非线程安全的
     * 需要换成，CopyOnWriteArrayList  或者访问时候手动加锁
     */
    public static List list = new ArrayList();
    /**
     * 静态属性
     * 线程安全的
     */
    public static List listA = new CopyOnWriteArrayList();

    /**
     * 静态方法
     */
    public static void staticFun() {

    }

    @Test
    public void staticTest() {

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void finalTest() {
    }

    /**
     * 不能被继承
     */
    public final class FinalClass {
    }

    /**
     * 不能被复写
     */
    final void finalFun() {
    }

    /**
     * 必须初始化，后面不能修改内存地址
     */
    final String s = "";

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * finally就算抛出异常以后页必定会执行。
     */
    @Test
    public void testCatchFinally() {
        try {
            System.out.println("try is run");
            if (true) {
                throw new RuntimeException("try exception");
            }
        } catch (Exception e) {
            System.out.println("catch is run");
            if (true) {
                throw new RuntimeException("catch exception");
            }
        } finally {
            System.out.println("finally is run");
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    volatile int i = 0;

    @Test
    public void volatileTest() {

        Thread thread = new Thread(() -> {
            int l = 0;
            while (l < 10) {
                l++;
                System.out.println("线程1"+i++);
            }
        });

        Thread thread1 = new Thread(() -> {
            int l = 0;
            while (l < 10) {
                l++;
                System.out.println("线程2"+i++);
            }
        });
        Thread thread2 = new Thread(() -> {
            int l = 0;
            while (l < 10) {
                l++;
                System.out.println("线程3"+i++);
            }
        });


        thread.start();
        thread1.start();
        thread2.start();

    }

}
