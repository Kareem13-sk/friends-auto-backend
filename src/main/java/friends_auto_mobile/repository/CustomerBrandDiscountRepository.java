package friends_auto_mobile.repository;

import friends_auto_mobile.entity.CustomerBrandDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerBrandDiscountRepository
        extends JpaRepository<CustomerBrandDiscount, Long> {

    // For Regular & Weekly Customers
    Optional<CustomerBrandDiscount> findByCustomerIdAndCustomerTypeAndBrand(
            Long customerId,
            String customerType,
            String brand
    );

    // Optional (keeps compatibility if you still use the old method anywhere)
    Optional<CustomerBrandDiscount> findByCustomerIdAndBrand(
            Long customerId,
            String brand
    );
}