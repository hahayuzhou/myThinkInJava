package com.thinking.my.algorithm.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
     *
     * 示例 1:
     *
     * 输入：s = "abaccdeff"
     * 输出：'b'
     * 示例 2:
     *
     * 输入：s = ""
     * 输出：' '
     * 限制：
     *
     * 0 <= s 的长度 <= 50000
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param s
     * @return
     */
    public static char firstUniqChar(String s) {
        if(s==null){
            return ' ';
        }
        char[] chars = s.toCharArray();
        int length = s.length();
        for(int i=0;i<chars.length&&s.length()>0;){
           s = s.replace(chars[i]+"","");
            if(length-s.length()==1){
                return chars[i];
            }
            length = s.length();
            chars = s.toCharArray();


        }
        return ' ';
    }

    public static void main(String[] args) {
        String s = "loveleetcode";
        System.out.println(firstUniqChar(s));
    }




}
