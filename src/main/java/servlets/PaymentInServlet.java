package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import utils.DBUtil;
import utils.HibernateUtil;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "paymentInServlet", value = {"/unpaid-receipt", "/payment-in","/all-payment-in","/search-payment-in","/payin-delete"})
public class PaymentInServlet extends HttpServlet {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    // get Customer UnPaid Receipt
    // get AllPaymentIn
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet Call");
        Session sesi = sf.openSession();
        Gson gson = new Gson();
        DBUtil dbUtil = new DBUtil();
        String stJson = "";
        String path = req.getServletPath();
        switch (path){
            case "/unpaid-receipt":
                String customerCode = req.getParameter("code");
                List<Integer> ls = null;
                if(!customerCode.equals("")){
                    ls = dbUtil.customerUnpaidReceipts(Integer.parseInt(customerCode));
                }
                stJson = gson.toJson(ls);
                System.out.println(stJson);
                break;
            case "/all-payment-in":
                List<Object[]> paymentInList = dbUtil.getAllPaymentIn();
                System.out.println("deneme");
                stJson = gson.toJson(paymentInList);
                break;
            case "/search-payment-in":
                String searchText = req.getParameter("searchPayIn");
                searchText = searchText.substring(1,searchText.length()-1).trim();

                List<Object[]> ls1 = dbUtil.getPaymentInSearch(searchText);
                stJson = gson.toJson(ls1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + path);
        }
        sesi.close();
        resp.setContentType("application/json");
        resp.getWriter().write( stJson );

    }

    // insert payment-in
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost Call");
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        String obj = req.getParameter("obj");
        JsonObject object = (JsonObject) parser.parse(obj);
        Long customerCode = object.get("cuCode").getAsLong();
    //    int receiptNumber = object.get("receipt_number").getAsInt();

        Session sesi = sf.openSession();

        try{
            PaymentIn payment_in = gson.fromJson(obj, PaymentIn.class);
            Customer customer = (Customer) sesi.createQuery("from Customer where cu_code = :cCode")
                    .setParameter("cCode", customerCode)
                    .getSingleResult();

            Date date = new Date();
            payment_in.setCustomer(customer);
            payment_in.setPin_date(date);

            List<Integer> controlList = new ArrayList<>();

            int nowPaidPrice = payment_in.getPin_price();
            DBUtil dbUtil = new DBUtil();
            Receipt receipt = dbUtil.getReceipt(payment_in.getReceipt_number());
            int totalPaidPrice = receipt.getTotalPaidPrice() + nowPaidPrice;
            if( receipt.getTotalPrice() >= totalPaidPrice ){
                // bu fişe ait öden tutar ile şuanki ödenen miktar, toplam odenmesi gerekenden kucukse devam et
                sesi.close();
                sesi = sf.openSession();
                Transaction tr = sesi.beginTransaction();
                receipt.setTotalPaidPrice(totalPaidPrice);
                sesi.update(receipt);
                sesi.save(payment_in);
                tr.commit();
                controlList.add(0);
                controlList.add(receipt.getTotalPrice() - receipt.getTotalPaidPrice());
            }else{
                System.out.println("Odenecek tutar, odenmesi gereken miktardan fazladır.");
                controlList.add(1);
                controlList.add(totalPaidPrice - receipt.getTotalPrice());
            }

            String stJson = gson.toJson(controlList);
            resp.setContentType("application/json");
            resp.getWriter().write(stJson);
        }catch(Exception ex){
            System.err.println("Error occured -> " + ex);
        }finally{
            sesi.close();
        }
    }

    // PaymentIn Delete
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doDelete Call");

        int return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            int pin_id = Integer.parseInt(req.getParameter("pin_id"));
            PaymentIn paymentIn = sesi.load(PaymentIn.class, pin_id);

            // Silme işlemi yapilmadan once silinecek odeme icin fiş bilgileri çekilir
            // Silme basarili olursa bu fiş için ödenen miktar, silinen ödemeye göre azalır.
            int receiptNumber = paymentIn.getReceipt_number();
            Receipt receipt = (Receipt) sesi.createQuery("from Receipt Where receipt_number = :recNumber")
                    .setParameter("recNumber", receiptNumber)
                    .getSingleResult();

            int deletePaidPrice = paymentIn.getPin_price();
            int paidPriceforReceipt = receipt.getTotalPaidPrice();
            receipt.setTotalPaidPrice(paidPriceforReceipt - deletePaidPrice);
            sesi.update(receipt);
            sesi.delete(paymentIn);
            tr.commit();
            return_id = paymentIn.getPin_id();
        } catch (Exception e) {
            System.err.println("Delete Error : " + e);
            tr.rollback();
        }finally {
            sesi.close();
        }
        resp.setContentType("application/json");
        resp.getWriter().write("" + return_id);
    }

}
