package friends_auto_mobile.controller;

import friends_auto_mobile.entity.WeeklyCustomer;
import friends_auto_mobile.repository.WeeklyCustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekly-customers")
@CrossOrigin(origins = "*")
public class WeeklyCustomerController {

    @Autowired
    private WeeklyCustomerRepository weeklyCustomerRepository;

    // ==========================
    // ADD WEEKLY CUSTOMER
    // ==========================
    @PostMapping
    public WeeklyCustomer addCustomer(
            @RequestBody WeeklyCustomer customer) {

        return weeklyCustomerRepository.save(customer);

    }

    // ==========================
    // GET ALL CUSTOMERS
    // ==========================
    @GetMapping
    public List<WeeklyCustomer> getAllCustomers() {

        return weeklyCustomerRepository.findAll();

    }

    // ==========================
    // GET CUSTOMER BY ID
    // ==========================
    @GetMapping("/{id}")
    public WeeklyCustomer getCustomer(
            @PathVariable Long id) {

        return weeklyCustomerRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer Not Found"));

    }

    // ==========================
    // UPDATE CUSTOMER
    // ==========================
    @PutMapping("/{id}")
    public WeeklyCustomer updateCustomer(
            @PathVariable Long id,
            @RequestBody WeeklyCustomer updatedCustomer) {

        WeeklyCustomer customer =
                weeklyCustomerRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Customer Not Found"));

        customer.setCustomerName(updatedCustomer.getCustomerName());
        customer.setMobileNumber(updatedCustomer.getMobileNumber());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setPreviousBalance(updatedCustomer.getPreviousBalance());

        return weeklyCustomerRepository.save(customer);

    }

    // ==========================
    // DELETE CUSTOMER
    // ==========================
    @DeleteMapping("/{id}")
    public void deleteCustomer(
            @PathVariable Long id) {

        weeklyCustomerRepository.deleteById(id);

    }

}