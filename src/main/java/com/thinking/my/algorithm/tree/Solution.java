package com.thinking.my.algorithm.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树
 *
 * 示例：
 * 输入：3
 * 输出：
 * [
 *   [1,null,3,2],
 *   [3,2,null,1],
 *   [3,1,null,null,2],
 *   [2,1,3],
 *   [1,null,2,null,3]
 * ]
 * 解释：
 * 以上的输出对应以下 5 种不同结构的二叉搜索树：
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-binary-search-trees-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Author liyong
 * @Date 2020/7/21 7:37 下午
 **/
public class Solution {
    /**
     * 方法一：递归
     * 思路与算法
     *
     * 二叉搜索树关键性质是根节点的的值大于左子树所有节点的值，小于右子树所有节点的值，且左子树和右子树也同样为二叉搜索树。因此在生成所有可行的二叉搜索树的时候，假设当前序列长度为 nn，如果我们枚举根节点的值为 ii，那么根据二叉搜索树的性质我们可以知道左子树的节点值的集合为 [1 \ldots i-1][1…i−1]，右子树的节点值的集合为 [i+1 \ldots n][i+1…n]。而左子树和右子树的生成相较于原问题是一个序列长度缩小的子问题，因此我们可以想到用递归的方法来解决这道题目。
     *
     * 我们定义 generateTrees(start, end) 函数表示当前值的集合为 [\textit{start},\textit{end}][start,end]，返回序列 [\textit{start},\textit{end}][start,end] 生成的所有可行的二叉搜索树。按照上文的思路，我们考虑枚举 [\textit{start},\textit{end}][start,end] 中的值 ii 为当前二叉搜索树的根，那么序列划分为了 [\textit{start},i-1][start,i−1] 和 [i+1,\textit{end}][i+1,end] 两部分。我们递归调用这两部分，即 generateTrees(start, i - 1) 和 generateTrees(i + 1, end)，获得所有可行的左子树和可行的右子树，那么最后一步我们只要从可行左子树集合中选一棵，再从可行右子树集合中选一棵拼接到根节点上，并将生成的二叉搜索树放入答案数组即可。
     *
     * 递归的入口即为 generateTrees(1, n)，出口为当 \textit{start}>\textit{end}start>end 的时候，当前二叉搜索树为空，返回空节点即可。
     *
     * @param n
     * @return
     */
    public static List<TreeNode> generateTrees1(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees1(1, n);

    }

    public static List<TreeNode> generateTrees1(int start ,int end) {
        List<TreeNode> allTrees = new LinkedList<>();
        if(start>end){
            allTrees.add(null);
            return allTrees;
        }

        //枚举可行根节点
        for(int i = start; i<= end;i++){
            //获得所有可行的左子树集合
            List<TreeNode> leftTrees = generateTrees1(start,i-1);
            //获得所有可行的右子树集合
            List<TreeNode> rightTrees = generateTrees1(i+1,end);
            // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
            int finalI = i;
//          allTrees.addAll(leftTrees.stream().flatMap(left->rightTrees.stream().map(right->{
//                TreeNode currTree = new TreeNode(finalI);
//                currTree.left = left;
//                currTree.right = right;
//                return currTree;
//
//            })).collect(Collectors.toList()));

           // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = left;
                    currTree.right = right;
                    allTrees.add(currTree);
                }
            }


        }

        return allTrees;
    }



    public String reverseVowels(String s) {
        int n = s.length();
        char[] arr = s.toCharArray();
        int i = 0,j= n-1;
        while(i<j){
            while (i<j&&!isVowel(arr[i])){
                i++;
            }

            while (j>i && !isVowel(arr[j])){
                j--;
            }
            swap(arr,i,j);
            i++;
            j--;
        }
        return new String(arr);

    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private boolean isVowel(char c) {
        char[] arr = {'a','e','i','o','u'};
        for (char c1 : arr) {
            if(c1 == c){
                return true;
            }
        }
        return false;
    }

        public boolean isBalanced(TreeNode root) {
            if (root == null) {
                return true;
            } else {
                return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
            }
        }

        public int height(TreeNode root) {
            if (root == null) {
                return 0;
            } else {
                return Math.max(height(root.left), height(root.right)) + 1;
            }
        }

    /**
     * 给定一个字符串 s 和一个整数 k，从字符串开头算起，每 2k 个字符反转前 k 个字符。
     *
     * 如果剩余字符少于 k 个，则将剩余字符全部反转。
     * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reverse-string-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param s
     * @param k
     * @return
     */
    public String reverseStr(String s, int k) {
        int n = s.length();
        char[] arr = s.toCharArray();
        for(int i = 0;i<n;i=i+2*k){
            reverse(arr,i,Math.min(i+k,n)-1);
        }
        return new String(arr);

    }

    public void reverse(char[] arr,int i,int j){
        while (i<j){
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i++;
            j--;

        }
    }



    public static void main(String[] args) {
        System.out.println(numTrees(3));
    }

    public static int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;

        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }





}
