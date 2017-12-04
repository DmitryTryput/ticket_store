package by.ticketstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country {

    private Long id;
    private String name;

    public Country(Long id) {
        this.id = id;
    }
}
