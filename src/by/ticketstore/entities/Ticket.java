package by.ticketstore.entities;

import lombok.Data;


@Data
public class Ticket {

    private Long id;
    private Integer row;
    private Integer seat;
    private Seance seance;
    private boolean purchased;
    private User user;

    public Ticket(Integer row, Integer seat, Seance seance) {
        this.row = row;
        this.seat = seat;
        this.seance = seance;
    }

    public Ticket(Long id) {
        this.id = id;
    }

    public Ticket(Long id, Integer row, Integer seat, boolean purchased) {
        this.id = id;
        this.row = row;
        this.seat = seat;
        this.purchased = purchased;
    }

    public Ticket(Long id, Integer row, Integer seat) {
        this.id = id;
        this.row = row;
        this.seat = seat;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
