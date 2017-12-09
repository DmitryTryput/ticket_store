package by.ticketstore.service;

import by.ticketstore.dao.ReviewDao;
import by.ticketstore.dto.AddReviewDto;
import by.ticketstore.entities.Movie;
import by.ticketstore.entities.Review;
import by.ticketstore.entities.User;

public class ReviewService {

    private static ReviewService INSTANCE = null;

    private ReviewService() {
    }

    public void addReview(AddReviewDto addReviewDto) {
        ReviewDao.getInstance().addCinema(new Review(
                addReviewDto.getText(), new User(addReviewDto.getUserId()), new Movie(addReviewDto.getMovieId())));
    }

    public static ReviewService getInstance() {
        if (INSTANCE == null) {
            synchronized (ReviewService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReviewService();
                }
            }
        }
        return INSTANCE;
    }
}
