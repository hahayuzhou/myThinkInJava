package com.thinking.my.algorithm.sum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description Given nums = [2, 7, 11, 15], target = 9,
 *
 *  Because nums[0] + nums[1] = 2 + 7 = 9,
 *   return [0, 1].
 * @Author liyong
 * @Date 2020/5/26 8:33 下午
 **/
public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(i+1<nums.length&&nums[i]+nums[i+1] == target){
                return new int[]{i,i+1};
            }else if(map.containsKey(target-nums[i])){
                return new int[]{i,map.get(target-nums[i])};
            }else{
                map.put(nums[i],i);
            }
        }
        return new int[0];
    }



}
