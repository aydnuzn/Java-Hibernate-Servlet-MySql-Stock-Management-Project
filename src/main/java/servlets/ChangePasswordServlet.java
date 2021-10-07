package servlets;

import entities.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.DBUtil;
import utils.HibernateUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "changePassword", value = "/change-password")
public class ChangePasswordServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGETCall");
        String uuid = req.getParameter("uuid");
        Session sesi = sf.openSession();

        Admin adm = null;
        try {
            DBUtil dbUtil = new DBUtil();
            adm = dbUtil.getUuidAdmin(uuid);

            req.getSession().setAttribute("adm",adm);
        }catch (Exception ex){
            System.out.println("changePassword Error : " + ex);
        }finally {
            sesi.close();
        }
        if(adm != null){
            req.setAttribute("adminInfo", adm);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/changePassword.jsp");
            dispatcher.forward(req,resp);
        }else{
            resp.sendRedirect(Util.base_url + "index.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Admin adm = (Admin) req.getSession().getAttribute("adm");
        String newPassword = req.getParameter("password1");
        String newPassword2 = req.getParameter("password2");
        System.out.println(adm);

        boolean status = newPassword.equals(newPassword2) ? true : false;
        if(adm != null && status) {
            Session sesi = sf.openSession();
            Transaction tr = sesi.beginTransaction();
            Util util = new Util();
            UUID uuid = UUID.randomUUID();
            String strUuid = String.valueOf(uuid);
            adm.setPassword(util.MD5(newPassword));
            adm.setUuid(strUuid);
            try {
                sesi.update(adm);
                tr.commit();
                // session adm delete
                req.getSession().removeAttribute("adm");
            } catch (Exception e) {
                System.out.println("Error : " + e);
                tr.rollback();
            } finally {
                sesi.close();
            }

            resp.sendRedirect(Util.base_url + "index.jsp");

        }else{
            String error = adm == null ? "Şifrenizi zaten değiştirdiniz" : "Girilen şifreler aynı olmalı";
            req.setAttribute("adminInfo", adm);
            req.setAttribute("changePasswordError", error);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/changePassword.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
