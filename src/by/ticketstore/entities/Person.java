package by.ticketstore.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Country country;
    private Set<Movie> actor = new HashSet<>();
    private Set<Movie> director = new HashSet<>();

    public Person(String firstName, String lastName, LocalDate dateOfBirth, Country country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
    }

    public Person(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(Long id) {
        this.id = id;
    }
}
