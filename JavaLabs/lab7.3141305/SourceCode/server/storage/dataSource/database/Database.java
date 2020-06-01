package com.lab.server.storage.dataSource.database;

import com.lab.server.storage.dao.DAOException;
import com.lab.server.storage.dataSource.DataSource;
import com.lab.server.storage.dataSource.DataSourceException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Database class that initialize tables.
 */
public final class Database extends DataSource {
    private final String CREATE_IF_NOT_EXISTS_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS users ("
                    + "id SERIAL NOT NULL PRIMARY KEY, "
                    + "login VARCHAR UNIQUE NOT NULL, "
                    + "password VARCHAR NOT NULL)";
    private final String CREATE_IF_NOT_EXISTS_MUSIC_BANDS_TABLE =
            "CREATE TABLE IF NOT EXISTS music_bands ("
                    + "id SERIAL NOT NULL PRIMARY KEY, "
                    + "owner_id SERIAL NOT NULL, "
                    + "key SERIAL NOT NULL, "
                    + "name VARCHAR NOT NULL CHECK(LENGTH(name)>0), "
                    + "coordinates_x REAL NOT NULL, "
                    + "coordinates_y REAL NOT NULL, "
                    + "creation_date DATE NOT NULL, "
                    + "number_of_participants INT NOT NULL CHECK(number_of_participants>0), "
                    + "genre VARCHAR NULL, "
                    + "album_name VARCHAR NOT NULL CHECK(LENGTH(name)>0), "
                    + "tracks INT NOT NULL CHECK(tracks>0), "
                    + "sales INT NOT NULL CHECK(sales>0), "
                    + "FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE)";

    /**
     * Initializes all tables. NOTE: Order is important.
     *
     * @param user     user login.
     * @param password user password.
     * @throws DataSourceException in case of data source exceptions.
     * @throws DatabaseException   if initialization is incorrect.
     */
    public Database(String user, String password) throws DataSourceException, DatabaseException {
        super(user, password);
        initUsersTable();
        initMusicBandsTable();
    }

    /**
     * Initializes workers table. Creates new one if not exists.
     *
     * @throws DatabaseException if initialization is incorrect
     */
    private void initUsersTable() throws DatabaseException {
        PreparedStatement preparedStatement = getPrepareStatement(CREATE_IF_NOT_EXISTS_USERS_TABLE);

        try {
            preparedStatement.executeUpdate();
        } catch (SQLException | DAOException e) {
            throw new DatabaseException("Ошибка при создании таблицы пользователей.", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
    }

    /**
     * Initializes workers table. Creates new one if not exists.
     *
     * @throws DatabaseException if initialization is incorrect
     */
    private void initMusicBandsTable() throws DatabaseException {
        PreparedStatement preparedStatement = getPrepareStatement(CREATE_IF_NOT_EXISTS_MUSIC_BANDS_TABLE);

        try {
            preparedStatement.executeUpdate();
        } catch (SQLException | DAOException e) {
            throw new DatabaseException("Ошибка при создании таблицы путей.", e);
        } finally {
            closePrepareStatement(preparedStatement);
        }
    }
}
