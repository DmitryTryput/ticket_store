package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Film;
import by.ticketstore.entities.Genre;
import by.ticketstore.entities.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FilmDao {

    private static FilmDao INSTANCE;

    private FilmDao(){}

    public void addFilm(Film film) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            CountryDao countryDao = CountryDao.getInstance();
            countryDao.addCountry(film.getCountry(), connection);
            String filmSql = "INSERT INTO films (title, create_date, country_id) VALUES (?,?,?)";
            PreparedStatement filmStatement = connection.prepareStatement(filmSql);
            filmStatement.setString(1, film.getTitle());
            filmStatement.setObject(2, film.getCreateDate());
            filmStatement.setLong(3, film.getCountry().getId());
            filmStatement.executeUpdate();
            filmStatement.close();
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

    public void addActor(Film film, Person person) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO films_actors (film_id, person_id) VALUES (?, ?);";
            PreparedStatement actorStatement = connection.prepareStatement(sql);
            actorStatement.setLong(1, film.getId());
            actorStatement.setLong(1, person.getId());
            actorStatement.executeUpdate();
            actorStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDirector(Film film, Person person) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO films_directors (film_id, person_id) VALUES (?, ?);";
            PreparedStatement directorStatement = connection.prepareStatement(sql);
            directorStatement.setLong(1, film.getId());
            directorStatement.setLong(1, person.getId());
            directorStatement.executeUpdate();
            directorStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGenre(Film film, Genre genre) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            GenreDao genreDao = GenreDao.getInstance();
            genreDao.addGenre(genre, connection);
            String genreSql = "INSERT INTO films_genres (film_id, genre_id) VALUES (?,?)";
            PreparedStatement personStatement = connection.prepareStatement(genreSql);
            personStatement.setLong(1, film.getId());
            personStatement.setLong(2, genre.getId());
            personStatement.executeUpdate();
            personStatement.close();
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


    public static FilmDao getInstance() {
        if (INSTANCE == null) {
            synchronized (FilmDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FilmDao();
                }
            }
        }
        return INSTANCE;
    }
}
