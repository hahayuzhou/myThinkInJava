package com.thinking.my.algorithm.tree;

/**
 * @Description
 * @Author liyong
 * @Date 2020/7/21 7:35 下午
 **/
public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(){}
    TreeNode(int val){
        this.val = val;
    }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
