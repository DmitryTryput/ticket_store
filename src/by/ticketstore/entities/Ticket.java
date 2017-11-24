package by.ticketstore.entities;

import lombok.Data;


@Data
public class Ticket {

    private Long id;
    private Integer row;
    private Integer seat;
    private Seance seance;
    private boolean isPurchased = false;
    private User user;

    public Ticket(Integer row, Integer seat, Seance seance) {
        this.row = row;
        this.seat = seat;
        this.seance = seance;
    }
}
