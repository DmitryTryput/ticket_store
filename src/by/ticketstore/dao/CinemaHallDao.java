package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Cinema;
import by.ticketstore.entities.CinemaHall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CinemaHallDao {

    private static CinemaHallDao INSTANCE;

    private CinemaHallDao(){}

    public void addCinemaHall(CinemaHall cinemaHall, Cinema cinema) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO cinemas (title, hall_rows, row_seats, cinema_id) VALUES (?,?,?,?);";
            PreparedStatement cinemaHallStatement = connection.prepareStatement(sql);
            cinemaHallStatement.setString(1, cinemaHall.getTitle());
            cinemaHallStatement.setLong(2, cinemaHall.getRows());
            cinemaHallStatement.setLong(3, cinemaHall.getSeats());
            cinemaHallStatement.setLong(4, cinema.getId());
            cinemaHallStatement.executeUpdate();
            cinemaHallStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
