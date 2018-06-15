package com.probufDemo;

import com.google.protobuf.ServiceException;

/**
 * Create by WangLei 2018/6/13 20:15
 * description:
 */
public interface ClientProtocol {
    public String sendData(String name, String age, String sex) throws ServiceException;
}

