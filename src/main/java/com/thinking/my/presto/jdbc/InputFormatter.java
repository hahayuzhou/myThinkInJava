package com.thinking.my.presto.jdbc;

import java.sql.ResultSet;
import java.util.Map;

/**
 * @author mitong
 * @since 1.4.0
 */
public interface InputFormatter {
    String get(ResultSet rs, int index);

    String get(Map<String, String> record, String label);
}
