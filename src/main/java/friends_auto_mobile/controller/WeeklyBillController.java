package friends_auto_mobile.controller;

import friends_auto_mobile.entity.WeeklyBill;
import friends_auto_mobile.repository.WeeklyBillRepository;

import friends_auto_mobile.entity.WeeklyEntry;
import friends_auto_mobile.repository.WeeklyEntryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import friends_auto_mobile.entity.WeeklyBillItem;
import java.util.ArrayList;
import java.util.List;
import friends_auto_mobile.entity.WeeklyCustomer;
import friends_auto_mobile.repository.WeeklyCustomerRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weekly-bills")
@CrossOrigin(origins = "*")
public class WeeklyBillController {

    @Autowired
    private WeeklyBillRepository weeklyBillRepository;

    @Autowired
    private WeeklyEntryRepository weeklyEntryRepository;

    @Autowired
    private WeeklyCustomerRepository weeklyCustomerRepository;

    // ==========================
    // SAVE WEEKLY BILL
    // ==========================
    @PostMapping
    public WeeklyBill saveWeeklyBill(
            @RequestBody WeeklyBill bill) {

        bill.setBillDate(LocalDate.now());

        List<WeeklyEntry> entries =
                weeklyEntryRepository.findByCustomerNameAndStatus(
                        bill.getCustomerName(),
                        "PENDING"
                );

        if (entries.isEmpty()) {

            throw new RuntimeException(
                    "No pending products found."
            );

        }

        List<WeeklyBillItem> items = new ArrayList<>();

        for (WeeklyEntry entry : entries) {

            WeeklyBillItem item = new WeeklyBillItem();

            item.setProductName(entry.getProductName());

            item.setQuantity(entry.getQuantity());

            item.setActualPrice(entry.getActualPrice());

            item.setPercentage(entry.getPercentage());

            item.setFinalPrice(entry.getFinalPrice());

            item.setPrice(entry.getFinalPrice());

            item.setTotal(entry.getTotal());

            item.setWeeklyBill(bill);

            items.add(item);

        }

        bill.setItems(items);

// Save Weekly Bill
        WeeklyBill savedBill = weeklyBillRepository.save(bill);

// Mark all entries as BILLED
        // Mark all entries as BILLED
        for (WeeklyEntry entry : entries) {

            entry.setStatus("BILLED");

            weeklyEntryRepository.save(entry);

        }

// =========================
// UPDATE CUSTOMER BALANCE
// =========================

        WeeklyCustomer customer =
                weeklyCustomerRepository.findAll()
                        .stream()
                        .filter(c ->
                                c.getCustomerName()
                                        .equalsIgnoreCase(
                                                bill.getCustomerName()))
                        .findFirst()
                        .orElse(null);

        if (customer != null) {

            customer.setPreviousBalance(
                    bill.getBalanceAmount());

            weeklyCustomerRepository.save(customer);

        }

        return savedBill;
    }

    // ==========================
    // GET ALL WEEKLY BILLS
    // ==========================
    @GetMapping
    public List<WeeklyBill> getAllBills() {

        return weeklyBillRepository.findAll();

    }

    // ==========================
    // GET CUSTOMER BILLS
    // ==========================
    @GetMapping("/customer/{customerName}")
    public List<WeeklyBill> getCustomerBills(
            @PathVariable String customerName) {

        return weeklyBillRepository.findByCustomerName(
                customerName);

    }

    // ==========================
    // GET BILL BY ID
    // ==========================
    @GetMapping("/{id}")
    public WeeklyBill getBill(
            @PathVariable Long id) {

        return weeklyBillRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Weekly Bill Not Found"));

    }

    // ==========================
    // DELETE BILL
    // ==========================
    @DeleteMapping("/{id}")
    public void deleteBill(
            @PathVariable Long id) {

        weeklyBillRepository.deleteById(id);

    }
    // ==========================
// UPDATE WEEKLY BILL
// ==========================
    @PutMapping("/{id}")
    public WeeklyBill updateBill(
            @PathVariable Long id,
            @RequestBody WeeklyBill updatedBill) {

        WeeklyBill bill = weeklyBillRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Weekly Bill Not Found"));

        bill.setCustomerName(
                updatedBill.getCustomerName());

        bill.setTotalAmount(
                updatedBill.getTotalAmount());

        bill.setPreviousBalance(
                updatedBill.getPreviousBalance());

        bill.setPaidAmount(
                updatedBill.getPaidAmount());

        bill.setBalanceAmount(
                updatedBill.getBalanceAmount());

        bill.setItems(updatedBill.getItems());

        return weeklyBillRepository.save(bill);

    }

}