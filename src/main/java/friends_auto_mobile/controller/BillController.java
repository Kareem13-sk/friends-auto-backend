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

        // IMPORTANT FIX

        if (bill.getItems() != null) {

            for (BillItem item : bill.getItems()) {

                item.setBill(bill);
            }
        }

        // BALANCE CALCULATION

        double balance =
                bill.getTotalAmount()
                        - bill.getPaidAmount();

        bill.setBalanceAmount(balance);

        // UPDATE CUSTOMER BALANCE

        Customer customer =
                customerRepository
                        .findAll()
                        .stream()
                        .filter(c ->
                                c.getCustomerName()
                                        .equalsIgnoreCase(
                                                bill.getCustomerName()))
                        .findFirst()
                        .orElse(null);

        if (customer != null) {

            customer.setTotalBalance(balance);

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

        return billRepository
                .findByCustomerName(customerName);
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long id) {

        Bill bill =
                billRepository
                        .findById(id)
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

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(
                            MediaType.APPLICATION_PDF)
                    .body(invoice.readAllBytes());

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}