package friends_auto_mobile.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weekly_bills")
public class WeeklyBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    // Products Total
    private Double totalAmount;

    // Amount Paid
    private Double paidAmount;

    // Remaining Balance
    private Double balanceAmount;

    // Previous Pending Amount
    private Double previousBalance;

    // Bill Date
    private LocalDate billDate;

    @OneToMany(
            mappedBy = "weeklyBill",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<WeeklyBillItem> items = new ArrayList<>();

    public WeeklyBill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(Double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public List<WeeklyBillItem> getItems() {
        return items;
    }

    public void setItems(List<WeeklyBillItem> items) {

        this.items = items;

        if (items != null) {

            for (WeeklyBillItem item : items) {

                item.setWeeklyBill(this);

            }

        }
    }

}