package com.thinking.my.xml;

import com.thinking.my.xml.IfBlock;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2020/12/4 3:16 下午
 **/
public class ResponseIsEmptyBlock extends IfBlock {
    private Filed filed;
    @Override
    public boolean canDo(BuffaloResponse rusult, List<AtomicQuery> atomicQueryList, Map<String, Object> paramMap, Map<String, Object> avriableMap) {
        return false;
    }
}
