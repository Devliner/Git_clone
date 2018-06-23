package com.probufDemo;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import org.apache.hadoop.ipc.protobuf.ProtobufRpcEngineProtos;

import javax.net.SocketFactory;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
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


        Message theRequest = (Message) args[1];


        System.out.println("进入到了 Invoker() , " + method.getName());
        return null;
    }


    private ProtobufRpcEngineProtos.RequestHeaderProto constructRpcRequestHeader(Method method){
        ProtobufRpcEngineProtos.RequestHeaderProto.Builder builder = ProtobufRpcEngineProtos.RequestHeaderProto.newBuilder();
        builder.setMethodName(method.getName())
                .setDeclaringClassProtocolName(protocolName)
                .setClientProtocolVersion(1L);
        return builder.build();
    }


    interface RpcWrapper{
        void write(DataOutput out) throws IOException;
        void readFields(DataInput in) throws IOException;
        int getLength();
    }

    private static abstract class RpcMessageWithHeader<T extends GeneratedMessage> implements RpcWrapper{
        T requestHeader;
        Message theRequest; // for clientSide, the request is here
        byte[] theRequestRead; // for server side, the request is here

        public RpcMessageWithHeader() {
        }

        public RpcMessageWithHeader(T requestHeader, Message theRequest) {
            this.requestHeader = requestHeader;
            this.theRequest = theRequest;
        }


        public void write(DataOutput out) throws IOException {
            OutputStream os = DataOutputOutputStream.constructOutputStream(out);

            ((Message)requestHeader).writeDelimitedTo(os);
            theRequest.writeDelimitedTo(os);
        }

        public void readFields(DataInput in) throws IOException {
            requestHeader = parseHeaderFrom(readVarintBytes(in));
            theRequestRead = readMessageRequest(in);
        }

        abstract T parseHeaderFrom(byte[] bytes) throws IOException;

        byte[] readMessageRequest(DataInput in) throws IOException {
            return readVarintBytes(in);
        }

        private static byte[] readVarintBytes(DataInput in) throws IOException {
            final int length = ProtoUtil.readRawVarint32(in);
            final byte[] bytes = new byte[length];
            in.readFully(bytes);
            return bytes;
        }

        public T getMessageHeader() {
            return requestHeader;
        }

        public byte[] getMessageBytes() {
            return theRequestRead;
        }

        public int getLength() {
            int headerLen = requestHeader.getSerializedSize();
            int reqLen;
            if (theRequest != null) {
                reqLen = theRequest.getSerializedSize();
            } else if (theRequestRead != null ) {
                reqLen = theRequestRead.length;
            } else {
                throw new IllegalArgumentException(
                        "getLength on uninitialized RpcWrapper");
            }
            return CodedOutputStream.computeRawVarint32Size(headerLen) +  headerLen
                    + CodedOutputStream.computeRawVarint32Size(reqLen) + reqLen;
        }
    }

    public static class RpcResponseWrapper implements RpcWrapper {
        Message theResponse; // for senderSide, the response is here
        byte[] theResponseRead; // for receiver side, the response is here

        public RpcResponseWrapper() {
        }

        public RpcResponseWrapper(Message message) {
            this.theResponse = message;
        }

        public void write(DataOutput out) throws IOException {
            OutputStream os = DataOutputOutputStream.constructOutputStream(out);
            theResponse.writeDelimitedTo(os);
        }

        public void readFields(DataInput in) throws IOException {
            int length = ProtoUtil.readRawVarint32(in);
            theResponseRead = new byte[length];
            in.readFully(theResponseRead);
        }

        public int getLength() {
            int resLen;
            if (theResponse != null) {
                resLen = theResponse.getSerializedSize();
            } else if (theResponseRead != null ) {
                resLen = theResponseRead.length;
            } else {
                throw new IllegalArgumentException(
                        "getLength on uninitialized RpcWrapper");
            }
            return CodedOutputStream.computeRawVarint32Size(resLen) + resLen;
        }
    }

}
