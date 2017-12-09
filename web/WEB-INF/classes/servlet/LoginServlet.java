package servlet;

import by.ticketstore.dto.LoginUserDto;
import by.ticketstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static util.ServletUtil.createViewPath;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher(createViewPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            authentication(req, email, password);
            resp.sendRedirect("upcoming");
        } catch (IllegalArgumentException e) {
            req.setAttribute("result", e.getMessage());
            getServletContext()
                    .getRequestDispatcher(createViewPath("login"))
                    .forward(req, resp);
        }
    }

    private void authentication(HttpServletRequest req, String email, String password) {
        LoginUserDto loginUserDto = UserService.getInstance().loginUser(new LoginUserDto(email, password));
        HttpSession session = req.getSession();
        session.setAttribute("id", loginUserDto.getId());
        session.setAttribute("firstName", loginUserDto.getFirstName());
        session.setAttribute("lastName", loginUserDto.getLastName());
        session.setAttribute("value", loginUserDto.getValue());
        session.setAttribute("role", loginUserDto.getRole());
    }
}
