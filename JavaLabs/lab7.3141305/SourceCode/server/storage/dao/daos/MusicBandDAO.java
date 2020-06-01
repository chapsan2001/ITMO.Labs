package com.lab.server.storage.dao.daos;

import com.lab.common.musicBand.Album;
import com.lab.common.musicBand.Coordinates;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicGenre;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.server.storage.dao.DAOException;
import com.lab.server.storage.dataSource.DataSource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public final class MusicBandDAO {
    private static final String SELECT_ALL = "SELECT * FROM music_bands";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    private static final String SELECT_BY_KEY = SELECT_ALL + " WHERE key = ?";
    private static final String INSERT =
            "INSERT INTO music_bands (owner_id, key, name, coordinates_x, coordinates_y, creation_date, number_of_participants, genre, album_name, tracks, sales) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE music_bands SET owner_id = ?, key = ?, name = ?, coordinates_x = ?, coordinates_y = ?, creation_date = ?, number_of_participants = ?, genre = ?, album_name = ?, tracks = ?, sales = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM music_bands WHERE id  = ?";
    private final DataSource dataSource;

    public MusicBandDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<Integer, MusicBand> getAll() throws DAOException {
        Map<Integer, MusicBand> musicBands = new HashMap<>();
        PreparedStatement preparedStatement = dataSource.getPrepareStatement(SELECT_ALL);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MusicBand musicBand = new MusicBand();

                musicBand.setId(resultSet.getInt(1));
                musicBand.setOwnerId(resultSet.getLong(2));
                musicBand.setKey(resultSet.getInt(3));
                musicBand.setName(resultSet.getString(4));

                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getLong(5));
                coordinates.setY(resultSet.getFloat(6));
                musicBand.setCoordinates(coordinates);

                musicBand.setCreationDate(resultSet.getDate(7).toLocalDate());
                musicBand.setNumberOfParticipants(resultSet.getLong(8));

                if (resultSet.getString(9) != null) {
                    musicBand.setGenre(MusicGenre.valueOf(resultSet.getString(9)));
                }

                Album album = new Album();
                album.setName(resultSet.getString(10));
                album.setSales(resultSet.getFloat(11));
                album.setTracks(resultSet.getInt(12));
                musicBand.setBestAlbum(album);

                musicBands.put(musicBand.getKey(), musicBand);
            }
        } catch (SQLException | ValidationException e) {
            throw new DAOException("Ошибка при получении всех путей из базы данных.");
        } finally {
            dataSource.closePrepareStatement(preparedStatement);
        }

        return musicBands;
    }

    public MusicBand getById(long id) throws DAOException {
        MusicBand musicBand = null;
        PreparedStatement preparedStatement = dataSource.getPrepareStatement(SELECT_BY_ID);

        try {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                musicBand = new MusicBand();

                musicBand.setId(resultSet.getInt(1));
                musicBand.setOwnerId(resultSet.getLong(2));
                musicBand.setKey(resultSet.getInt(3));
                musicBand.setName(resultSet.getString(4));

                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getLong(5));
                coordinates.setY(resultSet.getFloat(6));
                musicBand.setCoordinates(coordinates);

                musicBand.setCreationDate(resultSet.getDate(7).toLocalDate());
                musicBand.setNumberOfParticipants(resultSet.getLong(8));

                if (resultSet.getString(9) != null) {
                    musicBand.setGenre(MusicGenre.valueOf(resultSet.getString(9)));
                }

                Album album = new Album();
                album.setName(resultSet.getString(10));
                album.setSales(resultSet.getFloat(11));
                album.setTracks(resultSet.getInt(12));
                musicBand.setBestAlbum(album);
            }
        } catch (SQLException | ValidationException e) {
            throw new DAOException("Ошибка при получении пути по id.");
        } finally {
            dataSource.closePrepareStatement(preparedStatement);
        }

        return musicBand;
    }

    public MusicBand getByKey(int key) throws DAOException {
        MusicBand musicBand = null;
        PreparedStatement preparedStatement = dataSource.getPrepareStatement(SELECT_BY_KEY);

        try {
            preparedStatement.setInt(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                musicBand = new MusicBand();

                musicBand.setId(resultSet.getInt(1));
                musicBand.setOwnerId(resultSet.getLong(2));
                musicBand.setKey(resultSet.getInt(3));
                musicBand.setName(resultSet.getString(4));

                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getLong(5));
                coordinates.setY(resultSet.getFloat(6));
                musicBand.setCoordinates(coordinates);

                musicBand.setCreationDate(resultSet.getDate(7).toLocalDate());
                musicBand.setNumberOfParticipants(resultSet.getLong(8));

                if (resultSet.getString(9) != null) {
                    musicBand.setGenre(MusicGenre.valueOf(resultSet.getString(9)));
                }

                Album album = new Album();
                album.setName(resultSet.getString(10));
                album.setSales(resultSet.getFloat(11));
                album.setTracks(resultSet.getInt(12));
                musicBand.setBestAlbum(album);
            }
        } catch (SQLException | ValidationException e) {
            throw new DAOException("Ошибка при получении пути по id.");
        } finally {
            dataSource.closePrepareStatement(preparedStatement);
        }

        return musicBand;
    }

    public MusicBand insert(MusicBand musicBand) throws DAOException {
        PreparedStatement preparedStatement =
                dataSource.getPrepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        try {
            preparedStatement.setLong(1, musicBand.getOwnerId());
            preparedStatement.setInt(2, musicBand.getKey());
            preparedStatement.setString(3, musicBand.getName());

            preparedStatement.setLong(4, musicBand.getCoordinates().getX());
            preparedStatement.setFloat(5, musicBand.getCoordinates().getY());

            preparedStatement.setDate(6, Date.valueOf(musicBand.getCreationDate()));
            preparedStatement.setLong(7, musicBand.getNumberOfParticipants());

            if (musicBand.getGenre() != null) {
                preparedStatement.setString(8, musicBand.getGenre().toString());
            } else {
                preparedStatement.setString(8, null);
            }

            preparedStatement.setString(9, musicBand.getBestAlbum().getName());
            preparedStatement.setInt(10, musicBand.getBestAlbum().getTracks());
            preparedStatement.setFloat(11, musicBand.getBestAlbum().getSales());

            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                musicBand.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException | ValidationException e) {
            throw new DAOException("Ошибка при добавлении пути в базу данных.");
        } finally {
            dataSource.closePrepareStatement(preparedStatement);
        }

        return musicBand;
    }

    public void update(MusicBand musicBand) throws DAOException {
        PreparedStatement preparedStatement = dataSource.getPrepareStatement(UPDATE);

        try {
            preparedStatement.setLong(1, musicBand.getOwnerId());
            preparedStatement.setInt(2, musicBand.getKey());
            preparedStatement.setString(3, musicBand.getName());

            preparedStatement.setLong(4, musicBand.getCoordinates().getX());
            preparedStatement.setFloat(5, musicBand.getCoordinates().getY());

            preparedStatement.setDate(6, Date.valueOf(musicBand.getCreationDate()));
            preparedStatement.setLong(7, musicBand.getNumberOfParticipants());

            if (musicBand.getGenre() != null) {
                preparedStatement.setString(8, musicBand.getGenre().toString());
            } else {
                preparedStatement.setString(8, null);
            }

            preparedStatement.setString(9, musicBand.getBestAlbum().getName());
            preparedStatement.setInt(10, musicBand.getBestAlbum().getTracks());
            preparedStatement.setFloat(11, musicBand.getBestAlbum().getSales());

            preparedStatement.setLong(12, musicBand.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении работника.");
        } finally {
            dataSource.closePrepareStatement(preparedStatement);
        }
    }

    public void delete(MusicBand musicBand) throws DAOException {
        PreparedStatement preparedStatement = dataSource.getPrepareStatement(DELETE);

        try {
            preparedStatement.setLong(1, musicBand.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Ошибка при удалении пути.");
        } finally {
            dataSource.closePrepareStatement(preparedStatement);
        }
    }
}
