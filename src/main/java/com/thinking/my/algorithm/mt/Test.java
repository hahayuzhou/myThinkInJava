package com.thinking.my.algorithm.mt;

import java.util.*;

/**
 * @Description
 * @Author liyong
 * @Date 2021/8/24 2:04 下午
 **/
public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<int[]> str = new ArrayList<>();

        int n  = scanner.nextInt();
        int[] arr = new int[n];
        for(int i = 0;i<n;i++){
            arr[i] = scanner.nextInt();
        }
        Arrays.sort(arr);

        str.add(arr);
        List<int[]> allLsit = zuhe(arr);
        System.out.println(allLsit.size());
        allLsit.forEach(e->{
            StringBuilder stringBuilder = new StringBuilder();
            int j= 0;
            for (int i : e) {
                j++;
                if(j==e.length){
                    stringBuilder.append(i);
                }else {
                    stringBuilder.append(i).append(" ");
                }
            }
            System.out.println(stringBuilder.toString());
        });



    }

    public static List<int[]> zuhe(int[] arr){
        int n = arr.length;
        int tmp = -1;
        List<int[]> allList = new ArrayList<>();
        if(n==1){
            allList.add(arr);
        }else {
            for(int i = 0 ;i<n;i++){
                if(tmp != arr[i]){
                    tmp = arr[i];
                    int[] tarr = new int[n-1];
                    int m = 0;
                    for(int j = 0;j<n;j++){
                        if(j==i){
                            continue;
                        }
                        tarr[m] = arr[j];
                        m++;
                    }
                    List<int[]> arrList = zuhe(tarr);
                    for(int k = 0;k < arrList.size();k++){
                        int[] extended = new int[n];
                        extended[0] = tmp;
                        for(int c = 0;c<n-1;c++){
                            extended[c+1] = arrList.get(k)[c];
                        }
                        allList.add(extended);
                    }

                }else {
                    continue;
                }
            }
        }

        return allList;
    }
}
