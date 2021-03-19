package com.thinking.my.xml;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liyong
 * @Date 2020/12/5 8:06 下午
 **/
public class AtomicQuery {
    String domainName;
    String key; // 每个DAG中的 GET算子块都会生成一个唯一key(DAG查询中唯一的key), 用于区分同一个DAG中有多个相同domainName的情况
    List<String> selectList;
    List whereList;
    Map controlMap;
}
