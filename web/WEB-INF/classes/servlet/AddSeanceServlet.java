package servlet;


import by.ticketstore.dto.SeanceAddDto;
import by.ticketstore.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static util.ServletUtil.createViewPath;

@WebServlet("/add-seance")
public class AddSeanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("hours", TimeService.getInstance().getHours());
        req.setAttribute("minutes", TimeService.getInstance().getMinutes());
        req.setAttribute("days", DateService.getInstance().getDays());
        req.setAttribute("months", DateService.getInstance().getMonths());
        req.setAttribute("years", DateService.getInstance().getYears());
        req.setAttribute("movies", MovieService.getInstance().getAll());
        req.setAttribute("cinemaHalls", CinemaHallService.getInstance().getAllWithCinemas());
        getServletContext()
                .getRequestDispatcher(createViewPath("add-seance"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieId = Long.valueOf(req.getParameter("movies"));
        LocalDate date = LocalDate.of(Integer.valueOf(req.getParameter("year")),
                Integer.valueOf(req.getParameter("month")), Integer.valueOf(req.getParameter("day")));
        LocalTime time = LocalTime.of(Integer.valueOf(req.getParameter("hour")),
                Integer.valueOf(req.getParameter("minutes")));
        Long cinemaHallId = Long.valueOf(req.getParameter("cinemaHall"));
        String stringPrice = req.getParameter("price");
        if(!stringPrice.equals("")) {
            BigDecimal price = new BigDecimal(stringPrice);
            SeanceAddDto seanceAddDto = new SeanceAddDto(movieId, cinemaHallId, date, time, price);
            SeanceService.getInstance().add(seanceAddDto);
            resp.sendRedirect("upcoming");
        } else {
            resp.sendRedirect("add-seance");
        }
    }
}
