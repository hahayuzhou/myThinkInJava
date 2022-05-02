package com.thinking.my.algorithm.dynamic;

/**
 * @Description
 * @Author liyong
 * @Date 2021/12/20 8:30 下午
 **/
public class Solution {
    /**
     * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
     *
     * 要求时间复杂度为O(n)。
     *
     * 示例1:
     *
     * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        int sum = nums[0];
        int sum1 = 0;
        int sum2 = 0;
        int min = nums[0];
        int begin = 0;
        int end = 0;

        for(int i=1;i<nums.length;i++){
            int val = nums[i];
            if(val > 0 ){
               if((val+sum1)>= 0){
                   end = i;
                   sum1 = 0;
                   if(sum>0){
                       sum = sum +val + sum1;
                   }
               } else {
                   sum1 = val+sum1;
               }

                if(true){
                    sum = sum+val;
                } else {
                    begin = i;
                }
            } else {
                sum1 = sum1+val;
            }
        }
        return sum;
    }
}
