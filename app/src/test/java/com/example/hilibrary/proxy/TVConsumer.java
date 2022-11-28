package com.example.hilibrary.proxy;

import org.junit.Test;

public class TVConsumer {


    @Test
    public void test(){
        TVCompany target = new TVFactory();

        TVCompany tvCompany = (TVCompany) new TVProxyFactory(target).getProxy();
        System.out.println("调用，produceTv 开始");
        TV tv = tvCompany.produceTv();
        System.out.println("调用，produceTv 结束");

        System.out.println("调用，repair 开始");
        tvCompany.repair(tv);
        System.out.println("调用，repair 结束");
    }
}
