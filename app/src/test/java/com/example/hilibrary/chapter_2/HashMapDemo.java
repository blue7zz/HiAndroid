package com.example.hilibrary.chapter_2;


import java.util.HashMap;
import java.util.TreeMap;

/**
 * https://img.kancloud.cn/e0/90/e0903cf6ac62c695acdd29441c7819f4_828x686.png
 * 数据结构
 * 数组+链表+红黑树 数组元素可以单个Node 也可能是红黑树 也可以是链表
 * 数组容量 >64 并且链表长度 >8 转成红黑树
 * 红黑树长度<6 转成链表
 * <p>
 * 注释
 * 1：允许null值，非线程安全
 * 2:load factor(影响因子) 默认0.75,均衡了时间和空间损耗算出来的值。
 * 高值会减少空间开销（扩容减少，数组大小增长速度变慢），查找成本增加(HashMap冲突增加，链表长度变长)
 * 不扩容的条件：
 * 数组容量 > 需要的数组大小 / load factor;
 * 3. HashMap非线程安全，可以自己加锁或者通过Collections#synchronizedMap来实现
 */
public class HashMapDemo {
    private void hashMapTest() {
        HashMap hash = new HashMap();
        TreeMap tree = new TreeMap();

    }

    /**
     * https://img.kancloud.cn/5b/36/5b360521b084419a3725cba122c4af93_658x841.png
     * 1.空数组有无初始化，没有的话初始化；
     * 2.如果通过 key 的 hash 能够直接找到值，跳转到 6，否则到 3；
     * 3.如果 hash 冲突，两种解决方案：链表 or 红黑树；
     * 4.如果是链表，递归循环，把新元素追加到队尾；
     * 5.如果是红黑树，调用红黑树新增的方法；
     * 6.通过 2、4、5 将新元素追加成功，再根据 onlyIfAbsent 判断是否需要覆盖；
     * 7.判断是否需要扩容，需要扩容进行扩容，结束。
     */
    private void putMapTest() {

    }


    /**
     * 查找
     * 1：根据hash算法定位数组的索引位置，equals判断当前节点是我们需要寻找的key，是的话直接返回，不是的话往下。
     * 2：判断当前节点有无next节点，有的话判断是链表还是红黑树
     * 3: 根据不同类型的查找方法
     */
    private void query() {
        //链表查询
        //  do {
        //  如果当前节点 hash 等于 key 的 hash，并且 equals 相等，当前节点就是我们要找的节点
        //            // 当 hash 冲突时，同一个 hash 值上是一个链表的时候，我们是通过 equals 方法来比较 key 是否相等的
        //            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
        //                return e;
        //            // 否则，把当前节点的下一个节点拿出来继续寻找
        //        } while ((e = e.next) != null);
    }


}
