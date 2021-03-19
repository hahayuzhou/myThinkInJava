package com.thinking.my.io.inputstream;

import java.io.*;

/**
 * @Description
 * @Author liyong
 * @Date 2020/12/15 8:28 下午
 **/
public class DataInputStreamDemo {

    public static void main(String[] args) throws IOException {

        InputStream is = null;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        long[] l = {5488489334534l,234223424334l};

        try{
            // create file output stream
//            fos = new FileOutputStream("/Users/liyong/Desktop/data.txt");
//
//            // create data output stream
//            dos = new DataOutputStream(fos);
//
//            // for each long in long buffer
//            for(long j:l)
//            {
//                // write long to data output stream
//                dos.writeLong(j);
//            }
//
//            // force data to the underlying file output stream
//            dos.flush();

            // create file input stream
            is = new FileInputStream("/Users/liyong/Desktop/data.txt");

            // create new data input stream
            dis = new DataInputStream(is);

            // available stream to be read
            while(dis.available()>0)
            {
                // read four bytes from data input, return int
                long k = dis.readLong();

                // print long value
                System.out.println(k+" ");
            }

        }catch(Exception e){
            // if any error occurs
            e.printStackTrace();
        }finally{

            // releases all system resources from the streams
            if(is!=null)
                is.close();
            if(dis!=null)
                dis.close();
            if(fos!=null)
                fos.close();
            if(dos!=null)
                dos.close();
        }
    }
}
