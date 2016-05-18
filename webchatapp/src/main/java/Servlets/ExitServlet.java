package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 13/05/2016.
 */
@WebServlet(value= "/chat/exit")
public class ExitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = ((HttpServletRequest) req).getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LoginServlet.COOKIE_USER_ID)) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
        }

        req.getSession().invalidate();

        getServletContext().getRequestDispatcher("/loginServ").forward(req, resp);
    }
}
