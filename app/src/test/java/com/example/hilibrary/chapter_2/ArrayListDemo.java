package com.example.hilibrary.chapter_2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * ArrayList学习
 * <p>
 * 数据结构
 * index            0       1       2       3       4       5        6       7       8       9
 * elementData    [ 01  |  02  ｜   03  ｜  04   ｜  05  ｜  06   |   07   |  08   |  09   ｜ 10 ]
 * <p>
 * * 1.允许put null值，会自动扩容
 * * 2.size,isEmpty,get、set、add等方法时间复杂度都是O（1）
 * * 3.是非线程安全等，多线程情况下，推荐使用线程安全类:Collections#synchronizedList;
 * * 4.增强for循环或者使用迭代器迭代过程中，如果数组大小改变，会快速失败，抛出异常。
 * *
 * * 疑问：
 * * 1. 迭代过程中怎么抛出异常等？
 * * 2. 怎么扩容等？
 */
public class ArrayListDemo {
    //初始化1
    private ArrayList list = new ArrayList();
    //初始化2
    private ArrayList list1 = new ArrayList(10); //容量大小
    //初始化三 Collection
    private ArrayList list2 = new ArrayList(Arrays.asList(new String[10]));


    /**
     * 添加元素
     */
    @Test
    public void addTest() {
        //添加一个元素
        //判断数组大小是否足够
        //足够-直接赋值 elementData[size++] = "111"
        //不足够 - 扩容 数组是否为空 没有超过扩容10个，默认空间是10
        //扩容大小为 oldCapacity + oldCapacity >> 1    老的大小 加上 0。5倍 也就是1。5倍
        //最大值是 Integer.MAX_VALUE超过这个值，JVM就不会给分配内存空间了。
        //https://img1.sycdn.imooc.com/5d5fc62e000112c203600240.gif 扩容动图
        list.add("111");
        //Arrays.copyOf(elementData, newCapacity);
    }

    public void remove() {
        list.add("111");
        list.add(null);
        list.add("hahaha");
        list.remove(null);
        //判断移除元素是否为null,找到null移除
        //如果不是null， e.equals("1111") 数组向前移一位
        //https://img1.sycdn.imooc.com/5d5fc643000142a403600240.gif

        list.iterator().next();
    }

}
