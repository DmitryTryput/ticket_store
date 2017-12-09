package by.ticketstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTicketDto {

    private Long id;
    private Integer row;
    private Integer seat;
    private MovieBaseInfoDto movieBaseInfoDto;
    private SeanceBasicInfoDto seanceBasicInfoDto;
}
