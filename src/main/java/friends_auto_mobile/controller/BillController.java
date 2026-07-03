package friends_auto_mobile.controller;

import friends_auto_mobile.entity.Bill;
import friends_auto_mobile.entity.BillItem;
import friends_auto_mobile.entity.Customer;
import friends_auto_mobile.repository.BillRepository;
import friends_auto_mobile.repository.CustomerRepository;
import friends_auto_mobile.service.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bills")
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE
        }
)
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PdfService pdfService;

    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {

        // Set Bill inside Bill Items
        if (bill.getItems() != null) {
            for (BillItem item : bill.getItems()) {
                item.setBill(bill);
            }
        }

        double productsTotal =
                bill.getTotalAmount() == null
                        ? 0
                        : bill.getTotalAmount();

        double previousBalance =
                bill.getPreviousBalance() == null
                        ? 0
                        : bill.getPreviousBalance();

        double paid =
                bill.getPaidAmount() == null
                        ? 0
                        : bill.getPaidAmount();

        double grandTotal =
                productsTotal + previousBalance;

        bill.setBalanceAmount(grandTotal - paid);

        // Save Bill Date
        bill.setBillDate(LocalDate.now());

        // Update Customer Balance
        Customer customer =
                customerRepository.findAll()
                        .stream()
                        .filter(c ->
                                c.getCustomerName()
                                        .equalsIgnoreCase(
                                                bill.getCustomerName()))
                        .findFirst()
                        .orElse(null);

        if (customer != null) {

            customer.setTotalBalance(
                    bill.getBalanceAmount());

            customerRepository.save(customer);
        }

        return billRepository.save(bill);
    }

    @GetMapping
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @GetMapping("/customer/{customerName}")
    public List<Bill> getBillsByCustomer(
            @PathVariable String customerName) {

        return billRepository.findByCustomerName(customerName);
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long id) {

        Bill bill =
                billRepository.findById(id)
                        .orElseThrow();

        ByteArrayInputStream invoice =
                pdfService.generateInvoice(bill);

        HttpHeaders headers =
                new HttpHeaders();

        headers.add(
                "Content-Disposition",
                "inline; filename=invoice.pdf"
        );

        try {

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(invoice.readAllBytes());

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PutMapping("/{id}")
    public Bill updateBill(
            @PathVariable Long id,
            @RequestBody Bill updatedBill) {

        Bill bill =
                billRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Bill not found"));

        double productsTotal =
                bill.getTotalAmount() == null
                        ? 0
                        : bill.getTotalAmount();

        double previousBalance =
                bill.getPreviousBalance() == null
                        ? 0
                        : bill.getPreviousBalance();

        double paid =
                updatedBill.getPaidAmount() == null
                        ? 0
                        : updatedBill.getPaidAmount();

        double grandTotal =
                productsTotal + previousBalance;

        bill.setPaidAmount(paid);

        bill.setBalanceAmount(
                grandTotal - paid);

        // Keep original Bill Date
        if (bill.getBillDate() == null) {
            bill.setBillDate(LocalDate.now());
        }

        Customer customer =
                customerRepository.findAll()
                        .stream()
                        .filter(c ->
                                c.getCustomerName()
                                        .equalsIgnoreCase(
                                                bill.getCustomerName()))
                        .findFirst()
                        .orElse(null);

        if (customer != null) {

            customer.setTotalBalance(
                    bill.getBalanceAmount());

            customerRepository.save(customer);
        }

        return billRepository.save(bill);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(
            @PathVariable Long id) {

        billRepository.deleteById(id);
    }
}