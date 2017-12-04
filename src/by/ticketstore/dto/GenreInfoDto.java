package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreInfoDto {

    private Long id;
    private String name;

    public GenreInfoDto(Long id) {
        this.id = id;
    }
}
