package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDto {

    private String text;
    private MovieBaseInfoDto movie;
}
