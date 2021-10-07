package servlets;

import entities.Admin;
import smtp.MailSend;
import utils.DBUtil;
import utils.Util;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "messageServlet", value="/message-servlet")
public class MessageServlet extends HttpServlet {

    @EJB
    private MailSend mailSender = new MailSend();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String senderMail = req.getParameter("email");
        // email isValid Control
        DBUtil dbUtil = new DBUtil();
        Admin adm = dbUtil.mailIsValid(senderMail);
        String infoMessageCntrl = "";
        if(adm != null){
            String strUuid = adm.getUuid();
            String pathUrl = Util.base_url + "change-password?uuid=" + strUuid;

            // *** Mail Send Start ***
            String fromEmail = "musicwrldmail@gmail.com";
            String username = "musicwrldmail";
            String password = "xyz123zyx";

            String email = senderMail;
            boolean sendMessage = mailSender.sendEmail(fromEmail, username, password, email, pathUrl);
            //    boolean sendMessage = true;
            // *** Mail Send Finish ****
            infoMessageCntrl = "";
            if ( sendMessage ) {
                System.out.println("Mesaj Gönderildi");
                infoMessageCntrl = "1";
                //    resp.sendRedirect(Util.base_url + "index.jsp"); --> send in ajax
            }else {
                System.out.println("HATA! Mesaj Gönderilemedi!");
                infoMessageCntrl = "2";
            }
        }else{
            System.out.println("Girilen email sistemde mevcut değil");
            infoMessageCntrl = "3";
        }

        resp.setContentType("application/json");
        resp.getWriter().write(infoMessageCntrl);
    }

}
