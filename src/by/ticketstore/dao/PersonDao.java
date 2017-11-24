package by.ticketstore.dao;

import by.ticketstore.dao.connection.ConnectionManager;
import by.ticketstore.entities.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class PersonDao {

    private static PersonDao INSTANCE;

    private PersonDao(){}

    public void addPerson(Person person) {
        Connection connection = null;
        try {
            connection = ConnectionManager.getConnection();
            connection.setAutoCommit(false);
            CountryDao countryDao = CountryDao.getInstance();
            countryDao.addCountry(person.getCountry(), connection);
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
