package com.thinking.my.xml;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2020/11/26 1:04 下午
 **/
public class ControlsIfDagNode extends DagNode {
    /**
     * 逻辑判断模块 决定是进入 queryTree 还是 elseBoock
     */
    private IfBlock ifBlock;
    /**
     * 功能执行模块
     */
    private DagNode dagNode;
    private ControlsIfDagNode elseBlock;


    public BuffaloResponse transform(BuffaloResponse rusult,List<AtomicQuery> atomicQueryList, Map<String, Object> paramMap, Map<String, Object> avriableMap, QueryContext queryContext,BuffaloResponse buffaloResponse){
        if(ifBlock!=null){
            if(ifBlock.canDo(rusult,atomicQueryList,paramMap,avriableMap)){
              return dagNode.toQueryContext(atomicQueryList,paramMap,avriableMap,queryContext,buffaloResponse);
            }else{
               return elseBlock.transform(rusult,atomicQueryList,paramMap,avriableMap,queryContext,buffaloResponse);
            }
        }else{
           return dagNode.toQueryContext(atomicQueryList,paramMap,avriableMap,queryContext,buffaloResponse);
        }
    }
}
