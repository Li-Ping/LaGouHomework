package com.lagou.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 15:36 2020/3/18 0018
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    private Serializer serializer;


    public RpcDecoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        //读取消息头,整个消息的长度字段
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        //读取一个规定的int，即长度
        int dataLength = byteBuf.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        //读取字节数组,直到读取的字节数组长度等于dataLength
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        //将字节数组使用Hession反序列化为对象
        Object obj = serializer.deserialize(clazz,data);
        out.add(obj);

    }
}
