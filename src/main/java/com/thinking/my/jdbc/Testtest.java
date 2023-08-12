package com.thinking.my.jdbc;

import com.mysql.jdbc.StringUtils;

/**
 * @author liyong40
 * @Description
 * @date 2023/5/25
 */
public class Testtest {

    public static void main(String[] args) {
        String sql = "select 1 from aaa";
        char a = firstAlphaCharUc(sql,findStartOfStatement(sql));
        System.out.println(a);

    }

    public static char firstAlphaCharUc(String searchIn, int startAt) {
        if (searchIn == null) {
            return 0;
        }

        int length = searchIn.length();

        for (int i = startAt; i < length; i++) {
            char c = searchIn.charAt(i);

            if (Character.isLetter(c)) {
                return Character.toUpperCase(c);
            }
        }

        return 0;
    }

    public static int findStartOfStatement(String sql) {
        int statementStartPos = 0;

        if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
            statementStartPos = sql.indexOf("*/");

            if (statementStartPos == -1) {
                statementStartPos = 0;
            } else {
                statementStartPos += 2;
            }
        } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) {
            statementStartPos = sql.indexOf('\n');

            if (statementStartPos == -1) {
                statementStartPos = sql.indexOf('\r');

                if (statementStartPos == -1) {
                    statementStartPos = 0;
                }
            }
        }

        return statementStartPos;
    }


}
