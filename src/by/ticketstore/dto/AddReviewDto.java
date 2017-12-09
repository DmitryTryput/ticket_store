package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddReviewDto {

    private String text;
    private Long userId;
    private Long movieId;
}
