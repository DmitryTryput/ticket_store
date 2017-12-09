package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {


    private static TicketDao INSTANCE;

    private TicketDao() {
    }

    public List<Ticket> getUserTickets(Long id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM seances s JOIN tickets t ON s.id = t.seance_id " +
                            "JOIN films f ON s.film_id = f.id " +
                            "WHERE t.user_id = ?")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        tickets.add(getTicket(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    private Ticket getTicket(ResultSet resultSet) throws SQLException {
        LocalDate localDate = resultSet.getObject("s.seance_date", LocalDate.class);
        LocalTime localTime = resultSet.getObject("s.seance_time", LocalTime.class);
        Seance seance = new Seance(resultSet.getLong("s.id"),
                new Movie(resultSet.getLong("f.id"), resultSet.getString("f.title")),
                localDate, localTime, resultSet.getBigDecimal("s.price"));
        Ticket ticket = new Ticket(resultSet.getLong("t.id"),
                resultSet.getInt("t.row"), resultSet.getInt("t.seat"));
        ticket.setSeance(seance);
        return ticket;
    }

    public boolean buyTickets(User user, List<Ticket> tickets) {
        boolean confirm = true;
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            for (Ticket ticket : tickets) {
                String ticketSql = "SELECT is_purchased FROM tickets WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(ticketSql)) {
                    preparedStatement.setLong(1, ticket.getId());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            if (resultSet.getBoolean("is_purchased")) {
                                return false;
                            }
                        }
                    }
                }
            }
            for (Ticket ticket : tickets) {
                String ticketSql = "UPDATE tickets SET is_purchased = true, user_id = ? WHERE id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(ticketSql)) {
                    preparedStatement.setLong(1, user.getId());
                    preparedStatement.setLong(2, ticket.getId());
                    preparedStatement.executeUpdate();
                }
            }
            String valueSql = "UPDATE users SET user_value = ?  WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(valueSql)) {
                preparedStatement.setBigDecimal(1, user.getValue());
                preparedStatement.setLong(2, user.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
            return confirm;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    confirm=false;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    confirm=false;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return confirm;
    }

    public void addTicket(Ticket ticket, Connection connection) {
        try {
            String sql = "INSERT INTO tickets (seance_id, row, seat) VALUES (?,?,?);";
            try (PreparedStatement ticketStatement = connection.prepareStatement(sql)) {
                ticketStatement.setLong(1, ticket.getSeance().getId());
                ticketStatement.setLong(2, ticket.getRow());
                ticketStatement.setLong(3, ticket.getSeat());
                ticketStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TicketDao getInstance() {
        if (INSTANCE == null) {
            synchronized (TicketDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TicketDao();
                }
            }
        }
        return INSTANCE;
    }
}
