package com.thinking.my.regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liyong40
 * @Description
 * @date 2022/12/20
 */
public class SQLParse {


    /**
     *   // * 零次或多次匹配前面的字符或子表达式。例如，zo* 匹配"z"和"zoo"。* 等效于 {0,}。
     *  // ? 零次或一次匹配前面的字符或子表达式。例如，"do(es)?"匹配"do"或"does"中的"do"。? 等效于 {0,1}。
     *  //  + 一次或多次匹配前面的字符或子表达式。例如，"zo+"与"zo"和"zoo"匹配，但与"z"不匹配。+ 等效于 {1,}。
     */
//    private static final String TABLE_NAME_REGX = "[\\s|\\n]?(from|FROM|join|JOIN)(\\s?|\\n?)+[a-z|A-Z|_|-|0-9|.]*[\\s|\\n]?";

    private static final String TABLE_NAME_REGX = "[\\s|\\n]{1}(from|FROM|join|JOIN)[\\s|\\n]+[a-z|A-Z|_|-|0-9|\\.]*[\\s|\\n]?";

    public static List<String> parserTables(String sql) {
        Set<String> tableNameSet = new HashSet<>();
        sql = sql.replace(" ","  ");
        List<String> tableList = parser(sql, TABLE_NAME_REGX, true);
        for (String tableText : tableList) {
            if (tableText == null) continue;
            String tableTrim = tableText.trim();
            String[] arr = tableTrim.split("[\\s|\n]+");
            if (arr.length != 2) continue;
            tableNameSet.add(arr[1]);
        }
        return new ArrayList<>(tableNameSet);
    }

    private static List<String> parser(String sql, String regx, boolean throwException) {
        List<String> parserResult = new ArrayList<>();
        try {
            Pattern compile = Pattern.compile(regx);
            Matcher matcher = compile.matcher(sql);
            while (matcher.find()) {
                String name = matcher.group();
                parserResult.add(name);
            }
            return parserResult;
        } catch (Exception exp) {
            if (throwException) throw  exp;
           exp.printStackTrace();
        }
        return parserResult;
    }

    public static void main(String[] args) {
//        String sql = "select a,from_to from bjointablefromtable";
//        String sql = "select a from tt.b join tt.c on b.id = c.id";
//        String sql = "select a from tt.b join tt.c on b.id = c.id join (select mm from kk) jj on jj.mm = tt.b.mm";

        String sql= "<#if scheme_code = 'call'>\n" +
                "SELECT\n" +
                "    wbr_sub_bu_id AS work_bu_id,\n" +
                "    wbr_sub_bu_name AS work_bu_name,\n" +
                "    ip_city AS city,\n" +
                "    SUM(call_city_passive_service_cnt) AS all_service_cnt,\n" +
                "    SUM(call_city_trans_staff_passive_service_cnt) AS transfer_staff_cnt,\n" +
                "    SUM(call_city_trans_staff_passive_service_cnt) * 1.0 / SUM(call_city_passive_service_cnt) AS transfer_staff_rate,\n" +
                "    SUM(call_evaluation_cnt) AS smart_evaluation_cnt,\n" +
                "    SUM(call_nps_very_unsatisfy_cnt) AS smart_not_satisfy_cnt,\n" +
                "    SUM(call_nps_very_unsatisfy_cnt) * 1.0 / SUM(call_evaluation_cnt) AS smart_not_satisfy_rate\n" +
                "FROM\n" +
                "    app_api_peak_change_rank_city_5mins_d\n" +
                "WHERE\n" +
                "    partition_date BETWEEN TO_DATE('$begin_time') AND TO_DATE('$end_time')\n" +
                "AND five_mins_time_piece BETWEEN '$begin_time' AND '$end_time'\n" +
                "AND wbr_sub_bu_id IS NOT NULL\n" +
                "GROUP BY\n" +
                "    wbr_sub_bu_id,\n" +
                "    wbr_sub_bu_name,\n" +
                "    ip_city\n" +
                "<#elseif scheme_code = 'chat'>\n" +
                "SELECT\n" +
                "    wbr_sub_bu_id AS work_bu_id,\n" +
                "    wbr_sub_bu_name AS work_bu_name,\n" +
                "    ip_city AS city,\n" +
                "    SUM(chat_city_passive_service_cnt) AS all_service_cnt,\n" +
                "    SUM(chat_city_trans_staff_passive_service_cnt) AS transfer_staff_cnt,\n" +
                "    SUM(chat_city_trans_staff_passive_service_cnt) * 1.0 / SUM(chat_city_passive_service_cnt) AS transfer_staff_rate,\n" +
                "    SUM(chat_evaluation_cnt) AS smart_evaluation_cnt,\n" +
                "    SUM(chat_nps_very_unsatisfy_cnt) AS smart_not_satisfy_cnt,\n" +
                "    SUM(chat_nps_very_unsatisfy_cnt) * 1.0 / SUM(chat_evaluation_cnt) AS smart_not_satisfy_rate\n" +
                "FROM\n" +
                "    app_api_peak_change_rank_city_5mins_d\n" +
                "WHERE\n" +
                "    partition_date BETWEEN TO_DATE('$begin_time') AND TO_DATE('$end_time')\n" +
                "AND five_mins_time_piece BETWEEN '$begin_time' AND '$end_time'\n" +
                "AND wbr_sub_bu_id IS NOT NULL\n" +
                "GROUP BY\n" +
                "    wbr_sub_bu_id,\n" +
                "    wbr_sub_bu_name,\n" +
                "    ip_city\n" +
                "</#if>";

//        ParseResult parseResult =   parserVariable(sql);

        List<String> list = parserTables(sql);

        System.out.println(list);
//        System.out.println(parseResult);





    }

}
