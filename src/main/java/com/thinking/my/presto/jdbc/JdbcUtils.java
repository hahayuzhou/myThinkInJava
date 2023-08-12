//package com.thinking.my.presto.jdbc;
//
//import com.facebook.presto.spi.Page;
//import com.facebook.presto.spi.PageBuilder;
//import com.facebook.presto.spi.block.BlockBuilder;
//import com.facebook.presto.spi.type.Type;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.sankuai.apa.query.client.operator.param.Column;
//import com.sankuai.apa.query.client.operator.param.DataTypes;
//import com.sankuai.apa.query.exception.ColumnNotExistException;
//import com.sankuai.apa.query.log.LogUtil;
//import com.sankuai.apa.query.operator.get.KylinLogic;
//import com.sankuai.apa.query.operator.get.ScanLogic;
//import com.sankuai.apa.query.utils.SqlUtils;
//import io.airlift.slice.Slices;
//import org.apache.calcite.sql.parser.SqlParseException;
//import org.apache.commons.collections.CollectionUtils;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class JdbcUtils {
//
//    public static void release(ResultSet rs, Statement stat, Connection conn, Exception ex) {
//        if (ex == null) {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (stat != null) {
//                try {
//                    stat.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//            }
//        }
//    }
//
//    public static void pack(ScanLogic operator, ResultSet rs) throws SQLException {
//        List<Column> columns = operator.getSchema().getOutput();
//        List<Integer> indexes;
//        if (operator.isUseColumnIndex()) {
//            indexes = operator.getColumnIndexes();
//            if (CollectionUtils.isEmpty(columns)) {
//                indexes = Lists.newArrayList();
//                int columnCount = rs.getMetaData().getColumnCount();
//                for (int i = 0; i < columnCount; i++) {
//                    columns.add(new Column(rs.getMetaData().getColumnLabel(i + 1), rs.getMetaData().getColumnLabel(i + 1),
//                            null, null, rs.getMetaData().getColumnType(i + 1)));
//                    indexes.add(i);
//                }
//                operator.setSchemaIO(columns, true);
//            } else {
//                for (int i = 0; i < indexes.size(); i++) {
//                    columns.get(i).setColumnName(rs.getMetaData().getColumnLabel(indexes.get(i) + 1));
//                    columns.get(i).setDataType(rs.getMetaData().getColumnType(indexes.get(i) + 1));
//                }
//            }
//        } else {
//            indexes = Lists.newArrayList();
//            if (CollectionUtils.isEmpty(columns)) {
//                columns = Lists.newArrayList();
//                int columnCount = rs.getMetaData().getColumnCount();
//                for (int i = 0; i < columnCount; i++) {
//                    columns.add(new Column(rs.getMetaData().getColumnLabel(i + 1), rs.getMetaData().getColumnLabel(i + 1), i,
//                            null, null, rs.getMetaData().getColumnType(i + 1)));
//                    indexes.add(i);
//                }
//                try {
//                    if (operator instanceof KylinLogic) {
//                        // Kylin在构建cube期间可能出现jdbc返回的列信息比select的列少的情况
//                        List<String> parsedColumns = SqlUtils.getColumnNames(operator.getSql().replaceFirst(";\\s*$", ""));
//                        if (columns.size() < parsedColumns.size()) {
//                            columns.clear();
//                            columns.addAll(parsedColumns.stream()
//                                    .map(col -> new Column(col, col, null, null, DataTypes.STRING))
//                                    .collect(Collectors.toList()));
//                        }
//                    }
//                } catch (SqlParseException e) {
//                    LogUtil.logError(JdbcUtils.class, e, operator.getQueryId(), operator.getIndex(),
//                            String.format("[Q]:%s [S]:%s SQL parse exception.", operator.getQueryId(), operator.getIndex()));
//                }
//                operator.setSchemaIO(columns, true);
//            } else {
//                Map<String, Integer> columnTypes = Maps.newHashMap();
//                ResultSetMetaData meta = rs.getMetaData();
//                for (int i = 0; i < meta.getColumnCount(); i++) {
//                    columnTypes.put(meta.getColumnLabel(i + 1), i);
//                }
//                for (Column column : columns) {
//                    String label = column.getColumnName();
//                    Integer index = columnTypes.get(label);
//                    if (index == null) {
//                        index = columnTypes.get(label.toUpperCase());
//                        if (index == null) {
//                            throw new ColumnNotExistException(operator.getQueryId(), operator.getScanId(),
//                                    operator.getClass().getSimpleName(), operator.getIndex(),
//                                    label, columnTypes.keySet().toString());
//                        }
//                    }
//                    indexes.add(index);
//                    column.setDataType(meta.getColumnType(index + 1));
//                }
//            }
//        }
//        List<Type> types = new ArrayList<>();
//        for (int i = 0; i < columns.size(); i++) {
//            types.add(ScanLogic.STRING);
//        }
//        InputFormatter formatter = new IndexInputFormatter(rs, indexes, operator);
//        PageBuilder builder = PageBuilder.withMaxPageSize(ScanLogic.MAX_PAGE_SIZE, types);
//        int counter = 0;
//        while (rs.next()) {
//            if (builder.isFull() || counter == ScanLogic.MAX_PAGE_ROWS) {
//                Page page = builder.build();
//                operator.in(page);
//                builder.reset();
//                counter = 0;
//            }
//            builder.declarePosition();
//            for (int i = 0; i < columns.size(); i++) {
//                BlockBuilder output = builder.getBlockBuilder(i);
//                String value = formatter.get(rs, i);
//                if (value == null) {
//                    output.appendNull();
//                } else {
//                    ScanLogic.STRING.writeSlice(output, Slices.utf8Slice(value));
//                }
//            }
//            counter++;
//        }
//        if (counter >= 0) {
//            Page page = builder.build();
//            operator.in(page);
//            builder.reset();
//        }
//    }
//
//    private static int transDataType(int jdbcType) {
//        switch (jdbcType) {
//            case Types.BIT:
//            case Types.INTEGER:
//            case Types.BIGINT:
//            case Types.SMALLINT:
//            case Types.TINYINT: {
//                return DataTypes.INTEGER;
//            }
//            case Types.BOOLEAN: {
//                return DataTypes.BOOLEAN;
//            }
//            case Types.FLOAT:
//            case Types.REAL:
//            case Types.DECIMAL:
//            case Types.NUMERIC:
//            case Types.DOUBLE: {
//                return DataTypes.DOUBLE;
//            }
//            default: {
//                return DataTypes.STRING;
//            }
//        }
//    }
//
//}
