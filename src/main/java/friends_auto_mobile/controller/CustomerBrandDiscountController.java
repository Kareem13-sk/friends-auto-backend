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
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}