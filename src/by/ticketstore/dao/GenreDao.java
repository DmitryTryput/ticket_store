package by.ticketstore.dao;

import by.ticketstore.entities.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDao {

    private static GenreDao INSTANCE;

    private GenreDao(){}

    public void addGenre(Genre genre, Connection connection) {
        try {
            String genreSql = "SELECT id FROM genres WHERE genres_name = ?";
            PreparedStatement genreStatement = connection.prepareStatement(genreSql);
            genreStatement.setString(1, genre.getName());
            ResultSet genreResultSet = genreStatement.executeQuery();
            if (genreResultSet.next()) {
                genre.setId(genreResultSet.getLong("id"));
            } else {
                String addGenreSQL = "INSERT INTO genres (genres_name) VALUES (?)";
                PreparedStatement addGenreStatement = connection.prepareStatement(addGenreSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                addGenreStatement.setString(1, genre.getName());
                addGenreStatement.executeUpdate();
                ResultSet addGenreResultSet = addGenreStatement.getGeneratedKeys();
                if (addGenreResultSet.next()) {
                    genre.setId(addGenreResultSet.getLong(1));
                }
                addGenreStatement.close();
                addGenreResultSet.close();
            }
            genreStatement.close();
            genreResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static GenreDao getInstance() {
        if (INSTANCE == null) {
            synchronized (GenreDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GenreDao();
                }
            }
        }
        return INSTANCE;
    }
}
