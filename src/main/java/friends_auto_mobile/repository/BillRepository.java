package friends_auto_mobile.repository;

import friends_auto_mobile.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByCustomerName(String customerName);
}