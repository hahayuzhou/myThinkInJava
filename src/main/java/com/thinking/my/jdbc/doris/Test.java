package com.thinking.my.jdbc.doris;

import com.alibaba.fastjson.JSON;
import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.block.BlockBuilder;
import io.airlift.slice.Slices;

import java.sql.*;

/**
 * @Description
 * @Author liyong
 * @Date 2021/4/20 8:50 下午
 **/
public class Test {

    public static final int MAX_PAGE_SIZE = 16 * 1024 * 1024;
    public static final int MAX_PAGE_ROWS = 100000;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://doris.st.sankuai.com:8080/ly_test_stage";
    static final String DB_URL = "jdbc:mysql://10.221.76.23:8080/bigo_mtdw_validation";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "doris_mt_olap";
//    static final String USER = "ly_test_stage_r@default_cluster";
//    static final String PASS = "read_2W92iy0d";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            stmt.setQueryTimeout(10);
            String sql;
            sql = "explain select 1";
            long start = System.currentTimeMillis();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Use:"+(System.currentTimeMillis()-start));
            // 展开结果集数据库

//            PageBuilder builder = PageBuilder.withMaxPageSize(MAX_PAGE_SIZE, types);
            int counter = 0;
            while(rs.next()){

                System.out.println(JSON.toJSONString(rs));
                // 通过字段检索
//                long id  = rs.getLong("dt");
//
//
//                // 输出数据
//                System.out.print("long_time: " + id);
                System.out.print("\n");
//                if (builder.isFull() || counter == ScanLogic.MAX_PAGE_ROWS) {
//                    Page page = builder.build();
//                    operator.in(page);
//                    builder.reset();
//                    counter = 0;
//                }
//                builder.declarePosition();
//                for (int i = 0; i < columns.size(); i++) {
//                    BlockBuilder output = builder.getBlockBuilder(i);
//                    String value = formatter.get(rs, i);
//                    if (value == null) {
//                        output.appendNull();
//                    } else {
//                        ScanLogic.STRING.writeSlice(output, Slices.utf8Slice(value));
//                    }
//                }
                counter++;
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}


