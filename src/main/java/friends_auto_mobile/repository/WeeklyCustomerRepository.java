package friends_auto_mobile.repository;

import friends_auto_mobile.entity.WeeklyCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeeklyCustomerRepository
        extends JpaRepository<WeeklyCustomer, Long> {

    // Find customer by name
    Optional<WeeklyCustomer> findByCustomerName(String customerName);

    // Search customers
    List<WeeklyCustomer> findByCustomerNameContainingIgnoreCase(String customerName);
}