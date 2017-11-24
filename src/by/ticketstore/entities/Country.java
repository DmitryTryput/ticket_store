package by.ticketstore.entities;

import lombok.Data;

@Data
public class Country {

    private Long id;
    private String name;

    public Country(String name) {
        this.name = name;
    }
}
