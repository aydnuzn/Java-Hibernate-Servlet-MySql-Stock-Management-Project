package entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_payment")
public class v_payment {
    @Id
    private Integer totalPayInPrice;
    private Integer totalPayOutPrice;
    private Integer todayPayInPrice;
    private Integer todayPayOutPrice;

    public Integer getTotalPayInPrice() {
        return totalPayInPrice == null ? 0 : totalPayInPrice;
    }

    public void setTotalPayInPrice(Integer totalPayInPrice) {
        this.totalPayInPrice = totalPayInPrice;
    }

    public Integer getTotalPayOutPrice() {
        return totalPayOutPrice == null ? 0 : totalPayOutPrice;
    }

    public void setTotalPayOutPrice(Integer totalPayOutPrice) {
        this.totalPayOutPrice = totalPayOutPrice;
    }

    public Integer getTodayPayInPrice() {
        return todayPayInPrice == null ? 0 : todayPayInPrice;
    }

    public void setTodayPayInPrice(Integer todayPayInPrice) {
        this.todayPayInPrice = todayPayInPrice;
    }

    public Integer getTodayPayOutPrice() {
        return todayPayOutPrice == null ? 0 : todayPayOutPrice;
    }

    public void setTodayPayOutPrice(Integer todayPayOutPrice) {
        this.todayPayOutPrice = todayPayOutPrice;
    }
}
