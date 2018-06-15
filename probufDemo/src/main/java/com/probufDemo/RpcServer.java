package com.probufDemo;

import com.google.protobuf.ServiceException;

/**
 * Create by WangLei 2018/6/13 20:28
 * description:
 */
public class RpcServer implements ClientProtocol{
    public String sendData(String name, String age, String sex) throws ServiceException {

        System.out.println(name + ", " + age + ", " + sex);

        return "response" + System.currentTimeMillis();
    }
}
