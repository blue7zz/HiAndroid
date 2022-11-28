package com.example.hilibrary.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 代理类
 */
public class TVProxyFactory {

    private Object target;

    public TVProxyFactory(Object target) {
        this.target = target;
    }

    /**
     *
     * @return 返回任意类
     */
    public Object getProxy(){
        //类加载器
        //要实现的接口
        //动态代理调用的方法
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("TV proxy find factory for tv.... ");
                Object invoke = method.invoke(target,args);
                return invoke;
            }
        });
    }
}
