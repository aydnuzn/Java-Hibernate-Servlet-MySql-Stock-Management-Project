package entities;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cu_id;

    // BU kısım lazy iken datalar zamanında gelmedi. Sanırım bekleme koymak gerekiyor???
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<PaymentIn> paymentsIn;

    //@ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DepoOrder> depoOrders;

    private String cu_name;
    private String cu_surname;
    private String cu_company_title;
    @Column(unique = true)
    private long cu_code;
    private int cu_status;
    private int cu_tax_number;
    private String cu_tax_administration;
    @Column(length = 500)
    private String cu_address;
    private String cu_mobile;
    private String cu_phone;
    @Column(length = 500)
    private String cu_email;
    @Column(length = 32)
    private String cu_password;
    private boolean cu_isActive;

    public int getCu_id() {
        return cu_id;
    }

    public void setCu_id(int cu_id) {
        this.cu_id = cu_id;
    }

    public List<PaymentIn> getPaymentsIn() {
        return paymentsIn;
    }

    public void setPaymentsIn(List<PaymentIn> paymentsIn) {
        this.paymentsIn = paymentsIn;
    }

    public List<DepoOrder> getDepoOrders() {
        return depoOrders;
    }

    public void setDepoOrders(List<DepoOrder> depoOrders) {
        this.depoOrders = depoOrders;
    }

    public String getCu_name() {
        return cu_name;
    }

    public void setCu_name(String cu_name) {
        this.cu_name = cu_name;
    }

    public String getCu_surname() {
        return cu_surname;
    }

    public void setCu_surname(String cu_surname) {
        this.cu_surname = cu_surname;
    }

    public String getCu_company_title() {
        return cu_company_title;
    }

    public void setCu_company_title(String cu_company_title) {
        this.cu_company_title = cu_company_title;
    }

    public long getCu_code() {
        return cu_code;
    }

    public void setCu_code(long cu_code) {
        this.cu_code = cu_code;
    }

    public int getCu_status() {
        return cu_status;
    }

    public void setCu_status(int cu_status) {
        this.cu_status = cu_status;
    }

    public int getCu_tax_number() {
        return cu_tax_number;
    }

    public void setCu_tax_number(int cu_tax_number) {
        this.cu_tax_number = cu_tax_number;
    }

    public String getCu_tax_administration() {
        return cu_tax_administration;
    }

    public void setCu_tax_administration(String cu_tax_administration) {
        this.cu_tax_administration = cu_tax_administration;
    }

    public String getCu_address() {
        return cu_address;
    }

    public void setCu_address(String cu_address) {
        this.cu_address = cu_address;
    }

    public String getCu_mobile() {
        return cu_mobile;
    }

    public void setCu_mobile(String cu_mobile) {
        this.cu_mobile = cu_mobile;
    }

    public String getCu_phone() {
        return cu_phone;
    }

    public void setCu_phone(String cu_phone) {
        this.cu_phone = cu_phone;
    }

    public String getCu_email() {
        return cu_email;
    }

    public void setCu_email(String cu_email) {
        this.cu_email = cu_email;
    }

    public String getCu_password() {
        return cu_password;
    }

    public void setCu_password(String cu_password) {
        this.cu_password = cu_password;
    }

    public boolean isCu_isActive() {
        return cu_isActive;
    }

    public void setCu_isActive(boolean cu_isActive) {
        this.cu_isActive = cu_isActive;
    }
}
