package com.thinking.my.algorithm.sort;

import java.util.Arrays;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/26 9:24 下午
 **/
public class TestSort{
        public String minNumber(int[] nums) {
            String[] strs = new String[nums.length];
            for(int i = 0; i < nums.length; i++)
                strs[i] = String.valueOf(nums[i]);
            Arrays.sort(strs, (x, y) -> (x + y).compareTo(y + x));
            StringBuilder res = new StringBuilder();
            for(String s : strs)
                res.append(s);
            return res.toString();
        }
}
