package servlet;

import by.ticketstore.dto.CountryDto;
import by.ticketstore.dto.GenreInfoDto;
import by.ticketstore.dto.MovieAddDto;
import by.ticketstore.dto.PersonBasicDto;
import by.ticketstore.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.stream.Collectors;


import static util.ServletUtil.createViewPath;

@WebServlet("/add-movie")
public class AddMovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("countries", CountryService.getInstance().getAll());
        req.setAttribute("years", DateService.getInstance().getYears());
        req.setAttribute("persons", PersonService.getInstance().getAll());
        req.setAttribute("genres", GenreService.getInstance().getAll());
        getServletContext()
                .getRequestDispatcher(createViewPath("add-movie"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        Year year = Year.of(Integer.valueOf(req.getParameter("year")));
        PersonBasicDto director = new PersonBasicDto(Long.valueOf(req.getParameter("director")));
        CountryDto country = new CountryDto(Long.valueOf(req.getParameter("country")));
        MovieAddDto movieAddDto = new MovieAddDto(title, year, director, country);
        movieAddDto.getActors().addAll(Arrays.stream(req.getParameterValues("actors"))
                .map(id -> new PersonBasicDto(Long.valueOf(id))).collect(Collectors.toList()));
        movieAddDto.getGenres().addAll(Arrays.stream(req.getParameterValues("genres"))
                .map(id -> new GenreInfoDto(Long.valueOf(id))).collect(Collectors.toList()));
        MovieService.getInstance().add(movieAddDto);
        System.out.println(movieAddDto);
    }
}
