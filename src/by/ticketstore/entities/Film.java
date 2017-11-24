package by.ticketstore.entities;

import lombok.Data;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private Long id;
    private String title;
    private Date createDate;
    private Country country;;
    private Set<String> genres = new HashSet<>();
    private Set<Person> directors = new HashSet<>();
    private Set<Person> actors = new HashSet<>();
    private Set<Review> reviews = new HashSet<>();
}
