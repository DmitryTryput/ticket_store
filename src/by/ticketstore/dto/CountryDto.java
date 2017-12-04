package by.ticketstore.dto;


import lombok.Data;

@Data
public class CountryDto {

    private String name;
    private Long id;

    public CountryDto(Long id) {
        this.id = id;
    }
}
