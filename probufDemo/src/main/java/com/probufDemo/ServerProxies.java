package com.probufDemo;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * Create by WangLei 2018/6/14 21:03
 * description:
 */
public class ServerProxies {


    public static class ProxyAndInfo<PROXYTYPE>{
        private final PROXYTYPE proxy;
        private final InetSocketAddress address;

        public ProxyAndInfo(PROXYTYPE proxy,InetSocketAddress address){
            this.proxy = proxy;
            this.address = address;
        }

        public PROXYTYPE getProxy(){
            return proxy;
        }

        public InetSocketAddress getAddress(){
            return address;
        }
    }





    public static <T> ProxyAndInfo<T> createProxy(InetSocketAddress nameNodeAddress, Class<T> xface){
        T protocolProxy = RPC.getProtocolProxy(xface, nameNodeAddress);
        return new ProxyAndInfo<T>(protocolProxy,nameNodeAddress);
    }
}
