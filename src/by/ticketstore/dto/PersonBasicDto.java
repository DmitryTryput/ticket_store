package by.ticketstore.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonBasicDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private CountryDto countryDto;

    public PersonBasicDto(String firstName, String lastName, LocalDate dateOfBirth, CountryDto countryDto) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.countryDto = countryDto;
    }

    public PersonBasicDto(Long id) {
        this.id = id;
    }
}
