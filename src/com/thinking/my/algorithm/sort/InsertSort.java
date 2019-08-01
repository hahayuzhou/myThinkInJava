package com.thinking.my.algorithm.sort;

import java.util.Arrays;

/**
 * 插入类排序
 * Created by liyong on 2019/7/26.
 */
public class InsertSort {

    /**
     * 直接插入排序
     * @param r
     * @param low
     * @param high
     */
    public static void insertSort(int[] r, int low, int high){
        for (int i=low+1; i<=high; i++)
            if (r[i]<r[i-1]){ //小于时，需将 r[i]插入有序表 Object temp = r[i];
                int temp = r[i];
                r[i] = r[i-1];
                int j=i-2;
                for (; j>=low&&temp<r[j]; j--){
                    r[j+1] = r[j];//记录后移
                }

                r[j+1] = temp;//插入到正确位置
        }
    }

    /**
     * 折半插入排序
     * @param r
     * @param low
     * @param high
     */
    public static void binInsertSort(int[] r,int low ,int high){

        for (int i = low+1; i<=high; i++){
            int temp = r[i];//保存待插入元素
            int hi = i-1;//设置初始区间
            int lo = low;
            while (lo<=hi){
                int mid = (lo+hi)/2;
                if ( temp<r[mid]){
                    hi = mid-1;
                } else {
                    lo = mid+1;
                }
            }

            for (int j = i-1;j>hi;j--){
                r[j+1] = r[j];
            }

            r[hi+1] = temp;
        }
    }

    public static void main(String[] args) {
        int[] r = {3,5,2,1};
        binInsertSort(r,0,r.length-1);
        System.out.println(Arrays.toString(r));
        r = new int[]{3, 5, 2, 1,0};
        insertSort(r,0,r.length-1);
        System.out.println(Arrays.toString(r));


    }



}
