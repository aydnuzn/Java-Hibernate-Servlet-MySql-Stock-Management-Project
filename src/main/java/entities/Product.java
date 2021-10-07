package entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pr_id;

    //@ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<DepoOrder> depoOrders;

    private long pr_code;
    private String pr_name;
    private int pr_buyPrice;
    private int pr_sellPrice;
    private int pr_kdv;
    private int pr_unitType;
    @Column(length = 500)
    private String pr_detail;
    private int pr_quantity;

}
