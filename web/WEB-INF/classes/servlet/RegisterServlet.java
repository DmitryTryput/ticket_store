package servlet;

import by.ticketstore.dto.CheckUserEmailDto;
import by.ticketstore.dto.RegisterUserDto;
import by.ticketstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static util.ServletUtil.createViewPath;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        goTo(req, resp, "register");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getParameterMap().forEach((name, array) -> System.out.println(name + " :" + Arrays.deepToString(array)));
        if (req.getParameter("password").equals(req.getParameter("checkPassword"))) {
            if (checkEmail(req)) {
                UserService.getInstance().add(new RegisterUserDto(req.getParameter("email"),
                        req.getParameter("password"), req.getParameter("firstName"),
                        req.getParameter("lastName")));
                req.setAttribute("result", "Регистрация прошла успешно");
                goTo(req, resp, "login");
            } else {
                req.setAttribute("result", "Данный почтовый адресс уже используется");
                goTo(req, resp, "register");
            }
        } else {
            req.setAttribute("result", "Пароли не совпадают");
            goTo(req, resp, "register");
        }

    }


    public boolean checkEmail(HttpServletRequest req) {
        String email = req.getParameter("email");
        return UserService.getInstance().checkEmail(new CheckUserEmailDto(email));
    }

    public void goTo(HttpServletRequest req, HttpServletResponse resp, String reference) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher(createViewPath(reference))
                .forward(req, resp);
    }

}
