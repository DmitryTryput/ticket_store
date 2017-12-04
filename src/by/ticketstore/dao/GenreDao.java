package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Genre;
import by.ticketstore.entities.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {

    private static GenreDao INSTANCE;

    private GenreDao(){}

    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM genres")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String genreName = resultSet.getString("genres_name");

                        genres.add(new Genre(id, genreName));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }


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
