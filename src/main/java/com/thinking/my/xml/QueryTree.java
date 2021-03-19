package com.thinking.my.xml;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2020/11/26 1:22 下午
 **/
public class QueryTree {
    private BuffaloGetDagNode buffaloGetBlock;
    private ControlsIfDagNode controlsIfBlock;
    private List<TransFormDagNode> transFormBlocks;

    public final BuffaloResponse transform(List<AtomicQuery> atomicQueryList, Map<String, Object> paramMap,Map<String, Object> avriableMap,QueryContext queryContext){
        BuffaloResponse rusult = null;
        if(buffaloGetBlock!=null){
//        rusult  =  buffaloGetBlock.execute();
        }
        if(controlsIfBlock!=null){
//           rusult = controlsIfBlock.transform(rusult,atomicQueryList,paramMap,avriableMap,queryContext);
        }
        if(transFormBlocks!=null&&transFormBlocks.size()>0){
            for(TransFormDagNode transFormBlock:transFormBlocks) {
                rusult = transFormBlock.transForm(rusult);
            }
        }
        return rusult;
    }
}
