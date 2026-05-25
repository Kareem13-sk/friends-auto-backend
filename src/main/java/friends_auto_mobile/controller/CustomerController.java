package friends_auto_mobile.controller;

import friends_auto_mobile.entity.Customer;
import friends_auto_mobile.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {

        return customerRepository.save(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer updatedCustomer) {

        Customer customer =
                customerRepository.findById(id).orElseThrow();

        customer.setCustomerName(
                updatedCustomer.getCustomerName());

        customer.setPhone(
                updatedCustomer.getPhone());

        customer.setAddress(
                updatedCustomer.getAddress());

        customer.setDiscountPercentage(
                updatedCustomer.getDiscountPercentage());

        customer.setTotalBalance(
                updatedCustomer.getTotalBalance());

        return customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {

        customerRepository.deleteById(id);
    }
}