package entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PaymentIn {
    @Id
    @GeneratedValue
    private int pin_id;

    //private int fk_cuId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_cuId")
    private Customer customer;

    private int pin_price;
    private String pin_detail;
    private int receipt_number;

    @Temporal(TemporalType.DATE)
    private Date pin_date;

}
