package com.thinking.my.xml;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2020/11/26 1:17 下午
 **/
public class LogicOperationBlock extends IfBlock {
    @Override
    public boolean canDo(BuffaloResponse rusult, List<AtomicQuery> atomicQueryList, Map<String, Object> paramMap, Map<String, Object> avriableMap) {
        return false;
    }
}
