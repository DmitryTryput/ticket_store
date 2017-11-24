package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Seance;
import by.ticketstore.entities.Ticket;

import java.sql.*;

public class SeanceDao {

    private static SeanceDao INSTANCE;

    private SeanceDao(){}

    public void addSeance(Seance seance) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            String personSql = "INSERT INTO seances (film_id, cinema_hall_id, seance_date, seance_time, price) VALUES (?,?,?,?,?)";
            PreparedStatement personStatement = connection.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS);
            personStatement.setLong(1, seance.getFilm().getId());
            personStatement.setLong(2, seance.getCinemaHall().getId());
            personStatement.setObject(3, seance.getSeanceDate());
            personStatement.setObject(4, seance.getSeanceTime());
            personStatement.setBigDecimal(5, seance.getPrice());
            personStatement.executeUpdate();
            ResultSet addSeanceResultSet = personStatement.getGeneratedKeys();
            if (addSeanceResultSet.next()) {
                seance.setId(addSeanceResultSet.getLong(1));
            }
            personStatement.close();
            TicketDao instance = TicketDao.getInstance();
            for (int i = 1; i <= seance.getCinemaHall().getRows() ; i++) {
                for (int j = 1; j <= seance.getCinemaHall().getSeats(); j++) {
                    instance.addTicket(new Ticket(i,j, seance), connection);
                }
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

    public static SeanceDao getInstance() {
        if (INSTANCE == null) {
            synchronized (SeanceDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SeanceDao();
                }
            }
        }
        return INSTANCE;
    }
}

