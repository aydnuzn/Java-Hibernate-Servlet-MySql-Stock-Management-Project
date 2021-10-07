package utils;

import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DBUtil {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    public Admin login(String email, String password, String remember, HttpServletRequest req, HttpServletResponse resp) {
        Session sesi = sf.openSession();
        Admin adm = null;
        try {
            String sql = "from Admin Where email = ?1 and password = ?2";
            // ? yanına hangi degerin hangi soru işareti yerine geleceği belirlemek için, ? yanına degeri giriyoruz   --> içinde a geçenleri arıcaz
            adm = (Admin) sesi
                .createQuery(sql)
                .setParameter(1, email)
                .setParameter(2, Util.MD5(password))
                .getSingleResult();

            System.out.println("adm-email : " + adm.getEmail());

            if(adm != null){
                int aid = adm.getAid();
                String fullName = adm.getFullName();

                req.getSession().setAttribute("aid", aid);
                req.getSession().setAttribute("fullName", fullName);

                // cookie create de bunun altına gelecek ve remember bu cookie de kullanılacak
                if ( remember != null && remember.equals("on")) {
                    fullName = fullName.replaceAll(" ", "_");
                    String val = aid+"_"+fullName;
                    Cookie cookie = new Cookie("admin", val);
                    cookie.setMaxAge( 60*60 );
                    resp.addCookie(cookie);
                }
            }

        } catch (Exception e) {
            System.err.println("addLogin Error : " + e);
        } finally {
            sesi.close();
        }
        return adm;
    }

    // mail isValid
    public Admin mailIsValid(String email){
        Session sesi = sf.openSession();
        Admin adm = null;
        try {
            String sql = "from Admin Where email = ?1";

            adm = (Admin) sesi
                    .createQuery(sql)
                    .setParameter(1, email)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("mailIsValid Error : " + e);
        }finally {
            sesi.close();
        }
        return adm;
    }

    // admin info
    public Admin getUuidAdmin(String uuid){
        Session sesi = sf.openSession();
        Admin adm = null;
        try {
            String sql = "from Admin Where uuid = ?1";

            adm = (Admin) sesi
                    .createQuery(sql)
                    .setParameter(1, uuid)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("uuid Error : " + e);
        }finally {
            sesi.close();
        }
        return adm;
    }

    // all Customer List
    public List<Customer> getAllCustomer(){
        List<Customer> ls = null;
        Session sesi = sf.openSession();

        try {
            ls = sesi.createQuery("from Customer where cu_isActive=true").getResultList();
        } catch (Exception e) {
            System.err.println("getAllCustomer Error : " + e);
        } finally {
            sesi.close();
        }
        return ls;
    }

    // all Product List
    public List<Product> getAllProduct(){
        List<Product> ls = null;
        Session sesi = sf.openSession();

        try {
            ls = sesi.createQuery("from Product Where pr_quantity > 0").getResultList();
        } catch (Exception e) {
            System.err.println("getAllProduct Error : " + e);
        } finally {
            sesi.close();
        }
        return ls;
    }

    // validProductCount in basket
    public int validProductCount(String customerCode, String productCode){
        Session sesi = sf.openSession();
        int count = 0;
        try {
            DepoOrder orderTemp = (DepoOrder) sesi.createQuery("from DepoOrder Where customer.cu_code = ?1 and product.pr_code = ?2 and order_status = 0")
                    .setParameter(1, Long.parseLong(customerCode))
                    .setParameter(2, Long.parseLong(productCode))
                    .getSingleResult();
            count = orderTemp.getOr_quantity();
        } catch (Exception e) {
        } finally {
            sesi.close();
        }
        return count;
    }

    // payment-in Customers
    public List<Object[]> payInCustomers(){
        Session sesi = sf.openSession();
        List<Object[]> temp = null;
        try {
            temp = sesi.createSQLQuery("Select DISTINCT cs.cu_code, cs.cu_name From receipt as re " +
                            "INNER JOIN depoorder as ord ON re.re_id = ord.fk_reId " +
                            "INNER JOIN customer as cs ON cs.cu_id = ord.fk_cuId " +
                            "Where totalPrice > totalPaidPrice")
                    .addScalar("cu_code", LongType.INSTANCE)
                    .addScalar("cu_name", StringType.INSTANCE)
                    .list();
        } catch (Exception e) {
            System.out.println("payInCustomers Error : " + e);
        } finally {
            sesi.close();
        }
        return temp;
    }

    // Customer Unpaid Receipt
    public List<Integer> customerUnpaidReceipts(int cu_code){
        Session sesi = sf.openSession();
        List<Integer> unPaidReceiptList = null;
        try {
            unPaidReceiptList = sesi.createNativeQuery("Select DISTINCT re.receipt_number, re.totalPrice, re.totalPaidPrice From customer as cs " +
                    "INNER JOIN depoorder as ord ON cs.cu_id = ord.fk_cuId " +
                    "INNER JOIN receipt as re ON ord.fk_reId = re.re_id " +
                    "Where cs.cu_code = :cCode and re.totalPrice > re.totalPaidPrice")
                    .setParameter("cCode", cu_code).getResultList();
        } catch (Exception e) {
            System.out.println("customerUnpaidReceipts Error : " + e);
        } finally {
            sesi.close();
        }
        return unPaidReceiptList;
    }

    // get Receipt
    public Receipt getReceipt(int receiptNumber){
        Session sesi = sf.openSession();
        Receipt receipt = null;
        try {
            receipt = (Receipt) sesi.createQuery("from Receipt Where receipt_number = :receiptNumber")
                    .setParameter("receiptNumber", receiptNumber).getSingleResult();
        } catch (Exception e) {
            System.err.println("getReceipt Error : " + e );
        }finally {
            sesi.close();
        }
        return receipt;
    }

    // getAll Payment-in
    public List<Object[]> getAllPaymentIn(){
        List<Object[]> ls = null;
        Session sesi = sf.openSession();
        try{
            ls = sesi.createQuery("Select p.id, p.customer.cu_name, p.customer.cu_surname, p.receipt_number, p.pin_price " +
                    "from PaymentIn as p").getResultList();
        }catch(Exception ex){
            System.err.println("getAllPaymentIn Error : " + ex);
        }
        sesi.close();
        return ls;
    }

    // view Dashboard
    public List<v_dashboard> viewDashboard(){
        List<v_dashboard> ls = null;
        Session sesi = sf.openSession();
        try {
            ls = sesi.createQuery("from v_dashboard ").getResultList();
        } catch (Exception e) {
            System.err.println("viewDashboard Error : " + e);
        } finally {
            sesi.close();
        }
        return ls;
    }

    // view payment
    public List<v_payment> viewPayment(){
        List<v_payment> ls = null;
        Session sesi = sf.openSession();

        try {
            ls = sesi.createQuery("from v_payment").getResultList();
        } catch (Exception e) {
            System.err.println("viewPayment Error : " + e);
        } finally {
            sesi.close();
        }
        return ls;
    }

    public List<Object[]> lastFiveOrder() {
        Session sesi = sf.openSession();
        List<Object[]> ls = null;
        try{
            ls = sesi.createSQLQuery("Select DISTINCT re.receipt_number, cs.cu_name, re.totalPrice FROM depoorder as dp " +
                    "INNER JOIN customer as cs ON dp.fk_cuId = cs.cu_id " +
                    "INNER JOIN receipt as re ON re.re_id = dp.fk_reId " +
                    "WHERE dp.order_status = 1 and cs.cu_isActive = true " +
                    "ORDER BY or_id DESC LIMIT 0,5").list();
        }catch(Exception ex){
            System.err.println("getLastOrders Error : " + ex);
        }finally{
            sesi.close();
        }
        return ls;
    }

    public List<Product> lastFiveProduct() {
        // get last 5 Product
        List<Product> prList = null;
        Session sesi = sf.openSession();
        try {
            prList = sesi.createSQLQuery("Select * From product ORDER BY pr_id DESC LIMIT 0,5;")
                    .addEntity(Product.class)
                    .list();
        } catch (Exception ex) {
            System.err.println("getLastProducts Error : " + ex);
        } finally {
            sesi.close();
        }
        return prList;
    }

    // get Customer PaidReceipt
    public List<Object[]> getCustomerPaidReceipt(){
        List<Object[]> ls = null;
        Session sesi = sf.openSession();
        try {
            ls = sesi.createNativeQuery("Select DISTINCT cs.cu_code,cs.cu_name From paymentin as pin " +
                    "INNER JOIN customer as cs ON pin.fk_cuId = cs.cu_id").getResultList();
        } catch (Exception ex) {
            System.err.println("getCustomerPaidReceipt Error : " + ex);
        } finally {
            sesi.close();
        }
        return  ls;
    }

    // get paymentIn Search List
    public List<Object[]> getPaymentInSearch(String searchData){
        List<Object[]> ls = null;
        Session sesi = sf.openSession();
        try {
            ls = sesi.createSQLQuery("CALL paymentInSearch(:data)")
                    .setParameter("data", searchData)
                    .getResultList();
        } catch (Exception ex) {
            System.err.println("getPaymentOutSearch Error : " + ex);
        }finally {
            sesi.close();
        }
        return ls;
    }


    // get paymentOut Search List
    public List<PaymentOut> getPaymentOutSearch(String searchData){
        List<PaymentOut> ls = null;
        Session sesi = sf.openSession();
        try {
            ls = sesi.createSQLQuery("CALL payOutFullTextIndex(:data)")
                    .addEntity(PaymentOut.class)
                    .setParameter("data", searchData)
                    .getResultList();
        } catch (Exception ex) {
            System.err.println("getPaymentOutSearch Error : " + ex);
        }finally {
            sesi.close();
        }
        return ls;
    }

}
