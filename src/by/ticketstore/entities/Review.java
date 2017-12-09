package by.ticketstore.entities;


import lombok.Data;

@Data
public class Review {

    private Long id;
    private String text;
    private User user;
    private Movie movie;

    public Review(String text, User user, Movie movie) {
        this.text = text;
        this.user = user;
        this.movie = movie;
    }

    public Review(Long id, String text, User user) {
        this.id = id;
        this.text = text;
        this.user = user;
    }

    public Review(String text, Movie movie) {
        this.text = text;
        this.movie = movie;
    }
}
