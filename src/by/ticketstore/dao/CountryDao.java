package by.ticketstore.dao;

import by.ticketstore.entities.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CountryDao {

    private static CountryDao INSTANCE;

    private CountryDao(){}

    public void addCountry(Country country, Connection connection) {
       try {
           String countrySql = "SELECT id FROM countries WHERE country_name = ?";
           PreparedStatement countryStatement = connection.prepareStatement(countrySql);
           countryStatement.setString(1, country.getName());
           ResultSet countryResultSet = countryStatement.executeQuery();
           if (countryResultSet.next()) {
               country.setId(countryResultSet.getLong("id"));
           } else {
               String addCountrySQL = "INSERT INTO countries (country_name) VALUES (?)";
               PreparedStatement addCountryStatement = connection.prepareStatement(addCountrySQL, PreparedStatement.RETURN_GENERATED_KEYS);
               addCountryStatement.setString(1, country.getName());
               addCountryStatement.executeUpdate();
               ResultSet addCountryResultSet = addCountryStatement.getGeneratedKeys();
               if (addCountryResultSet.next()) {
                   country.setId(addCountryResultSet.getLong(1));
               }
               addCountryStatement.close();
               addCountryResultSet.close();
           }
           countryStatement.close();
           countryResultSet.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }

    public static CountryDao getInstance() {
        if (INSTANCE == null) {
            synchronized (CountryDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CountryDao();
                }
            }
        }
        return INSTANCE;
    }
}
