package com.example.hilibrary.proxy;

/**
 * 被代理类
 */
public class TVFactory implements TVCompany{
    @Override
    public TV produceTv() {
        System.out.println("TV factory produce TV...");
        return new TV("小米电视机","合肥");
    }

    @Override
    public TV repair(TV tv) {
        System.out.println("tv is repair finished...");
        return new TV("小米电视机","合肥");
    }
}
