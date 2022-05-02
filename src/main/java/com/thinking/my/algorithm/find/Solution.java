package com.thinking.my.algorithm.find;

/**
 * @Description
 * @Author liyong
 * @Date 2021/12/6 7:52 下午
 **/
public class Solution {

    public static int missingNumber(int[] nums) {

        int start = 0; int end = nums.length-1;
        int mind=end;
        while ((start <= end) && (mind != ( start + end )/2)){

            mind = ( start + end )/2;
            if(nums[mind]>mind){
                end = mind;
            }else{
                start = mind;
            }
        }
        if(end==0){
            return 0;
        }

        return nums[start]+1;
    }


    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }



    public static void main(String[] args) {
        int[] nums = {1,2};
        int a = missingNumber(nums);
        System.out.println(a);
    }







}
