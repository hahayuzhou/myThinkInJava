package com.thinking.my.xml;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2020/11/26 1:11 下午
 **/
public abstract class IfBlock {
    /**
     * 获取逻辑块的最终结果
     * @return
     */
    public abstract boolean canDo(BuffaloResponse rusult, List<AtomicQuery> atomicQueryList, Map<String, Object> paramMap, Map<String, Object> avriableMap) ;
}
