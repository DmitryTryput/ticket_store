package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Review;
import by.ticketstore.entities.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    private static UserDao INSTANCE;

    private UserDao() {
    }

    public Optional<User> getUserInfo(Long id) {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users u JOIN reviews r ON u.id = r.user_id " +
                            "JOIN films f ON r.film_id = f.id " +
                            "WHERE u.id = ?")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User(resultSet.getLong("u.id"), resultSet.getString("u.first_name"),
                                resultSet.getString("u.last_name") );
                        addReview(user, resultSet);
                    }
                    while(resultSet.next()) {
                        addReview(user, resultSet);
                    }
                    if (user != null) {
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean addReview(User user, ResultSet resultSet) throws SQLException {
        return user.getReviews().add(new Review(resultSet.getString("r.text"),
                new Movie(resultSet.getLong("f.id"), resultSet.getString("f.title"))));
    }

    public void add(User user) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (email, first_name, last_name, user_password)" +
                            "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.executeUpdate();
                try (ResultSet preparedResultSet = preparedStatement.getGeneratedKeys()) {
                    if (preparedResultSet.next()) {
                        user.setId(preparedResultSet.getLong(1));
                    }
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users_roles (user_id)" +
                            "VALUES (?)")) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.executeUpdate();
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

    public BigDecimal addValue(Long id, BigDecimal value) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            BigDecimal userValue = getValue(connection, id);
            String updateUserValue = "UPDATE users SET user_value = ? WHERE  id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserValue)) {
                preparedStatement.setBigDecimal(1, userValue.add(value));
                preparedStatement.setLong(2, id);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            connection.commit();
            return getValue(connection, id);
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

    public Optional<User> login(User user) {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users u JOIN users_roles ur ON u.id = ur.user_id " +
                            "JOIN roles r ON ur.role_id = r.id " +
                            "WHERE email = ? AND user_password = ?")) {
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getPassword());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return getUser(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<User> getUser(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString("u.email");
        Long id = resultSet.getLong("u.id");
        String firstName = resultSet.getString("u.first_name");
        String lastName = resultSet.getString("u.last_name");
        BigDecimal userValue = resultSet.getBigDecimal("u.user_value");
        User loggedUser = new User(id, email, firstName, lastName, userValue);
        loggedUser.setRole(resultSet.getString("r.name"));
        return Optional.of(loggedUser);
    }


    public boolean checkEmail(User user) {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM users WHERE email = ?")) {
                preparedStatement.setString(1, user.getEmail());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static UserDao getInstance() {
        if (INSTANCE == null) {
            synchronized (UserDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserDao();
                }
            }
        }
        return INSTANCE;
    }

    private BigDecimal getValue(Connection connection, Long id) throws SQLException {
        String getUserValueSql = "SELECT user_value FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getUserValueSql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("user_value");
                }
            }
        }
        return null;
    }
}
