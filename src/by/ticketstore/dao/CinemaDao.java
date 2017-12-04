package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Cinema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CinemaDao {

    private static CinemaDao INSTANCE;

    private CinemaDao(){}

    public void addCinema(Cinema cinema) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO cinemas (title) VALUES (?);";
            PreparedStatement cinemaStatement = connection.prepareStatement(sql);
            cinemaStatement.setString(1, cinema.getTitle());
            cinemaStatement.executeUpdate();
            cinemaStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Cinema> getAll() {
        List<Cinema> cinemas = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM cinemas")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        cinemas.add(new Cinema(resultSet.getLong("id"), resultSet.getString("title")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemas;
    }

    public static CinemaDao getInstance() {
        if (INSTANCE == null) {
            synchronized (CinemaDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CinemaDao();
                }
            }
        }
        return INSTANCE;
    }
}
