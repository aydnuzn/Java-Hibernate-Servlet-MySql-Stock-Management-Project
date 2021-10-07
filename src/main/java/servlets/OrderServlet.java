package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Customer;
import entities.DepoOrder;
import entities.Product;
import entities.Receipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
import java.util.List;

@WebServlet(name = "orderServlet", value = {"/shopping-cart-post", "/shopping-cart-delete","/shopping-cart-get","/order-add-post"})
public class OrderServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    /// get orders
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet Call");
        String customerCode = req.getParameter("code");
        List<DepoOrder> ls = null;
        Gson gson = new Gson();
        if(!customerCode.equals("")){
            Session sesi = sf.openSession();

            // Seçilen Kullanıcının bilgileri cekiliyor
            Customer csTemp = (Customer) sesi.createQuery("from Customer Where cu_code = ?1")
                    .setParameter(1,Long.parseLong(customerCode))
                    .getSingleResult();

            // Seçilen kullanıcının sepetindeki ürünler getiriliyor.
            // Customer esitlenme sebebi secilen kullanıcının sepeti gelsin diye.
            Query query =  sesi.createQuery("from DepoOrder Where customer = ?1 and order_status = 0")
                    .setParameter(1, csTemp);
            ls = query.getResultList();

    /*     List<DepoOrder> ls = sesi.createNativeQuery("Select * From depoorder AS ord " +
                 "INNER JOIN customer as cu on cu.cu_id = ord.fk_cuId " +
                 "INNER JOIN product as pr on pr.pr_id = ord.fk_prId\n").getResultList();
     */
            // !!! Gson-Json sonsuz döngüye giriyordu. Referans döngüsü sebebiyle. Annotation lar ile yapılıyor
            // fakat denediğimde etkisiz kaldı !!! Geçici bir çözüm !!!
            for (DepoOrder depoOrder : ls) {
                if(depoOrder.getCustomer() != null){
                    depoOrder.getCustomer().setDepoOrders(null);
                    depoOrder.getCustomer().setPaymentsIn(null);
                }
                if(depoOrder.getProduct() != null){
                    depoOrder.getProduct().setDepoOrders(null);
                }
                if(depoOrder.getReceipt() != null){
                    depoOrder.getReceipt().setDepoOrders(null);
                }
            }

            sesi.close();
        }

            String stJson = gson.toJson(ls);
            System.out.println(stJson);
            resp.setContentType("application/json");
            resp.getWriter().write( stJson );

    }

    // insert shopping-cart
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost Call");

        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        DBUtil dbUtil = new DBUtil();
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        int or_id = 0;
        String stJson = "";
        String path = req.getServletPath();
        switch (path){
            case "/shopping-cart-post":
                or_id = 0;
                try {
                    List<Integer> controlList = new ArrayList<>();
                    String obj = req.getParameter("obj");
                    JsonObject object = (JsonObject) parser.parse(obj);
                    String customerCode = object.get("cuCode").getAsString();
                    String productCode = object.get("prCode").getAsString();
                    String fisNo = object.get("or_receiptNumber").getAsString();
                    int fisNumber = Integer.parseInt(fisNo);

                    DepoOrder order = gson.fromJson(obj, DepoOrder.class);
                    order.setOrder_status(0);


                    // Sepete eklenmek istenen ürün adeti stokta yeterince var mı
                    // productCode ile ürün çekilip stoktaki adedi alınacak
                    // ardından  order içindeki quantity adedi ile karşılaştırılacak
                    // eger alınmak istenen stoktakinden az ise ürün sepete eklenecek
                    // degilse eklenmeyecek
                    // Kullanıcının sepetinde eklemek istedigi ürün varsa sepetinde kac tane var onun kontrolude saglanacak

                    Customer customer = (Customer) sesi.createQuery("from Customer Where cu_code = ?1")
                            .setParameter(1, Long.parseLong(customerCode))
                            .getSingleResult();

                    int validCartProduct = dbUtil.validProductCount(customerCode, productCode);


                    Product product = (Product) sesi.createQuery("from Product Where pr_code = ?1")
                            .setParameter(1, Long.parseLong(productCode))
                            .getSingleResult();

                    if( product.getPr_quantity() >= ( order.getOr_quantity() + validCartProduct )){

                        // Sepete urun ilk eklenirken databasede eklensin -- eger fis zaten varsa eklenmesin
                        Query qReceipt = sesi.createQuery("Select count(*) From Receipt Where receipt_number=:fisNo")
                                .setParameter("fisNo",fisNumber);
                        int isValidReceipt = ((Long)qReceipt.getSingleResult()).intValue();

                        if(isValidReceipt == 0){
                            Receipt receipt = new Receipt();
                            receipt.setTotalPaidPrice(0);
                            receipt.setTotalPrice(0);
                            receipt.setReceipt_number(fisNumber);
                            sesi.save(receipt);
                            tr.commit();
                            tr = sesi.beginTransaction();
                        }

                        Receipt receipt = (Receipt) sesi.createQuery("from Receipt Where receipt_number = ?1")
                                .setParameter(1, fisNumber)
                                .getSingleResult();

                        order.setCustomer(customer);
                        order.setProduct(product);
                        order.setReceipt(receipt);


                        int customerId = customer.getCu_id();
                        int productId = product.getPr_id();
                        //    String sql = "Select COUNT(*) From depoorder pr_id=" + productId + " and order_status=0";
                        Query query = sesi.createQuery("Select count(*), or_quantity, or_id From DepoOrder " +
                                        "Where product=:pr and customer=:cs and order_status=0")
                                .setParameter("cs",customer)
                                .setParameter("pr", product);

                        List<Object[]> tempLs = (List<Object[]>)query.getResultList();
                        Object[] tt = tempLs.get(0);
                        //        int tryTemp = ((Long)(tempLs.get(0))[0]).intValue();
                        int validOrderNumber = ((Long)tt[0]).intValue();

                        if(validOrderNumber != 0){
                            Object[] tempObj = tempLs.get(0);
                            int productAmount = (int) tempObj[1];       // DB sepetteki ürün miktarı
                            int validOrderId = (int) tempObj[2];        // DB sepetteki siparis id --> DB deki urunu  cekicez ve uptade yapicaz
                            int validProductAmount = order.getOr_quantity();    // Sepete yeni eklenen urun miktari

                            DepoOrder sss = sesi.get(DepoOrder.class, validOrderId);
                            sss.setOr_quantity(validProductAmount + productAmount);
                            sesi.update(sss);
                        }else{
                            sesi.save(order);
                        }
                        controlList.add(0);
                    }else{
                        controlList.add(1);
                        controlList.add(product.getPr_quantity());
                    }
                    stJson = gson.toJson(controlList);

                }catch ( Exception ex) {
                    System.err.println("Save OR Update Error : " + ex);
                    tr.rollback();
                }
                break;
            case "/order-add-post":
                System.out.println("update oncesi");
                String rcpt_numb = req.getParameter("obj");
                JsonObject object = (JsonObject) parser.parse(rcpt_numb);
                String receiptNumb = object.get("or_receiptNumber").getAsString();
                System.out.println("Fiş Number -> " + receiptNumb);

                try{
                    // Sepetteki Urunler onaylaninca DB ye borc girilecek
                    Receipt reTemp = (Receipt) sesi.createQuery("from Receipt Where receipt_number = ?1")
                            .setParameter(1, Integer.parseInt(receiptNumb))
                            .getSingleResult();

                    List<DepoOrder> dpLs = sesi.createQuery("from DepoOrder Where receipt = ?1 and order_status = 0")
                            .setParameter(1, reTemp)
                            .getResultList();

                    List<String> ls = new ArrayList<>();
                    ls.add("0");

                    boolean control = true;
                    for (DepoOrder item: dpLs) {
                        int productNumber = item.getProduct().getPr_quantity();
                        int basketProductNumber = dbUtil.validProductCount(Long.toString(item.getCustomer().getCu_code()),(Long.toString(item.getProduct().getPr_code())));
                        if(basketProductNumber > productNumber){
                            control = false;
                            String productName = item.getProduct().getPr_name();
                            ls.add(productName + " " + String.valueOf(productNumber) + " adet kalmıştır.");
                        }
                    }
                    ls.add("Almak istediginiz ürün adedini değiştirin.");

                    stJson = gson.toJson(ls);
                    if(control){
                        int kdv = 0;
                        int totalPrice = 0;
                        for (DepoOrder item : dpLs) {
                            int unitPrice = item.getProduct().getPr_sellPrice();
                            int quantity = item.getOr_quantity();
                            int proQuantity = item.getProduct().getPr_quantity();
                            item.getProduct().setPr_quantity(proQuantity - quantity);

                            switch(item.getProduct().getPr_kdv()) {
                                case 0: kdv = 0; break;
                                case 1: kdv = 1; break;
                                case 2: kdv = 8; break;
                                case 3: kdv = 18; break;
                                default: break;
                            }



                            int price = unitPrice * quantity;
                            totalPrice += price + Math.floor(price*kdv/100);
                            item.setOrder_status(1);
                            //         sesi.update(item);
                            or_id = (int) item.getCustomer().getCu_code();
                            stJson = ""+or_id;
                        }
                        reTemp.setTotalPrice(totalPrice);
                    }
                }catch (Exception ex){
                    System.err.println("Update or Get Error -> " + ex);
                }

                System.out.println("update sonrasi" + or_id);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + path);
        }

        tr.commit();
        sesi.close();

        resp.setContentType("application/json");
        resp.getWriter().write(stJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doDelete Call");

        long return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            int or_id = Integer.parseInt( req.getParameter("orderId") );
            DepoOrder temp = sesi.get(DepoOrder.class, or_id);
        //    DepoOrder order = sesi.load(DepoOrder.class, or_id);
            sesi.delete(temp);
            tr.commit();
            return_id = temp.getCustomer().getCu_code();
        }catch (Exception ex) {
            System.err.println("Delete Error : " + ex);
        }finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write( ""+return_id );
    }
}
