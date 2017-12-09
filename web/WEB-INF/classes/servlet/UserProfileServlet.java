package servlet;

import by.ticketstore.dto.UserInfoDto;
import by.ticketstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static util.ServletUtil.createViewPath;

@WebServlet("/user-profile")
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("id");
        if (!id.equals(userId)) {
            try {
                UserInfoDto userInfoDto = UserService.getInstance().userProfile(id);
                req.setAttribute("user", userInfoDto);
                getServletContext()
                        .getRequestDispatcher(createViewPath("user-profile"))
                        .forward(req, resp);

            } catch (IllegalArgumentException e) {
                resp.sendRedirect(req.getHeader("Referer"));
            }
        }
        resp.sendRedirect("user");
    }
}
