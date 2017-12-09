package by.ticketstore.dto;

import lombok.Data;


@Data
public class TicketDto {

    private Long id;
    private Integer row;
    private Integer seat;
    private boolean isPurchased = false;

    public TicketDto(Long id, Integer row, Integer seat) {
        this.id = id;
        this.row = row;
        this.seat = seat;
    }

    public TicketDto(Long id) {
        this.id = id;
    }
}
