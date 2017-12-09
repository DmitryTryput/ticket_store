package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Cinema;
import by.ticketstore.entities.Country;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PersonDao {

    private static PersonDao INSTANCE;

    private PersonDao() {
    }

    public void addPerson(Person person) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            String personSql = "INSERT INTO persons (first_name, last_name, country_id, date_of_birth) VALUES (?,?,?,?)";
            PreparedStatement personStatement = connection.prepareStatement(personSql);
            personStatement.setString(1, person.getFirstName());
            personStatement.setString(2, person.getLastName());
            personStatement.setLong(3, person.getCountry().getId());
            personStatement.setObject(4, person.getDateOfBirth());
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

    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM persons ORDER BY last_name")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        persons.add(new Person(id, firstName, lastName));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public Person getFullInfo(Long id) {
        Person person = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM persons p JOIN countries c ON p.country_id = c.id " +
                            "LEFT JOIN films_directors fd ON p.id = fd.person_id " +
                            "LEFT JOIN films f ON fd.film_id = f.id " +
                            "LEFT JOIN films_actors fa ON p.id = fa.person_id " +
                            "LEFT JOIN films fl ON fa.film_id = fl.id " +
                            "WHERE p.id = ?")) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   if (resultSet.next()) {
                       String firstName = resultSet.getString("p.first_name");
                       String lastName = resultSet.getString("p.last_name");
                       Country country = new Country(resultSet.getLong("c.id"),
                               resultSet.getString("c.country_name"));
                       LocalDate dateOfBirth = resultSet.getDate("p.date_of_birth").toLocalDate();
                       person = new Person(firstName,lastName,dateOfBirth,country);
                       person.getDirector().add(new Movie(resultSet.getLong("f.id"), resultSet.getString("f.title")));
                       person.getActor().add(new Movie(resultSet.getLong("fl.id"), resultSet.getString("fl.title")));
                   }
                    while (resultSet.next()) {
                        person.getDirector().add(new Movie(resultSet.getLong("f.id"), resultSet.getString("f.title")));
                        person.getActor().add(new Movie(resultSet.getLong("fl.id"), resultSet.getString("fl.title")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static PersonDao getInstance() {
        if (INSTANCE == null) {
            synchronized (PersonDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PersonDao();
                }
            }
        }
        return INSTANCE;
    }
}
