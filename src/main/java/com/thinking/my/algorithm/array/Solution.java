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

    public static int minArray(int[] numbers) {
        if(numbers.length<=1){

        }
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


}
