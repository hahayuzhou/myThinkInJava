//package com.thinking.my.presto.jdbc;
//
//import com.google.common.collect.Maps;
//import com.sankuai.apa.query.exception.ColumnNotExistException;
//import com.sankuai.apa.query.exception.QueryTrackerException;
//import com.sankuai.apa.query.operator.get.ScanLogic;
//import com.sankuai.apa.query.utils.NumberUtils;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Unused
// */
//public class LabelInputFormatter implements InputFormatter {
//
//    /* project columns' labels */
//    private List<String> labels;
//    /* project columns' types */
//    private List<Integer> types;
//
//    private Map<String, Integer> columnIndex;
//
//    private ScanLogic logic;
//
//    public LabelInputFormatter(ResultSet rs, List<String> labels, ScanLogic logic) {
//        this.labels = labels;
//        this.logic = logic;
//        columnIndex = Maps.newHashMap();
//        types = new ArrayList<>(labels.size());
//        try {
//            ResultSetMetaData meta = rs.getMetaData();
//            for (int i = 0; i < meta.getColumnCount(); i++) {
//                columnIndex.put(meta.getColumnLabel(i + 1), i + 1);
//            }
//            for (String label : this.labels) {
//                Integer index = columnIndex.get(label);
//                if (index == null) {
//                    index = columnIndex.get(label.toUpperCase());
//                    if (index == null) {
//                        throw new ColumnNotExistException(logic.getQueryId(), logic.getScanId(),
//                                logic.getClass().getSimpleName(), logic.getIndex(), label,
//                                columnIndex.keySet().toString());
//                    }
//                }
//                types.add(meta.getColumnType(index));
//            }
//        } catch (SQLException e) {
//            throw new QueryTrackerException(logic.getQueryId(), logic.getScanId(), logic.getClass().getSimpleName(), logic.getIndex(), "Get result meta failed!", e);
//        }
//    }
//
//    @Override
//    public String get(ResultSet rs, int index) {
//        if (index >= types.size()) {
//            throw new QueryTrackerException(logic.getQueryId(), logic.getScanId(), logic.getClass().getSimpleName(), logic.getIndex(), String.format("Column index out of range:%s/%s", index, types.size()));
//        }
//        String value;
//        try {
//            if (types.get(index) == Types.TIMESTAMP) {
//                Timestamp timestamp = rs.getTimestamp(labels.get(index));
//                value = timestamp == null ? "0000-00-00 00:00:00" : timestamp.toString();
//            } else {
//                value = rs.getString(labels.get(index));
//                if (value == null && logic.npReplaceStr != null)
//                    value = logic.npReplaceStr;
//            }
//        } catch (Exception e) {
//            if (e.getMessage().contains("0000-00-00")) return "0000-00-00 00:00:00";
//            throw new QueryTrackerException(logic.getQueryId(), logic.getScanId(), logic.getClass().getSimpleName(), logic.getIndex(), String.format("Format value fail:%s", index), e);
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
