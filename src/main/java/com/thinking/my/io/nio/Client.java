package com.thinking.my.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author liyong
 * @Date 2020/9/11 3:44 下午
 **/
public class Client {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();) {
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
            System.out.println("client 启动...");

            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            buffer.put("hi, 这是client".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

