package friends_auto_mobile.controller;

import friends_auto_mobile.entity.Customer;
import friends_auto_mobile.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
@RequestMapping("/customers")
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
    public String deleteCustomer(@PathVariable Long id) {

        try {

            customerRepository.deleteById(id);

            return "Customer Deleted Successfully";

        } catch (Exception e) {

            return "Delete Failed : " + e.getMessage();
        }
    }
}