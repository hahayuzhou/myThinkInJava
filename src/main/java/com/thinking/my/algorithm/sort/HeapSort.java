package com.thinking.my.algorithm.sort;

import java.util.Arrays;

/**
 *
 * 堆排序
 * Created by liyong on 2019/7/3.
 */
public class HeapSort {

    private static int num=0;
    /**
     * 调整大顶堆
     * @param arr
     * @param i
     * @param leng
     */
    public static void adjustBigHeap(int[] arr,int i,int leng){
        int tmp = arr[i];
        for(int k = 2*i+1;k<leng;k=2*k+1){
            num++;
            if(k+1<leng && arr[k]<arr[k+1]){

                k++;
            }
            if(arr[k]>tmp){
                num++;
                arr[i] = arr[k];
                i=k;
            }else{
                num++;
                break;
            }

        }
        arr[i] = tmp;
    }

    public static void sortBigHead(int[] arr){
        //1.构建大顶堆
        for ( int i= arr.length/2-1;i>=0;i--){
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustBigHeap(arr,i,arr.length);
        }

        //2.调整堆结构+交换堆顶元素与末尾元素
        for(int j=arr.length-1;j>0;j--){
            swap(arr,0,j);//将堆顶元素与末尾元素进行交换
            adjustBigHeap(arr,0,j);//重新对堆进行调整
        }

    }


    /**
     * 交换元素
     * @param arr
     * @param a
     * @param b
     */
    public static void swap(int []arr,int a ,int b){
        int temp=arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


    public static void main(String []args){
        int []arr = {9,8,7,6,5,4,3,2,1};
        sortBigHead(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(num);
    }






}
