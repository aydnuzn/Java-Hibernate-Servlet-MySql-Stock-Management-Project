package entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class DepoOrder {
    @Id
    @GeneratedValue
    private int or_id;
    private int or_quantity;
 //   private int or_receiptNumber;
    private int order_status;
 //   private int orderTotalPrice;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "fk_cuId")
     @ToString.Exclude
     private Customer customer;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "fk_prId")
     @ToString.Exclude
     private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_reId")
    @ToString.Exclude
    private Receipt receipt;


}