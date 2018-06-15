package com.probufDemo;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * Create by WangLei 2018/6/15 9:22
 * description:
 */
public class RPC {

    public static <T> T getProtocolProxy(Class<T> protocol, InetSocketAddress address){
        Invoker invoker = new Invoker(protocol.getName(), address);

        return (T) Proxy.newProxyInstance(protocol.getClassLoader(),new Class[]{protocol},invoker);
    }
}
