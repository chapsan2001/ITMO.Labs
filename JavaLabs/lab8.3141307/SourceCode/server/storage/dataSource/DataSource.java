package com.lab.server.storage.dataSource;

import com.lab.server.storage.dao.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DataSource {
  protected final Connection connection;
  private final String url;
  private final String user;
  private final String password;

  public DataSource(String url, String user, String password) throws DataSourceException {
    this.url = url;
    this.user = user;
    this.password = password;

    connection = setupConnection();
  }

  /**
   * Setups connection with database. NOTE: Url, user and password required.
   *
   * @return New Connection with database.
   * @throws DataSourceException if connection is incorrect.
   */
  private Connection setupConnection() throws DataSourceException {
    Connection connection;

    try {
      connection = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
      throw new DataSourceException("Error connecting to the database.", e);
    }

    return connection;
  }

  /**
   * Returns prepared statement for specified sql request.
   *
   * @param sqlStatement specified sql request.
   * @return Prepared statement for specified sql request.
   * @throws DAOException if prepare was incorrect.
   */
  public final PreparedStatement getPrepareStatement(String sqlStatement) throws DAOException {
    PreparedStatement preparedStatement;
    try {
      preparedStatement = connection.prepareStatement(sqlStatement);
    } catch (SQLException e) {
      throw new DAOException("Error preparing SQL statement.");
    }

    return preparedStatement;
  }

  /**
   * Returns prepared statement for specified sql request and statement.
   *
   * @param sqlStatement specified sql request.
   * @param statement concrete statement;
   * @return Prepared statement for specified sql request.
   * @throws DAOException if prepare was incorrect.
   */
  public final PreparedStatement getPrepareStatement(String sqlStatement, int statement)
      throws DAOException {
    PreparedStatement preparedStatement;
    try {
      preparedStatement = connection.prepareStatement(sqlStatement, statement);
    } catch (SQLException e) {
      throw new DAOException("Error preparing SQL statement.");
    }

    return preparedStatement;
  }

  /**
   * Closes prepared statement.
   *
   * @param preparedStatement concrete prepared statement.
   * @throws DAOException if close was incorrect.
   */
  public final void closePrepareStatement(PreparedStatement preparedStatement) throws DAOException {
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        throw new DAOException("Error closing SQL statement.");
      }
    }
  }

  /**
   * Closes connection with database.
   *
   * @throws DataSourceException if connection is incorrect
   */
  public final void closeConnection() throws DataSourceException {
    if (connection == null) return;
    try {
      connection.close();
    } catch (SQLException e) {
      throw new DataSourceException("Error closing database connection.");
    }
  }
}
