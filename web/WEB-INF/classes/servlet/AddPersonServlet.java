package servlet;

import by.ticketstore.dto.CountryDto;
import by.ticketstore.dto.CreatePerson;
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
        getServletContext()
                .getRequestDispatcher(createViewPath("add-person"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreatePerson createPerson = new CreatePerson(req.getParameter("firstName"),
                req.getParameter("lastName"),
                LocalDate.of(Integer.valueOf(req.getParameter("year")), Integer.valueOf(req.getParameter("month"))
                        , Integer.valueOf(req.getParameter("day"))), new CountryDto(req.getParameter("country")));
        System.out.println(createPerson);
        PersonService personService = PersonService.getInstance();
        personService.add(createPerson);


    }
}
