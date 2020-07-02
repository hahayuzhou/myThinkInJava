package com.thinking.my.algorithm.sum;

/**
 * @Description
 * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
 *
 *示例 1：
 *
 * 输入: n = 3
 * 输出: 6
 * 示例 2：
 *
 * 输入: n = 9
 * 输出: 45
 * @Author liyong
 * @Date 2020/6/2 8:55 下午
 **/
public class Solution2 {

    public static int sumNums(int n) {


        if(n == 1){
            return n;
        }
        int result = n+sumNums(n-1);
        return result;
    }

    public static int sumNums2(int n) {
       boolean b = n==1||(n+=sumNums(n-1))==1;
       return n;
    }

    public static void main(String[] args) {
        System.out.println(sumNums(3));
        System.out.println(sumNums(9));
        System.out.println(sumNums2(3));
        System.out.println(sumNums2(9));
    }

    /**
     * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
     *
     * 输入: 12258
     * 输出: 5
     * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {

        String snum = num+"";
        int max2 = snum.length()/2;
        String[] sttr = new String[snum.length()];

        for(int j=1;j<=max2;j++){
            for(int i=0;i<snum.length();i++){
                int number = Integer.parseInt(sttr[i]+sttr[i+1]);
                while (number<=25&&number>9){
                    i++;
                }
            }
        }
        return 0;
    }

}
