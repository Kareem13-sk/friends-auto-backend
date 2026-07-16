package friends_auto_mobile.controller;

import friends_auto_mobile.entity.CustomerBrandDiscount;
import friends_auto_mobile.repository.CustomerBrandDiscountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand-discounts")
@CrossOrigin(origins = "*")
public class CustomerBrandDiscountController {

    @Autowired
    private CustomerBrandDiscountRepository repository;

    // =========================
    // SAVE
    // =========================
    @PostMapping
    public CustomerBrandDiscount save(
            @RequestBody CustomerBrandDiscount data) {

        List<CustomerBrandDiscount> existingList =
                repository.findByCustomerIdAndCustomerTypeAndBrand(
                        data.getCustomerId(),
                        data.getCustomerType(),
                        data.getBrand()
                );

        if (!existingList.isEmpty()) {

            CustomerBrandDiscount existing = existingList.get(0);

            existing.setCustomerName(data.getCustomerName());
            existing.setDiscountPercentage(data.getDiscountPercentage());

            return repository.save(existing);
        }

        return repository.save(data);
    }

    // =========================
    // GET ALL
    // =========================
    @GetMapping
    public List<CustomerBrandDiscount> getAll() {
        return repository.findAll();
    }

    // =========================
    // FIND DISCOUNT
    // =========================
    @GetMapping("/find")
    public CustomerBrandDiscount findDiscount(
            @RequestParam Long customerId,
            @RequestParam String customerType,
            @RequestParam String brand) {

        List<CustomerBrandDiscount> list =
                repository.findByCustomerIdAndCustomerTypeAndBrand(
                        customerId,
                        customerType,
                        brand
                );

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // =========================
    // UPDATE
    // =========================
    @PutMapping("/{id}")
    public CustomerBrandDiscount update(
            @PathVariable Long id,
            @RequestBody CustomerBrandDiscount data) {

        CustomerBrandDiscount existing =
                repository.findById(id)
                        .orElseThrow();

        existing.setCustomerId(data.getCustomerId());
        existing.setCustomerName(data.getCustomerName());
        existing.setCustomerType(data.getCustomerType()); // NEW
        existing.setBrand(data.getBrand());
        existing.setDiscountPercentage(data.getDiscountPercentage());

        return repository.save(existing);
    }
}