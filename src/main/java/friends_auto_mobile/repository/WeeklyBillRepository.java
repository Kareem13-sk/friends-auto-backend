package friends_auto_mobile.repository;

import friends_auto_mobile.entity.WeeklyBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyBillRepository
        extends JpaRepository<WeeklyBill, Long> {

    List<WeeklyBill> findByCustomerName(String customerName);

}