package com.thinking.my.algorithm.sort;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liyong on 2019/7/2.
 */
public class QuickSort {


    public static int partition(Integer[] arr,int left,int right){
        int pivot = arr[left];
        while(left<right){
            while (left<right&arr[right]>=pivot){
                right--;
            }
            arr[left] = arr[right];
            while (left<right&arr[left]<=pivot){
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;

    }
    public static void quickSort(Integer[] arr,int left,int right){
        if(left<right){
            int p = partition(arr,left,right);
            quickSort(arr,left,p-1);
            quickSort(arr,p+1,right);
        }
    }

    public static void quickSort2(Integer[] arr,int left,int right){
        if(left<right){

        }

        return;
    }


    public static void main(String[] args) {
        Integer[] arr = {1,2,3,5,7,4,2};
        System.out.println(arr.hashCode());
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i]);
        }
        quickSort(arr,0,arr.length-1);
        System.out.println(arr.hashCode());

        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i]);
        }

       List<Integer> aa = Arrays.asList(arr);
        Arrays.stream(arr).forEach(e->{
            System.out.println(e);
        });
    }










}
