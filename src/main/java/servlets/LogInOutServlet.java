package servlets;

import entities.Admin;
import utils.DBUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "logInOutServlet", value = {"/login-servlet", "/logout-servlet"})
public class LogInOutServlet extends HttpServlet {

    // logout-servlet
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // session items delete
        req.getSession().removeAttribute("aid");
        req.getSession().removeAttribute("fullName");

        // all session remove
        req.getSession().invalidate();

        // cookie delete
        Cookie cookie = new Cookie("admin", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        resp.sendRedirect(Util.base_url + "index.jsp");
    }

    // login-servlet
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        System.out.println("email = " + email + " password = " + password + " remember = " + remember);

        DBUtil util = new DBUtil();
        Admin adm_status = util.login(email, password, remember, req, resp);
        if ( adm_status != null ) {
            //login success
            resp.sendRedirect( Util.base_url + "dashboard.jsp" );
        } else {
            // login error
            req.setAttribute("error", "Mail veya Şifre Hatalı!");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }

    }

}
