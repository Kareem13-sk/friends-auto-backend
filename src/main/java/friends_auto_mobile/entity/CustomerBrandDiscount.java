package friends_auto_mobile.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_brand_discount")
public class CustomerBrandDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private String customerName;

    // NEW FIELD
    private String customerType;

    private String brand;

    private Double discountPercentage;

    public CustomerBrandDiscount() {
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // NEW GETTER
    public String getCustomerType() {
        return customerType;
    }

    // NEW SETTER
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}