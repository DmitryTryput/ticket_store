package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Cinema;
import by.ticketstore.entities.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewDao {

    private static ReviewDao INSTANCE;

    private ReviewDao() {
    }

    public void addCinema(Review review) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO reviews (text, film_id, user_id) VALUES (?,?,?);";
            PreparedStatement cinemaStatement = connection.prepareStatement(sql);
            cinemaStatement.setString(1, review.getText());
            cinemaStatement.setLong(2, review.getMovie().getId());
            cinemaStatement.setLong(3, review.getUser().getId());
            cinemaStatement.executeUpdate();
            cinemaStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ReviewDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ReviewDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReviewDao();
                }
            }
        }
        return INSTANCE;
    }
}
