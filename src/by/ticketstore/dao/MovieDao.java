package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Country;
import by.ticketstore.entities.Genre;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Person;
import by.ticketstore.entities.Review;
import by.ticketstore.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;


public class MovieDao {

    private static MovieDao INSTANCE;

    private MovieDao() {
    }

    public Long addMovie(Movie movie) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            String movieSql = "INSERT INTO films (title, create_date, country_id) VALUES (?,?,?)";
            try (PreparedStatement movieStatement
                         = connection.prepareStatement(movieSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                movieStatement.setString(1, movie.getTitle());
                movieStatement.setInt(2, movie.getCreateDate().getValue());
                movieStatement.setLong(3, movie.getCountry().getId());
                movieStatement.executeUpdate();
                try (ResultSet movieResultSet = movieStatement.getGeneratedKeys()) {
                    if (movieResultSet.next()) {
                        movie.setId(movieResultSet.getLong(1));
                    }
                }
            }
            String movieDirectorSql = "INSERT INTO films_directors (film_id, person_id) VALUES (?,?)";
            try (PreparedStatement movieDirectorStatement = connection.prepareStatement(movieDirectorSql)) {
                movieDirectorStatement.setLong(1, movie.getId());
                movieDirectorStatement.setLong(2, movie.getDirector().getId());
                movieDirectorStatement.executeUpdate();
            }
            for (Genre genre : movie.getGenres()) {
                String movieGenresSql = "INSERT INTO films_genres (film_id, genre_id) VALUES (?,?)";
                try (PreparedStatement movieGenresStatement = connection.prepareStatement(movieGenresSql)) {
                    movieGenresStatement.setLong(1, movie.getId());
                    movieGenresStatement.setLong(2, genre.getId());
                    movieGenresStatement.executeUpdate();
                }
            }
            for (Person person : movie.getActors()) {
                String moviePersonSql = "INSERT INTO films_actors (film_id, person_id) VALUES (?,?)";
                try (PreparedStatement moviePersonStatement = connection.prepareStatement(moviePersonSql)) {
                    moviePersonStatement.setLong(1, movie.getId());
                    moviePersonStatement.setLong(2, person.getId());
                    moviePersonStatement.executeUpdate();
                }
            }
            connection.commit();
            return movie.getId();
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
        return null;
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
                            "LEFT JOIN reviews r ON f.id = r.film_id " +
                            "LEFT JOIN users u ON r.user_id = u.id " +
                            "WHERE f.id = ? ORDER BY r.id")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String title = resultSet.getString("f.title");
                        Year createDate =Year.of(resultSet.getInt("f.create_date"));
                        Country country = new Country(resultSet.getLong("c.id"), resultSet.getString("c.country_name"));
                        Person director = new Person(resultSet.getLong("pr.id"), resultSet.getString("pr.first_name"),
                                resultSet.getString("pr.last_name"));
                        movie = new Movie(title, createDate, country, director);
                        addActorsGenresReviews(movie, resultSet);
                    }
                    while (resultSet.next()) {
                        addActorsGenresReviews(movie, resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    private void addActorsGenresReviews(Movie movie, ResultSet resultSet) throws SQLException {
        Person actor = new Person(resultSet.getLong("p.id"), resultSet.getString("p.first_name"),
                resultSet.getString("p.last_name"));
        Review review = new Review(resultSet.getLong("r.id"), resultSet.getString("r.text"),
                new User(resultSet.getLong("u.id"),resultSet.getString("u.first_name"),resultSet.getString("u.last_name")));
        Genre genre = new Genre(resultSet.getLong("g.id"), resultSet.getString("g.genres_name"));
        movie.getReviews().add(review);
        movie.getActors().add(actor);
        movie.getGenres().add(genre);
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
