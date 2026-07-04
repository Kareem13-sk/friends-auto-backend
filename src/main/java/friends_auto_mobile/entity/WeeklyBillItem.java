package friends_auto_mobile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "weekly_bill_items")
public class WeeklyBillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Integer quantity;

    // Original Product Price
    private Double actualPrice;

    // Discount Percentage
    private Double percentage;

    // Price After Discount
    private Double finalPrice;

    // Same as finalPrice (kept for compatibility)
    private Double price;

    // Total Amount
    private Double total;

    @ManyToOne
    @JoinColumn(name = "weekly_bill_id")
    @JsonBackReference
    private WeeklyBill weeklyBill;

    public WeeklyBillItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public WeeklyBill getWeeklyBill() {
        return weeklyBill;
    }

    public void setWeeklyBill(WeeklyBill weeklyBill) {
        this.weeklyBill = weeklyBill;
    }
}