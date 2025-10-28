package com.thinking.my;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liyong40
 * @Description
 * @date 2025/10/28
 */
public class TreeTest {

    class TreeNode{
        private String root;
        private TreeNode left;
        private TreeNode right;
    }

    private HashMap<Integer, List<String>> totalmap = new HashMap<>();

    public void printTree(TreeNode treeNode,int heep){
        int h = heep;
        List list = totalmap.get(heep);
        if (list == null) {
            list = new LinkedList();
            totalmap.put(heep,list);
        }
        if (treeNode != null) {
            System.out.println(treeNode.root);
        }

    }
}
