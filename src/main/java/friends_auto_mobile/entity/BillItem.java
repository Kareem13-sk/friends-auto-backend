package friends_auto_mobile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "bill_items")
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Integer quantity;

    // ORIGINAL PRODUCT PRICE
    private Double actualPrice;

    // FINAL PRICE AFTER DISCOUNT
    private Double price;

    // DISCOUNT PERCENTAGE
    private Double percentage;

    // FINAL PRICE AFTER PERCENTAGE
    private Double finalPrice;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonBackReference
    private Bill bill;

    public BillItem() {
    }

    // =========================
    // GETTERS AND SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(
            String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(
            Integer quantity) {
        this.quantity = quantity;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(
            Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(
            Double price) {
        this.price = price;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(
            Double percentage) {
        this.percentage = percentage;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(
            Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(
            Double total) {
        this.total = total;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(
            Bill bill) {
        this.bill = bill;
    }
}