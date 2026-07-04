package friends_auto_mobile.controller;

import friends_auto_mobile.entity.WeeklyEntry;
import friends_auto_mobile.repository.WeeklyEntryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weekly-entry")
@CrossOrigin(origins = "*")
public class WeeklyEntryController {

    @Autowired
    private WeeklyEntryRepository weeklyEntryRepository;

    // ===========================
    // ADD PRODUCT
    // ===========================
    @PostMapping
    public WeeklyEntry addProduct(
            @RequestBody WeeklyEntry entry) {

        entry.setEntryDate(LocalDate.now());

        entry.setStatus("PENDING");

        return weeklyEntryRepository.save(entry);
    }

    // ===========================
    // GET ALL PRODUCTS
    // ===========================
    @GetMapping
    public List<WeeklyEntry> getAllProducts() {

        return weeklyEntryRepository.findAll();

    }

    // ===========================
    // GET CUSTOMER PRODUCTS
    // ===========================
    @GetMapping("/customer/{customerName}")
    public List<WeeklyEntry> getCustomerProducts(
            @PathVariable String customerName) {

        return weeklyEntryRepository
                .findByCustomerNameAndStatus(
                        customerName,
                        "PENDING"
                );

    }

    // ===========================
    // DELETE PRODUCT
    // ===========================
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id) {

        weeklyEntryRepository.deleteById(id);

    }

}