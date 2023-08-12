package com.thinking.my.sql;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liyong40
 * @Description
 * @date 2023/5/25
 */
public class MTExplainUtil {
    private static final String QUERY_ID_START_TAG = "/**EXP_QUERY_ID";
    private static final String QUERY_ID_END_TAG = "EXP_QUERY_ID**/";

    public static String getQueryId(String sql){
        if (StringUtils.isEmpty(sql)) {
            return "";
        }

        int startIndex =  sql.indexOf(QUERY_ID_START_TAG);
        if (startIndex>=0) {
            int endIndex =  sql.indexOf(QUERY_ID_END_TAG);
            if (endIndex > 0) {
                return sql.substring(startIndex+QUERY_ID_START_TAG.length(),endIndex);
            }
        }
        return "";
    }


    public static void main(String[] args) {
        String sql = "/**EXP_QUERY_ID1234567EXP_QUERY_ID**/ select 1";
        String id = getQueryId(sql);
        System.out.println(id);
    }


}
