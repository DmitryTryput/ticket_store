package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Country;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Genre;
import by.ticketstore.entities.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;


public class MovieDao {

    private static MovieDao INSTANCE;

    private MovieDao() {
    }

    public void addMovie(Movie movie) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            String movieSql = "INSERT INTO films (title, create_date, country_id) VALUES (?,?,?)";
            PreparedStatement movieStatement = connection.prepareStatement(movieSql, PreparedStatement.RETURN_GENERATED_KEYS);
            movieStatement.setString(1, movie.getTitle());
            movieStatement.setInt(2, movie.getCreateDate().getValue());
            movieStatement.setLong(3, movie.getCountry().getId());
            movieStatement.executeUpdate();
            ResultSet movieResultSet = movieStatement.getGeneratedKeys();
            if (movieResultSet.next()) {
                movie.setId(movieResultSet.getLong(1));
            }
            movieResultSet.close();
            movieStatement.close();
            String movieDirectorSql = "INSERT INTO films_directors (film_id, person_id) VALUES (?,?)";
            PreparedStatement movieDirectorStatement = connection.prepareStatement(movieDirectorSql);
            movieDirectorStatement.setLong(1, movie.getId());
            movieDirectorStatement.setLong(2, movie.getDirector().getId());
            movieDirectorStatement.executeUpdate();
            movieDirectorStatement.close();
            for (Genre genre : movie.getGenres()) {
                String movieGenresSql = "INSERT INTO films_genres (film_id, genre_id) VALUES (?,?)";
                PreparedStatement movieGenresStatement = connection.prepareStatement(movieGenresSql);
                movieGenresStatement.setLong(1, movie.getId());
                movieGenresStatement.setLong(2, genre.getId());
                movieGenresStatement.executeUpdate();
                movieGenresStatement.close();
            }
            for (Person person : movie.getActors()) {
                String moviePersonSql = "INSERT INTO films_actors (film_id, person_id) VALUES (?,?)";
                PreparedStatement moviePersonStatement = connection.prepareStatement(moviePersonSql);
                moviePersonStatement.setLong(1, movie.getId());
                moviePersonStatement.setLong(2, person.getId());
                moviePersonStatement.executeUpdate();
                moviePersonStatement.close();
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Movie getFullInfo(Long id) {
        Movie movie = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM films f JOIN films_actors fa ON f.id = fa.film_id " +
                            "JOIN countries c ON f.country_id =c.id " +
                            "JOIN persons p ON fa.person_id = p.id " +
                            "JOIN films_directors fd ON f.id = fd.film_id " +
                            "JOIN persons pr ON fd.person_id = pr.id " +
                            "JOIN films_genres fg ON f.id = fg.film_id " +
                            "JOIN genres g ON fg.genre_id = g.id " +
                            "WHERE f.id = ?")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String title = resultSet.getString("f.title");
                        Year createDate =Year.of(resultSet.getInt("f.create_date"));
                        Country country = new Country(resultSet.getLong("c.id"), resultSet.getString("c.country_name"));
                        Person director = new Person(resultSet.getLong("pr.id"), resultSet.getString("pr.first_name"),
                                resultSet.getString("pr.last_name"));
                        Person actor = new Person(resultSet.getLong("p.id"), resultSet.getString("p.first_name"),
                                resultSet.getString("p.last_name"));
                        Genre genre = new Genre(resultSet.getLong("g.id"), resultSet.getString("g.genres_name"));
                        movie = new Movie(title, createDate, country, director);
                        movie.getActors().add(actor);
                        movie.getGenres().add(genre);
                    }
                    while (resultSet.next()) {
                        Person actor = new Person(resultSet.getLong("p.id"), resultSet.getString("p.first_name"),
                                resultSet.getString("p.last_name"));
                        Genre genre = new Genre(resultSet.getLong("g.id"), resultSet.getString("g.genres_name"));
                        movie.getActors().add(actor);
                        movie.getGenres().add(genre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<Movie> getAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM films")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String title = resultSet.getString("title");
                        movies.add(new Movie(id, title));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static MovieDao getInstance() {
        if (INSTANCE == null) {
            synchronized (MovieDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieDao();
                }
            }
        }
        return INSTANCE;
    }
}
