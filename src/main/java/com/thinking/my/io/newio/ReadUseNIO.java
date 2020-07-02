package com.thinking.my.io.newio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description 一个简单的使用NIO从文件中读取数据的例子
 * 任何时候读取数据，都不是直接从通道读取，而是从通道读取到缓冲区
 *  使用NIO读取数据可以分为下面三个步骤：
 * 1. 从FileInputStream获取Channel
 * 2. 创建Buffer
 * 3. 将数据从Channel读取到Buffer中
 * @Author liyong
 * @Date 2020/1/10 3:38 下午
 **/
public class ReadUseNIO {

    static public void main( String args[] ) throws Exception {

        FileInputStream fin = new FileInputStream("/Users/liyong/Documents/test.txt");
        // 获取通道
        FileChannel fc = fin.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取数据到缓冲区
        fc.read(buffer);
        buffer.flip();
        while (buffer.remaining()>0) {
            byte b = buffer.get();
            System.out.print(((char)b));
        }
        fin.close();

    }

}


