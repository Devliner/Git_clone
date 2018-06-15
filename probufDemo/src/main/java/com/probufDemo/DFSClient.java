package com.probufDemo;

import com.google.protobuf.ServiceException;

/**
 * Create by WangLei 2018/6/13 19:22
 * description:
 */
public class DFSClient {
    final private ClientProtocol server;

    public DFSClient(ClientProtocol server){
        this.server = server;
    }


    public ClientProtocol getServer() {
        return server;
    }

    public static void main(String[] args) throws ServiceException {
        RpcServer rpcServer = new RpcServer();
        ClientServerProtocolServerSideTranslatorPB ServerSideTranslatorPB = new ClientServerProtocolServerSideTranslatorPB(rpcServer);
        ClientServerProtocolTranslatePB server = new ClientServerProtocolTranslatePB(ServerSideTranslatorPB);
        DFSClient DFSClient = new DFSClient(server);
        DFSClient.getServer().sendData("wanglei","24","man");
    }
}
