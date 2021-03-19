package com.thinking.my.jdbc;

import java.sql.Connection;

public interface Validator {
    /**
     * Validate a connection and return a boolean to indicate if it's valid.
     *
     * @return true if the connection is valid
     */
    public boolean validate(Connection connection, int validateAction);
}
