package com.thinking.my.algorithm.string;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2021/11/29 8:45 下午
 **/
public class Solution {
    public String replaceSpace(String s) {
        StringBuilder sbr = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c:chars){
            if (c == ' '){
                sbr.append("20%");
            } else {
                sbr.append(c);
            }
        }
        return sbr.toString();
    }

    public int findRepeatNumber(int[] nums) {
        Map<Integer,Integer> maps = new HashMap<>(nums.length);
        for(int i=0;i<nums.length;i++){
            if(maps.get(nums[i])!=null){
                return nums[i];
            } else {
                maps.put(nums[i],nums[i]);
            }
        }
        return -1;
    }

    public int search(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false) - 1;
        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target) {
            return rightIdx - leftIdx + 1;
        }
        return 0;
    }

    public int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0,right = nums.length-1,idx=nums.length;
        int mid ;
        while (left<=right){
            mid  = (left+right)/2;
            if(nums[mid]>target || ( lower && nums[mid] >= target)){
                right = mid-1;
                idx = mid;
            } else {
                left = mid + 1;
            }
        }
        return idx;
    }




}
