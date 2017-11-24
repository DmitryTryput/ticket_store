package by.ticketstore.dao;

import by.ticketstore.entities.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketDao {


    private static TicketDao INSTANCE;

    private TicketDao(){}

    public void addTicket(Ticket ticket, Connection connection) {
        try {
            String sql = "INSERT INTO tickets (seance_id, row, seat) VALUES (?,?,?);";
            PreparedStatement ticketStatement = connection.prepareStatement(sql);
            ticketStatement.setLong(1, ticket.getSeance().getId());
            ticketStatement.setLong(2, ticket.getRow());
            ticketStatement.setLong(3, ticket.getSeat());
            ticketStatement.executeUpdate();
            ticketStatement.close();
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
