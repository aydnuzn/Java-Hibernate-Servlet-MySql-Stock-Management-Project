package entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PaymentOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pout_id;

    private String pout_name;
    private int pout_paymentType;
    private int pout_price;
    private String pout_detail;
    @Temporal(TemporalType.DATE)
    private Date pout_date;

}
