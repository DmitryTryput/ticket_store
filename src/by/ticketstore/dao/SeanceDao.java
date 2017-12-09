package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Cinema;
import by.ticketstore.entities.CinemaHall;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Seance;
import by.ticketstore.entities.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class SeanceDao {

    private static SeanceDao INSTANCE;

    private SeanceDao() {
    }

    public Optional<Seance> buyTickets(Long id) {
        Seance seance = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    getTicketsSql() + "WHERE s.id = ?")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        seance = createSeance(resultSet);
                        addTicket(seance, resultSet);
                    }
                    while (resultSet.next()) {
                        addTicket(seance, resultSet);
                    }
                    if (seance != null) {
                        return Optional.of(seance);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void addTicket(Seance seance, ResultSet resultSet) throws SQLException {
        if (seance.getRowSeatTickets().containsKey(resultSet.getInt("t.row"))) {
            getTicket(seance, resultSet);
        } else {
            seance.getRowSeatTickets().put(resultSet.getInt("t.row"), new HashMap<>());
            getTicket(seance, resultSet);
        }
    }

    private Ticket getTicket(Seance seance, ResultSet resultSet) throws SQLException {
        return seance.getRowSeatTickets().get(resultSet.getInt("t.row"))
                .put(resultSet.getInt("t.seat"), new Ticket(resultSet.getLong("t.id"),
                        resultSet.getInt("t.row"), resultSet.getInt("t.seat"),
                        resultSet.getBoolean("t.is_purchased")));
    }

    public void addSeance(Seance seance) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            String seanceSql = "INSERT INTO seances (film_id, cinema_hall_id, seance_date, seance_time, price) VALUES (?,?,?,?,?)";
            try (PreparedStatement seanceStatement = connection.prepareStatement(seanceSql, Statement.RETURN_GENERATED_KEYS)) {
                seanceStatement.setLong(1, seance.getMovie().getId());
                seanceStatement.setLong(2, seance.getCinemaHall().getId());
                seanceStatement.setObject(3, seance.getSeanceDate());
                seanceStatement.setObject(4, seance.getSeanceTime());
                seanceStatement.setBigDecimal(5, seance.getPrice());
                seanceStatement.executeUpdate();
                try (ResultSet addSeanceResultSet = seanceStatement.getGeneratedKeys()) {
                    if (addSeanceResultSet.next()) {
                        seance.setId(addSeanceResultSet.getLong(1));
                    }
                }
            }
            String cinemaHallSql = "SELECT * FROM cinema_halls WHERE id = ?";
            try (PreparedStatement cinemaHallStatement = connection.prepareStatement(cinemaHallSql)) {
                cinemaHallStatement.setLong(1, seance.getCinemaHall().getId());
                try (ResultSet resultSet = cinemaHallStatement.executeQuery()) {
                    while (resultSet.next()) {
                        seance.getCinemaHall().setRows(resultSet.getInt("hall_rows"));
                        seance.getCinemaHall().setSeats(resultSet.getInt("row_seats"));
                    }
                }
            }
            TicketDao instance = TicketDao.getInstance();
            for (int i = 1; i <= seance.getCinemaHall().getRows(); i++) {
                for (int j = 1; j <= seance.getCinemaHall().getSeats(); j++) {
                    instance.addTicket(new Ticket(i, j, seance), connection);
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

    public List<Cinema> getUpcomingAll() {
        List<Cinema> cinemas = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM cinemas")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        cinemas.add(new Cinema(resultSet.getLong("id"),
                                resultSet.getString("title")));
                    }
                }
            }
            for (Cinema cinema : cinemas) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM cinema_halls WHERE cinema_id = ?")) {
                    preparedStatement.setLong(1, cinema.getId());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            cinema.getHalls().add(new CinemaHall(resultSet.getLong("id"),
                                    resultSet.getString("title")));
                        }
                    }
                }
                for (CinemaHall cinemaHall : cinema.getHalls()) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(
                            "SELECT * FROM seances s JOIN films f ON s.film_id = f.id " +
                                    "WHERE s.seance_date >= ? AND s.cinema_hall_id = ?")) {
                        preparedStatement.setObject(1, LocalDate.now());
                        preparedStatement.setLong(2, cinemaHall.getId());
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            while (resultSet.next()) {
                                getUpcomingSeances(cinemaHall, resultSet);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemas;
    }

    private void getUpcomingSeances(CinemaHall cinemaHall, ResultSet resultSet) throws SQLException {
        Movie movie = new Movie(resultSet.getLong("f.id"),
                resultSet.getString("f.title"));
        LocalDate date = resultSet.getDate("s.seance_date").toLocalDate();
        LocalTime time = resultSet.getTime("s.seance_time").toLocalTime();
        Seance seance = new Seance(resultSet.getLong("s.id"),
                movie, date, time, resultSet.getBigDecimal("s.price"));
        cinemaHall.getSeances().add(seance);
    }

    private Seance createSeance(ResultSet resultSet) throws SQLException {
        LocalDate localDate = resultSet.getObject("s.seance_date", LocalDate.class);
        LocalTime localTime = resultSet.getObject("s.seance_time", LocalTime.class);
        Seance seance = new Seance(resultSet.getLong("s.id"),
                new Movie(resultSet.getLong("f.id"), resultSet.getString("f.title")),
                localDate, localTime, resultSet.getBigDecimal("s.price"));
        seance.setCinemaHall(new CinemaHall(resultSet.getLong("ch.id"),
                resultSet.getString("ch.title")));
        seance.getCinemaHall().setSeats(resultSet.getInt("ch.row_seats"));
        seance.getCinemaHall().setRows(resultSet.getInt("ch.hall_rows"));
        seance.getCinemaHall().setCinema(new Cinema(resultSet.getLong("c.id"),
                resultSet.getString("c.title")));
        return seance;
    }

    private String getTicketsSql() {
        return "SELECT * FROM seances s JOIN tickets t ON s.id = t.seance_id " +
                "JOIN films f ON s.film_id = f.id " +
                "JOIN cinema_halls ch ON s.cinema_hall_id = ch.id " +
                "JOIN cinemas c ON ch.cinema_id = c.id ";
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

