package com.thinking.my.algorithm.fib;

/**
 * @Description
 * @Author liyong
 * @Date 2021/12/16 8:26 下午
 **/
public class Solution {
    /**
     * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下：
     *
     * F(0) = 0,   F(1) = 1
     * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
     * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
     *
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：n = 2
     * 输出：1
     * 示例 2：
     *
     * 输入：n = 5
     * 输出：5
     *
     *
     * @param n
     * @return
     */
    public static int fib(int n) {
        if(n<=1){
            return n;
        }

//        int m = fib(n-1)+fib(n-2);
//        if(m<=1000000007){
//            return m;
//        }

        int n_2 = 0;
        int n_1 = 1;
        int m = 1;
        for(int i=2;i<=n;i++){
            m = (n_1 + n_2)%1000000007;
            n_2 = n_1;
            n_1 = m;
        }
        return m;

    }

    public static void main(String[] args) {
        System.out.println(numWays(50));
    }


    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
     *
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     *
     * 示例 1：
     *
     * 输入：n = 2
     * 输出：2
     * 示例 2：
     *
     * 输入：n = 7
     * 输出：21
     * 示例 3：
     *
     * 输入：n = 0
     * 输出：1
     */
    public static int numWays(int n) {
        if(n<=1){
            return n;
        }

//        int m = fib(n-1)+fib(n-2);
//        if(m<=1000000007){
//            return m;
//        }

        int n_2 = 1;
        int n_1 = 1;
        int m = 1;
        for(int i=2;i<=n;i++){
            m = (n_1 + n_2)%1000000007;
            n_2 = n_1;
            n_1 = m;
        }
        return m;
    }

    /**
     * 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
     *
     *  
     *
     * 示例 1:
     *
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     * 示例 2:
     *
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     *  
     *
     * 限制：
     *
     * 0 <= 数组长度 <= 10^5
     *
     * @param prices
     * @return
     */








    public int maxProfit(int[] prices) {
        if(prices==null||prices.length<=1){
            return 0;
        }
        int m = 0;
        int min = prices[0];

        for(int i=1;i<prices.length;i++){
            int c = prices[i]-min;
            if(c >= 0){
                if(c>m){
                    m = c;
                }
            }else{
                min = prices[i];
            }
        }
        return m;

    }
}
