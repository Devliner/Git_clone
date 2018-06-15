package com.probufDemo;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

/**
 * Create by WangLei 2018/6/13 20:32
 * description:
 */
public class ClientServerProtocolServerSideTranslatorPB implements ClientServerProtocolPB {
    final private ClientProtocol server;

    public ClientServerProtocolServerSideTranslatorPB(ClientProtocol server){
        this.server = server;
    }

    public ClientServerProtocolProtos.socketResponseProto sendData(RpcController controller, ClientServerProtocolProtos.socketRequestProto rep) throws ServiceException {
        String result = server.sendData(rep.getName(), rep.getAge(), rep.getSex());


        return ClientServerProtocolProtos.socketResponseProto.newBuilder().setResponseId(result).build();
    }
}
