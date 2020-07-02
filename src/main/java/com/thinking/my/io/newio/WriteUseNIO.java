package com.thinking.my.io.newio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description 使用NIO写入数据
 * @Author liyong
 * @Date 2020/1/10 4:00 下午
 **/
public class WriteUseNIO {

        static private final byte message[] = { 83, 111, 109, 101, 32,

                98, 121, 116, 101, 115, 46 };



        static public void main( String args[] ) throws Exception {

            FileOutputStream fout = new FileOutputStream( "/Users/liyong/Documents/test.txt" );

            FileChannel fc = fout.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate( 1024 );

            for (int i=0; i<message.length; ++i) {

                buffer.put( message[i] );

            }

            buffer.flip();
            fc.write( buffer );

            fout.close();
        }

    }


