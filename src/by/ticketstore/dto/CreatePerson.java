package by.ticketstore.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePerson {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private CountryDto countryDto;

    public CreatePerson(String firstName, String lastName, LocalDate dateOfBirth, CountryDto countryDto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.countryDto = countryDto;
    }
}
