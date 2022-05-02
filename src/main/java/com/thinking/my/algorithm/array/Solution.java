package com.thinking.my.algorithm.array;

import com.thinking.my.algorithm.lru.damo.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @Description
 * @Author liyong
 * @Date 2020/7/22 8:56 下午
 **/
public class Solution {
    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     *
     * 给你一个可能存在 重复 元素值的数组 numbers ，它原来是一个升序排列的数组，并按上述情形进行了一次旋转。请返回旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一次旋转，该数组的最小值为1。  
     *
     * 示例 1：
     *
     * 输入：[3,4,5,1,2]
     * 输出：1
     * 示例 2：
     *
     * 输入：[2,2,2,0,1]
     * 输出：0
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param numbers
     * @return
     */
    public static int minArray(int[] numbers) {
        int min=numbers[0];
//        min =  Arrays.stream(numbers).min().getAsInt();
        for(int i = 1;i<numbers.length;i++){
            if(min>numbers[i]){
                return numbers[i];
            }
        }

        return min;
    }

    public static void main(String[] args) {
        System.out.println(hammingWeight(999999999));
        System.out.println(hammingWeight2(999999999));
    }

    /**
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     *
     * 示例:
     *
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        if(grid==null||grid.length == 0 || grid[0].length==0){
            return 0;
        }
        int rows = grid.length,columns = grid[0].length;
        int[][] minPath = new int[rows][columns];
        minPath[0][0] = grid[0][0];
        for(int i = 0;i<rows;i++){
            minPath[i][0] = minPath[i-1][0] + grid[i][0];
        }
        for(int i = 0;i<columns;i++){
            minPath[0][i] = minPath[0][i] + grid[0][i];
        }
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                minPath[i][j] = Math.min(minPath[i - 1][j], minPath[i][j - 1]) + grid[i][j];
            }
        }
        return minPath[rows - 1][columns - 1];

    }

    public static int hammingWeight(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) != 0) {
                ret++;
            }
        }
        return ret;
    }

    public static int hammingWeight2(int n) {
        int ret = 0;
        while (n!=0){
            n = n&(n-1);
            ret++;
        }
        return ret;
    }

    /**
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * 子数组 是数组中的一个连续部分。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     * 示例 2：
     *
     * 输入：nums = [1]
     * 输出：1
     * 示例 3：
     *
     * 输入：nums = [5,4,-1,7,8]
     * 输出：23
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {

        if(nums.length==1){
            return nums[0];
        }

        int sn = nums[0];
        int max= nums[0];
        for (int i = 1; i<nums.length;i++){
            sn = Math.max(sn+nums[i],nums[i]);

            max = Math.max(max,sn);
        }

        return max;

    }


}
