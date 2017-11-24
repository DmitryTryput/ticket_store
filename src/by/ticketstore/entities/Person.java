package by.ticketstore.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Country country;

    public Person(String firstName, String lastName, LocalDate dateOfBirth, Country country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
    }
}
