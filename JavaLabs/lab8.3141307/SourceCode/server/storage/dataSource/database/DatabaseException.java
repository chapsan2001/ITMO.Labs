package com.lab.server.storage.dataSource.database;

import com.lab.server.storage.dataSource.DataSourceException;

public class DatabaseException extends DataSourceException {
  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }
}
