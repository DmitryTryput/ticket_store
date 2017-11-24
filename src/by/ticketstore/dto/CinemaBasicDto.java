package by.ticketstore.dto;

import lombok.Data;

@Data
public class CinemaBasicDto {

    private Long id;
    private String title;

    public CinemaBasicDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public CinemaBasicDto(String title) {
        this.title = title;
    }
}
