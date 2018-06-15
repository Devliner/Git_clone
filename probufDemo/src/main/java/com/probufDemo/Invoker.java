package com.probufDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

/**
 * Create by WangLei 2018/6/15 9:26
 * description:
 */
public class Invoker implements InvocationHandler {

    private final String protocolName;
    private final InetSocketAddress address;

    public Invoker(String protocolName,InetSocketAddress address){
        this.protocolName = protocolName;
        this.address = address;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {



        System.out.println("进入到了 Invoker() , " + method.getName());
        return null;
    }
}
