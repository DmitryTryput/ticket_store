package servlet;

import by.ticketstore.dto.CountryDto;
import by.ticketstore.dto.PersonBasicDto;
import by.ticketstore.service.CountryService;
import by.ticketstore.service.DateService;
import by.ticketstore.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static util.ServletUtil.createViewPath;

@WebServlet("/add-person")
public class AddPersonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("days", DateService.getInstance().getDays());
        req.setAttribute("months", DateService.getInstance().getMonths());
        req.setAttribute("years", DateService.getInstance().getYears());
        req.setAttribute("countries", CountryService.getInstance().getAll());
        getServletContext()
                .getRequestDispatcher(createViewPath("add-person"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer year = Integer.valueOf(req.getParameter("year"));
        Integer month = Integer.valueOf(req.getParameter("month"));
        Integer day = Integer.valueOf(req.getParameter("day"));
        Long countryId = Long.valueOf(req.getParameter("countryId"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        PersonBasicDto personBasicDto = new PersonBasicDto(firstName, lastName,
                LocalDate.of(year, month, day), new CountryDto(countryId));
        PersonService.getInstance().add(personBasicDto);
    }
}
