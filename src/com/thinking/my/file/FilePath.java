package com.thinking.my.file;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liyong on 2019/7/28.
 */
public class FilePath {

    public static void getFilePath(String path){
        File file = new File(path);
        File[] files =  file.listFiles();
        for(File f:files){
            outFilePathName(f);
        }


    }


    public static void outFilePathName(File file){
        System.out.println(file.getAbsolutePath());
//        System.out.println(file.getPath());
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public static void main(String[] args) {
        String path = "/Users/liyong/Desktop/";
//        getFilePath(path);
        traverseFolder1(path);
    }

    //非递归输出 所有文件
    public static void traverseFolder1(String path){

        File file = new File(path);
        //判断文件是否存在
        if(file.exists()){

            LinkedList<File> linkedList = new LinkedList();
            addFile(file,linkedList);

            File temp_file;
            while (!linkedList.isEmpty()){
                temp_file = linkedList.removeFirst();
                addFile(temp_file,linkedList);

            }
        }else{
            System.out.println("文件不存在");
        }
    }


    public static void addFile(File file,List<File> fList){
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                fList.add(f);
                System.out.println(f.getName()+"is目录");
            }
            outFilePathName(f);
        }
    }



}
