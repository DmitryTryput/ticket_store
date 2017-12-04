package by.ticketstore.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private BigDecimal value;
    private Set<Ticket> tickets = new HashSet<>();
    private Set<Review> reviews = new HashSet<>();

    public User(String email) {
        this.email = email;
    }

    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Long id, String email, String firstName, String lastName, BigDecimal value) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.value = value;
    }
}
