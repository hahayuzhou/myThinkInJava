package com.thinking.my.algorithm.sum;

import java.util.*;

/**
 * @Description
 * @Author liyong
 * @Date 2020/6/1 9:00 下午
 **/
public class Solution {

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     *  *
     *  * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     *  *
     *  * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *  *
     *  * 示例：
     *  *
     *  * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     *  * 输出：7 -> 0 -> 8
     *  * 原因：342 + 465 = 807
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        int n1 = l1.val;
        int n2 = l2.val;
        int k = (n1+n2)/10;
        int n = (n1+n2)%10;
        ListNode r = new ListNode(n);
        ccc(l1.next,l2.next,k,r);
        return r;
    }

    public void ccc(ListNode l1, ListNode l2,int k,ListNode r){

        if(l1!=null||l2!=null){
            int n1 = 0;
            int n2 = 0;
            ListNode l1Next = null;
            ListNode l2Next = null;
            if(l1!=null){
                n1 = l1.val;
                l1Next = l1.next;
            }
            if(l2!=null){
                n2 = l2.val;
                l2Next = l2.next;
            }
            int n = (n1+n2+k)%10;
            r.next = new ListNode(n);
            k = (n1+n2+k)/10;
            ccc(l1Next,l2Next,k,r.next);
        }else{
            if(k!=0){
                r.next = new ListNode(k);
            }
        }
    }

    /**
     * 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     *
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param s
     * @return
     */
//    public static int lengthOfLongestSubstring(String s) {
//        byte[] bytes =  s.getBytes();
//        int max = 0;
//        List<Byte> list = new ArrayList<>(s.length());
//        list.add(bytes[0]);
//        for (int i=1;i<bytes.length;i++) {
//            if(list.get(0)==bytes[i]){
//                max=max>=list.size()?max:list.size();
//                list.remove(bytes[i]);
//                list.add(bytes[i]);
//            }else{
//                list.add(bytes[i]);
//            }
//        }
//        return max>=list.size()?max:list.size();
//    }

    public static int lengthOfLongestSubstring(String s) {

        if(s.length()==1){
            return 1;
        }
        byte[] bytes =  s.getBytes();
        Map<Byte,Integer> map = new HashMap<>();
        int max = 0;
        int n = 0;
        int i = 1;
        for(;i<=bytes.length;i++){

            Integer a = map.get(bytes[i-1]);
            if(a!=null&&a>n){
                max = max>=i-n-1?max:i-n-1;
                n = a;
            }
            if(s.length()==i){
              max = max>=i-n?max:i-n;
              break;
            }
            map.put(bytes[i-1],i);

        }

        return   max;
    }
    public static int lengthOfLongestSubstring2(String s) {

        if(s.length()==1){
            return 1;
        }
        Map<String,Integer> map = new HashMap<>();
        int max = 0;
        int n = 0;
        int i = 1;
        for(;i<=s.length();i++){
            String str = s.substring(i-1,i);
            Integer a = map.get(str);
            if(a!=null&&a>n){
                max = max>=i-n-1?max:i-n-1;
                n = a;

            }
            if(s.length()==i){
              max = max>=i-n?max:i-n;
              break;
            }

            map.put(str,i);

        }

        return   max;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1==null||nums1.length==0){
           return findMedianSortedArrays(nums2);
        }
        if(nums2==null||nums2.length==0){
           return findMedianSortedArrays(nums1);
        }
        int length1 = nums1.length;
        int length2 = nums2.length;

        int [] nums = new int[length1+length2];
        int i=0;
        int j=0;
        int k=0;
        for(;i<length1;i++){

            while (j<length2&&nums1[i]>=nums2[j]){
                nums[k]=nums2[j];
                j++;
                k++;
            }
            nums[k]=nums1[i];
            k++;
        }
        while (j<length2){
            nums[k]=nums2[j];
            j++;
            k++;
        }
        return findMedianSortedArrays(nums);
    }

    public static double findMedianSortedArrays(int[] nums1) {
        int n=nums1.length;
        if(n%2==0){
            return (nums1[n/2-1]+nums1[n/2])/2.0;
        }else{
            return nums1[n/2];
        }
    }


    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     *
     * 输入: "cbbd"
     * 输出: "bb"
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if(s.length()<=1){
            return s;
        }
        String[] strr = new String[s.length()];
        int max = 0;
        int begin = 0;
        int end = 0;
        Map<String,List<Integer>> map = new HashMap<>();
        cA:
        for(int i=0;i<s.length();i++){
            String str = s.substring(i,i+1);
            strr[i] = str;
            List<Integer> list = map.get(str);
            if(list==null){
                list = new ArrayList<>();
                list.add(i);
                map.put(str,list);
            }else{
                list.add(i);
                cB:
               for(int j=0;j<list.size()-1;j++){
                   int k=list.get(j);
                   if((i-k)<=2){
                       if(max<i-k+1){
                           max = i-k+1;
                           begin = k;
                           end = i;
                       }
                       continue cA;
                   }
                   int c = i-1;
                   int max2 = 2;
                   k++;
                   while (k<c){
                       if(strr[k++].equals(strr[c--])){
                           max2 = max2+2;
                           if(c==k){
                               max2++;
                               break;
                           }
                       }else{
                           continue cB;
                       }
                   }
                   if(max<max2){
                       max = max2;
                       begin = list.get(j);
                       end = i;
                   }
                   continue cA;
               }
            }
        }

        if(max==0){
            return strr[0];
        }
        StringBuilder stringBuilder = new StringBuilder(end-begin+1);

        for(int i=0;i<max;i++){
            stringBuilder.append(strr[begin+i]);
        }
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
//        System.out.println(findMedianSortedArrays(new int[]{1,2},new int[]{3,4}));

        System.out.println(longestPalindrome("abababa"));
        System.out.println(longestPalindrome("abc"));
        System.out.println(longestPalindrome("aaaa"));
        System.out.println(longestPalindrome("babadada"));
        System.out.println(longestPalindrome("abcda"));

    }


     class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
}
