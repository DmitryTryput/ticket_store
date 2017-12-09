package servlet;

import by.ticketstore.dto.AddReviewDto;
import by.ticketstore.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/add-review")
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reviewText = req.getParameter("review");
        if (!reviewText.equals("")) {
            HttpSession session = req.getSession();
            Long id = (Long) session.getAttribute("id");
            Long movieId = Long.valueOf(req.getParameter("id"));
            ReviewService.getInstance().addReview(new AddReviewDto(reviewText, id, movieId));
            resp.sendRedirect(req.getHeader("Referer"));
        } else {
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
}
