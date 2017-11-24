package by.ticketstore.dto;


import lombok.Data;

@Data
public class CountryDto {

    private String name;

    public CountryDto(String name) {
        this.name = name;
    }
}
