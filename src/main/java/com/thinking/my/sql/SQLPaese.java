package com.thinking.my.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author liyong40
 * @Description
 * @date 2022/12/20
 */
public class SQLPaese {

    public static void main(String[] args) {

//        String sql= "select a from cc.table1  t1 join cc.table2 t2 on t1.c = t2.c ";
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
        String dbType = JdbcConstants.MYSQL;
        List<SQLStatement> sqlStatements= SQLUtils.parseStatements(sql, dbType);
        System.out.println(JSON.toJSONString(sqlStatements));
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {

            SQLStatement stmt = stmtList.get(i);
            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
            stmt.accept(visitor);

            //获取表名称
            System.out.println("Tables : " + visitor.getTables().keySet());
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());
        }

        System.out.println(sqlStatements);
    }
}
