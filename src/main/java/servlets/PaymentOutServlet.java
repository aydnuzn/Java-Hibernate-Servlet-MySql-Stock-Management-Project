package servlets;

import com.google.gson.Gson;
import entities.Customer;
import entities.PaymentOut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.DBUtil;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name="paymentOutServlet", value = {"/payout-post", "/payout-delete", "/payout-get","/search-payment-out"})
public class PaymentOutServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    // All PaymentOut List
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet Call");
        DBUtil dbUtil = new DBUtil();
        Session sesi = sf.openSession();
        Gson gson = new Gson();
        String path = req.getServletPath();
        List<PaymentOut> ls = null;
        String stJson = "";
        switch(path){
            case "/payout-get":
                ls = sesi.createQuery("from PaymentOut").getResultList();
                break;
            case "/search-payment-out":
                String searchText = req.getParameter("searchPayOut");
                searchText = searchText.substring(1,searchText.length()-1);
                searchText = searchText.trim().replaceAll("\\s+","*")+"*";

                ls = dbUtil.getPaymentOutSearch(searchText);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + path);
        }

        sesi.close();
        stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write(stJson);
    }

    // PaymentOut Insert
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost  Call");

        int pout_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            Gson gson = new Gson();


            // Kasada kalan mevcut miktar - start
            DBUtil dbUtil = new DBUtil();
            int safeTotalPrice = dbUtil.viewPayment().get(0).getTotalPayInPrice() - dbUtil.viewPayment().get(0).getTotalPayOutPrice();
            // Kasada kalan mevcut miktar - finish

            // Şuan çekilmek istenen miktar - start
            String obj = req.getParameter("obj");
            PaymentOut paymentOut = gson.fromJson(obj, PaymentOut.class);
            int getMoneySafe =  paymentOut.getPout_price();
            // Şuan çekilmek istenen miktar - finish

            List<Integer> controlList = new ArrayList<>();
            if( safeTotalPrice >= getMoneySafe ){
                // kasadaki toplam miktar, çekilmek istenen paradan büyükse
                Date date = new Date();
                paymentOut.setPout_date(date);
                sesi.save(paymentOut);
                tr.commit();
                controlList.add(0);
                controlList.add(safeTotalPrice - getMoneySafe); //Kasada kalan para
            }else{
                System.out.println("Odenecek tutar, odenmesi gereken miktardan fazladır.");
                controlList.add(1);
                controlList.add(getMoneySafe - safeTotalPrice); // Kasadaki mevcut paradan çekilmeye çalışılan fazlalık fiyat.
            }
            String stJson = gson.toJson(controlList);
            resp.setContentType("application/json");
            resp.getWriter().write(stJson);
        } catch (Exception e) {
            System.err.println("paymentOut Save Error : " + e);
        }finally {
            sesi.close();
        }

    }

    // PaymentOut Delete
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doDelete Call");

        int return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            int pout_id = Integer.parseInt(req.getParameter("pout_id"));
            PaymentOut paymentOut = sesi.load(PaymentOut.class, pout_id);
            sesi.delete(paymentOut);
            tr.commit();
            return_id = paymentOut.getPout_id();
        } catch (Exception e) {
            System.err.println("Delete Error : " + e);
        }finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write("" + return_id);
    }
}
