package servlets;

import com.google.gson.Gson;
import entities.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "customerServlet", value = { "/customer-post", "/customer-delete", "/customer-get", "/search-get" })
public class CustomerServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    // /customer-insert
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int cid = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            String obj = req.getParameter("obj");
            Gson gson = new Gson();
            Customer customer = gson.fromJson(obj, Customer.class);
            sesi.saveOrUpdate(customer);
            tr.commit();
            cid = 1;
        }catch ( Exception ex) {
            System.err.println("Save OR Update Error : " + ex);
        }finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write( "" +cid );
    }

    // All Customers list -- Search Customer list
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session sesi = sf.openSession();
        Gson gson = new Gson();
        String path = req.getServletPath();

        List<Customer> ls = null;
        switch(path){
            case "/search-get":
                String searchText = req.getParameter("searchC");
                searchText = searchText.substring(1,searchText.length()-1);
                searchText = searchText.trim().replaceAll("\\s+","*")+"*";

                ls = sesi
                        .createSQLQuery("CALL fullTextSearch(:data)")
                        .addEntity(Customer.class)
                        .setParameter("data",searchText)
                        .getResultList();
                break;
            case "/customer-get":
                ls = sesi.createQuery("from Customer Where cu_isActive=true").getResultList();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + path);
        }
        sesi.close();
        for (Customer cs : ls) {
            cs.setDepoOrders(null);
            cs.setPaymentsIn(null);
        }

        String stJson = null;
        stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write( stJson );
    }

    // /customer-remove
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            int cu_id = Integer.parseInt( req.getParameter("cu_id") );
            Customer customer = sesi.load(Customer.class, cu_id);
        //    sesi.delete(customer); // customer status -> passive

            customer.setCu_isActive(false);
            sesi.update(customer);

            tr.commit();
            return_id = customer.getCu_id();
        }catch (Exception ex) {
            System.err.println("Delete Error : " + ex);
        }finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write( ""+return_id );
    }


}
