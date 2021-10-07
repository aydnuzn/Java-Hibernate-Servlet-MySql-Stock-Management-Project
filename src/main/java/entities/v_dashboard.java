package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_dashboard")
public class v_dashboard {
    @Id
    private Integer customerNumber;

    private Integer orderNumber;
    private Integer productTypeNumber;
    private Integer productNumber;
    private Integer totalBuyPrice;
    private Integer totalSellPrice;

    public Integer getCustomerNumber() {
        return customerNumber == null ? 0 : customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber == null ? 0 : orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getProductTypeNumber() {
        return productTypeNumber == null ? 0 : productTypeNumber;
    }

    public void setProductTypeNumber(Integer productTypeNumber) {
        this.productTypeNumber = productTypeNumber;
    }

    public Integer getProductNumber() {
        return productNumber == null ? 0 : productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getTotalBuyPrice() {
        return totalBuyPrice == null ? 0 : totalBuyPrice;
    }

    public void setTotalBuyPrice(Integer totalBuyPrice) {
        this.totalBuyPrice = totalBuyPrice;
    }

    public Integer getTotalSellPrice() {
        return totalSellPrice == null ? 0 : totalSellPrice;
    }

    public void setTotalSellPrice(Integer totalSellPrice) {
        this.totalSellPrice = totalSellPrice;
    }
}
