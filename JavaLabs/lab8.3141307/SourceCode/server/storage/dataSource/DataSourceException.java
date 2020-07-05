package com.lab.server.storage.dataSource;

public class DataSourceException extends RuntimeException {
  public DataSourceException(String message) {
    super(message);
  }

  public DataSourceException(String message, Throwable cause) {
    super(message, cause);
  }
}
