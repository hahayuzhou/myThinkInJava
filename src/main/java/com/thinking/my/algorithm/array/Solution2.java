package com.thinking.my.algorithm.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 *  
 *
 * 示例:
 *
 * 现有矩阵 matrix 如下：
 *
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 *
 * 给定 target = 20，返回 false。
 * 限制：
 *
 * 0 <= n <= 1000
 *
 * 0 <= m <= 1000
 *
 * @Author liyong
 * @Date 2021/12/8 7:19 下午
 **/
public class Solution2 {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {

        if(matrix==null||matrix.length==0){
            return false;
        }

        int rows = matrix.length;
        int columns = matrix[0].length;
        int row = 0;
        int column=columns-1;

        while (row<rows&&column>=0){
            int num = matrix[row][column];
            if(num==target){
                return true;
            }
            if(target>num){
                row++;
            } else {
                column--;
            }
        }
        return false;

    }

    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }

        return false;

    }

    /**
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     *
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     *
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     *
     * 示例 1：
     *
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
     * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
     * 示例 2：
     *
     * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
     * 输出：[1]
     * 解释：需要合并 [1] 和 [] 。
     * 合并结果是 [1] 。
     * 示例 3：
     *
     * 输入：nums1 = [0], m = 0, nums2 = [1], n = 1
     * 输出：[1]
     * 解释：需要合并的数组是 [] 和 [1] 。
     * 合并结果是 [1] 。
     * 注意，因为 m = 0 ，所以 nums1 中没有元素。nums1 中仅存的 0 仅仅是为了确保合并结果可以顺利存放到 nums1 中。
     *  
     *
     * 提示：
     *
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -109 <= nums1[i], nums2[j] <= 109
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if(n==0){
            return;
        }
        if(m == 0 && n != 0 ){
            System.arraycopy(nums2,0,nums1,0,n);
        }
        int j = 0;
        int i = 0;
        int[] nums3 = new int[m+n];
        for(int x = 0;x<m+n;x++){
            if(i==m){
                nums3[x] = nums2[j];
                j++;
                continue;
            }

            if(j == n){
                nums3[x] = nums1[i];
                i++;
                continue;
            }


            if(i==m||nums1[i]>nums2[j]){
                nums3[x] = nums2[j];
                j++;
            } else  {
                nums3[x] = nums1[i];
                i++;
            }
        }

        System.arraycopy(nums3,0,nums1,0,m+n);
    }

    /**
     * 给你两个整数数组 nums1 和 nums2 ，请你以数组形式返回两数组的交集。返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。可以不考虑输出结果的顺序。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2,2]
     * 示例 2:
     *
     * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出：[4,9]
     *  
     *
     * 提示：
     *
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 1000
     *  
     *
     * 进阶：
     *
     * 如果给定的数组已经排好序呢？你将如何优化你的算法？
     * 如果 nums1 的大小比 nums2 小，哪种方法更优？
     * 如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
     *
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {
       int[] nums = new int[nums1.length+nums2.length];
       Map<Integer,Integer> map = new HashMap<>();
       int index = 0;
       if (nums1.length < nums2.length) {
           for(int i = 0;i<nums1.length;i++){
               int n = map.getOrDefault(nums1[i],0) + 1;
               map.put(nums1[i],n);
           }

           for(int i = 0;i<nums2.length;i++){
               int n = map.getOrDefault(nums2[i],0) - 1;
               if(n == -1){

               } else {
                   map.put(nums1[i],n);
                   nums[index] = nums2[i];
                   index++;
               }
           }
       }
       return Arrays.copyOfRange(nums,0,index);
    }
}
