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

    @PostMapping
    public CustomerBrandDiscount save(
            @RequestBody CustomerBrandDiscount data) {

        return repository.save(data);
    }

    @GetMapping
    public List<CustomerBrandDiscount> getAll() {

        return repository.findAll();
    }

    @GetMapping("/find")
    public CustomerBrandDiscount findDiscount(
            @RequestParam Long customerId,
            @RequestParam String brand) {

        return repository
                .findByCustomerIdAndBrand(
                        customerId,
                        brand)
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        repository.deleteById(id);
    }
    @PutMapping("/{id}")
    public CustomerBrandDiscount update(
            @PathVariable Long id,
            @RequestBody CustomerBrandDiscount data) {

        CustomerBrandDiscount existing =
                repository.findById(id)
                        .orElseThrow();

        existing.setCustomerId(
                data.getCustomerId());

        existing.setCustomerName(
                data.getCustomerName());

        existing.setBrand(
                data.getBrand());

        existing.setDiscountPercentage(
                data.getDiscountPercentage());

        return repository.save(existing);
    }
}