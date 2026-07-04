package friends_auto_mobile.repository;

import friends_auto_mobile.entity.WeeklyEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyEntryRepository
        extends JpaRepository<WeeklyEntry, Long> {

    // All products of one customer
    List<WeeklyEntry> findByCustomerName(String customerName);

    // Pending products
    List<WeeklyEntry> findByStatus(String status);

    // Pending products of one customer
    List<WeeklyEntry> findByCustomerNameAndStatus(
            String customerName,
            String status
    );
}