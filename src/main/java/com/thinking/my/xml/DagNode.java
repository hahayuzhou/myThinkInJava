package com.thinking.my.xml;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2020/12/5 7:57 下午
 **/
public class DagNode {
    private String type;
    private DagNode next;

    /**
     * 定义一个模板方法 实现 DagBlock到QueryContext
     * @param atomicQueryList
     * @param paramMap
     * @param avriableMap
     * @param queryContext
     * @param buffaloResponse
     * @return
     */
    public final BuffaloResponse  toQueryContext(List<AtomicQuery> atomicQueryList, Map<String, Object> paramMap,Map<String, Object> avriableMap,QueryContext queryContext,BuffaloResponse buffaloResponse){
        buffaloResponse = this.transform(atomicQueryList,paramMap,avriableMap,queryContext,buffaloResponse);
        if(next!=null){
           buffaloResponse = next.toQueryContext(atomicQueryList,paramMap,avriableMap,queryContext,buffaloResponse);
        }
        return buffaloResponse;
    }

    protected BuffaloResponse transform(List<AtomicQuery> atomicQueryList, Map<String, Object> paramMap,Map<String, Object> avriableMap,QueryContext queryContext ,BuffaloResponse buffaloResponse){
        throw new UnsupportedOperationException();
    }
}
