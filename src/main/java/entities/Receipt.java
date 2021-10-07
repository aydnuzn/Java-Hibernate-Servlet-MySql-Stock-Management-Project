package entities;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int re_id;
    private int totalPrice;
    private int totalPaidPrice;
    private int receipt_number;

    //@ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "receipt")
    private List<DepoOrder> depoOrders;

}
