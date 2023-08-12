//package com.thinking.my.presto.jdbc;
//
//import com.sankuai.apa.query.exception.QueryTrackerException;
//import com.sankuai.apa.query.log.LogUtil;
//import com.sankuai.apa.query.operator.get.ScanLogic;
//import com.sankuai.apa.query.utils.NumberUtils;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class IndexInputFormatter implements InputFormatter {
//
//    /* project columns' indexes */
//    private List<Integer> indexes;
//    /* project columns' types */
//    private List<Integer> types;
//
//    private ScanLogic logic;
//
//    public IndexInputFormatter(ResultSet rs, List<Integer> indexes, ScanLogic logic) {
//        this.indexes = indexes;
//        this.logic = logic;
//        types = new ArrayList<>(indexes.size());
//        try {
//            ResultSetMetaData meta = rs.getMetaData();
//            for (Integer index : this.indexes) {
//                types.add(meta.getColumnType(index + 1));
//            }
//        } catch (SQLException e) {
//            throw new QueryTrackerException(logic.getQueryId(), logic.getScanId(), logic.getClass().getSimpleName(), logic.getIndex(), "Get result meta failed!", e);
//        }
//    }
//
//    public IndexInputFormatter(ScanLogic logic) {
//        this.logic = logic;
//    }
//
//    @Override
//    public String get(ResultSet rs, int index) {
//        if (index >= types.size()) {
//            throw new QueryTrackerException(logic.getQueryId(), logic.getScanId(), logic.getClass().getSimpleName(), logic.getIndex(), String.format("Column index out of range:%s/%s", index, types.size()));
//        }
//        String value = null;
//        try {
//            if (types.get(index) == Types.TIMESTAMP) {
//                Timestamp timestamp = rs.getTimestamp(indexes.get(index) + 1);
//                value = timestamp == null ? "0000-00-00 00:00:00" : timestamp.toString();
//            } else {
//                value = rs.getString(indexes.get(index) + 1);
//                if (value == null && logic.npReplaceStr != null)
//                    value = logic.npReplaceStr;
//            }
//        } catch (SQLException e) {
//            if (e.getMessage().contains("0000-00-00")) {
//                return "0000-00-00 00:00:00";
//            }
//            LogUtil.logError(this.getClass(), e, logic.getQueryId(), logic.getScanId(),
//                    String.format("[Q]:%s [S]:%s format value fail:%s", logic.getQueryId(), logic.getScanId(), index));
//        }
//        return value;
//    }
//
//    @Override
//    public String get(Map<String, String> record, String label) {
//        String value = record.get(label) == null ? null : String.valueOf(record.get(label));
//        if (value == null && logic.npReplaceStr != null)
//            value = logic.npReplaceStr;
//        if (NumberUtils.isInt(value)) {
//            value = value.substring(0, value.indexOf("."));
//        }
//        return value;
//    }
//
//}
