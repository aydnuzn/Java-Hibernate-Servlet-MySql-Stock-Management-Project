package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Customer;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name="paymentServlet", value = {"/payment-getCustomer","/payment-getSafe"})
public class PaymentSevlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    // get Customer Payment
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost Call");
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        String obj = req.getParameter("obj");
        JsonObject object = (JsonObject) parser.parse(obj);
        Long customerCode = object.get("cuCode").getAsLong();
        String dateStart = object.get("date_in").getAsString();
        String dateEnd = object.get("date_out").getAsString();
        String paymentType = object.get("paymentType").getAsString();


        Session sesi = sf.openSession();
        List<Object[]> ls = null;
        try {
            ls = sesi.createSQLQuery("CALL customerPayIn(:c_code,:in_date,:out_date)")
                    .setParameter("c_code", customerCode)
                    .setParameter("in_date", dateStart)
                    .setParameter("out_date", dateEnd)
                    .getResultList();
            System.out.println("deneme");
        }catch (Exception ex){
            System.err.println("Ekleme Error : " + ex);
        }
        sesi.close();

        String stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write(stJson);

    }

    // get Payment Safe
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet Call");
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        String obj = req.getParameter("obj");
        JsonObject object = (JsonObject) parser.parse(obj);
        String customerCode = object.get("cuCode").getAsString();
    //    Long customerCode = object.get("cuCode").getAsLong();
        String dateStart = object.get("date_in").getAsString();
        String dateEnd = object.get("date_out").getAsString();
        String paymentType = object.get("paymentType").getAsString();

        Session sesi = sf.openSession();
        List<Object[]> ls = null;
        String stJson = "";
        switch (paymentType){
            case "0":
                System.out.println("Giriş dataları ayarlandı");
                ls = sesi.createSQLQuery("CALL getSafeDate(:dateStart,:dateFinish)")
                        .setParameter("dateStart", dateStart)
                        .setParameter("dateFinish", dateEnd)
                        .getResultList();

                stJson = gson.toJson(ls);
                break;
            case "1":
                System.out.println("Çıkış dataları ayarlandı");
                ls =  sesi.createNativeQuery("Select * From paymentout Where pout_date BETWEEN CAST(:dateStart AS DATE) and CAST(:dateEnd AS DATE)")
                        .setParameter("dateStart", dateStart)
                        .setParameter("dateEnd", dateEnd)
                        .getResultList();
                stJson = gson.toJson(ls);
                break;
            default:
                System.out.println("Payment degiskeni degeri degistirilmiş.");
        }
        sesi.close();

        resp.setContentType("application/json");
        resp.getWriter().write(stJson);


    }
}
