package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Cinema;
import by.ticketstore.entities.CinemaHall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CinemaHallDao {

    private static CinemaHallDao INSTANCE;

    private CinemaHallDao() {
    }

    public void addCinemaHall(CinemaHall cinemaHall) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO cinema_halls (title, hall_rows, row_seats, cinema_id) VALUES (?,?,?,?);";
            PreparedStatement cinemaHallStatement = connection.prepareStatement(sql);
            cinemaHallStatement.setString(1, cinemaHall.getTitle());
            cinemaHallStatement.setLong(2, cinemaHall.getRows());
            cinemaHallStatement.setLong(3, cinemaHall.getSeats());
            cinemaHallStatement.setLong(4, cinemaHall.getCinemaId());
            cinemaHallStatement.executeUpdate();
            cinemaHallStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<CinemaHall> getAllCinemaHallsWithCinemas() {
        Set<CinemaHall> cinemas = new HashSet<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM cinemas JOIN cinema_halls ON cinemas.id = cinema_halls.cinema_id")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        CinemaHall cinemaHall = new CinemaHall(resultSet.getString("cinema_halls.title"),
                                resultSet.getInt("cinema_halls.hall_rows"), resultSet.getInt("cinema_halls.row_seats"),
                                resultSet.getLong("cinemas.id"));
                        cinemaHall.setId(resultSet.getLong("cinema_halls.id"));
                        cinemaHall.setCinema(new Cinema(resultSet.getString("cinemas.title")));
                        cinemas.add(cinemaHall);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemas;
    }

    public static CinemaHallDao getInstance() {
        if (INSTANCE == null) {
            synchronized (CinemaHallDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CinemaHallDao();
                }
            }
        }
        return INSTANCE;
    }
}
