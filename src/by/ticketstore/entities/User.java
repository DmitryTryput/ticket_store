package by.ticketstore.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class User {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private BigDecimal value;
    private Set<Ticket> tickets = new HashSet<>();
    private Set<Review> reviews = new HashSet<>();
}
