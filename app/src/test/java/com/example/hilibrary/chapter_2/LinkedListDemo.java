package com.example.hilibrary.chapter_2;

import java.util.LinkedList;

/**
 * https://img1.sycdn.imooc.com/5d5fc67a0001f59212400288.png 数据结构
 * [Null | first | Node1 | Node2 | Node3| last | Null]
 * - 节点是Node
 * first 是头节点  last是尾节点
 * 只要及其内存足够大，大小没有限制
 * <p>
 * 想象一下锁链
 * https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg95.699pic.com%2Fphoto%2F50147%2F8748.jpg_wh860.jpg&refer=http%3A%2F%2Fimg95.699pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661570745&t=3046871eeff28e92e5c97ab57cd82931 *
 */
public class LinkedListDemo<E> {
    private LinkedList linkedList = new LinkedList();

    private Node firstNode;
    private Node lastNode;
    private int size = 0;  //int 类型
    private int modCount = 0;


    /**
     * Node 数据结构
     *
     * @param <E>
     */
    private static class Node<E> {
        E item; //节点值
        Node<E> next; //下一个节点
        Node<E> prev; //上一个节点

        public Node(E item, Node<E> next, Node<E> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    //https://img1.sycdn.imooc.com/5d5fc69600013e4803600240.gif
    //添加
    //锁链最简单的加扣 是前面或者后面加
    //头部添加
    //尾部添加
    private void linkFirst(E e) {
        //先创建一个Node
        final Node<E> f = firstNode;
        final Node<E> newNode = new Node(e, null, firstNode);
        firstNode = newNode;
        if (f == null) {
            lastNode = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
        modCount++;

    }
    //删除 - 从头部删除，从尾部删除
    //从头部删除
    //拿到第一个节点
    //把第一个节点next 变成第一个节点 first
    //清空关联，清空变量等待GC回收


    //查询
    //简单二分法查询的，先判查询的index是属于前半部分还是后半部分
    //index < (size>>1)  //size >>1 等于 size /2




}
