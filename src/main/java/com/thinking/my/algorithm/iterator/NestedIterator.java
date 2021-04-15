package com.thinking.my.algorithm.iterator;

import java.util.*;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/23 8:54 下午
 **/
public class NestedIterator implements Iterator<Integer> {
//    List<Integer> vals;
//    Iterator<Integer> cur;
//
//    public NestedIterator(List<NestedInteger> nestedList) {
//        vals = new ArrayList<>();
//        dsf(nestedList);
//        cur = vals.iterator();
//    }
//
//    private void dsf(List<NestedInteger> nestedList) {
//        for(NestedInteger nestedInteger:nestedList){
//            if(nestedInteger.isInteger()){
//                vals.add(nestedInteger.getInteger());
//            }else{
//                dsf(nestedInteger.getList());
//            }
//        }
//    }
//
//    @Override
//    public boolean hasNext() {
//        return cur.hasNext();
//    }
//
//    @Override
//    public Integer next() {
//        return cur.next();
//    }

    // 存储列表的当前遍历位置
    private Deque<Iterator<NestedInteger>> stack;

    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new LinkedList<Iterator<NestedInteger>>();
        stack.push(nestedList.iterator());
    }

    @Override
    public Integer next() {
        // 由于保证调用 next 之前会调用 hasNext，直接返回栈顶列表的当前元素
        return stack.peek().next().getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()) {
            Iterator<NestedInteger> it = stack.peek();
            if (!it.hasNext()) { // 遍历到当前列表末尾，出栈
                stack.pop();
                continue;
            }
            // 若取出的元素是整数，则通过创建一个额外的列表将其重新放入栈中
            NestedInteger nest = it.next();
            if (nest.isInteger()) {
                List<NestedInteger> list = new ArrayList<NestedInteger>();
                list.add(nest);
                stack.push(list.iterator());
                return true;
            }
            stack.push(nest.getList().iterator());
        }
        return false;
    }


     public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
          public boolean isInteger();

             // @return the single integer that this NestedInteger holds, if it holds a single integer
                // Return null if this NestedInteger holds a nested list
          public Integer getInteger();

             // @return the nested list that this NestedInteger holds, if it holds a nested list
              // Return null if this NestedInteger holds a single integer
          public List<NestedInteger> getList();
  }
}
