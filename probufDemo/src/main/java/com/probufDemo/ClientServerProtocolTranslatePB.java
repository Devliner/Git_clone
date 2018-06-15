package com.probufDemo;

import com.google.protobuf.ServiceException;

/**
 * Create by WangLei 2018/6/13 19:15
 * description:
 */
public class ClientServerProtocolTranslatePB implements ClientProtocol{

    final private ClientServerProtocolPB rpcProxy;

    public ClientServerProtocolTranslatePB(ClientServerProtocolPB rpcProxy){
        this.rpcProxy = rpcProxy;
    }

    public String sendData(String name, String age, String sex) throws ServiceException {

        ClientServerProtocolProtos.socketRequestProto rep = ClientServerProtocolProtos.socketRequestProto.newBuilder()
                .setName(name)
                .setAge(age)
                .setSex(sex)
                .build();

        return rpcProxy.sendData(null,rep).getResponseId();
    }
}
