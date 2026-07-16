package friends_auto_mobile.repository;

import friends_auto_mobile.entity.CustomerBrandDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerBrandDiscountRepository
        extends JpaRepository<CustomerBrandDiscount, Long> {

    List<CustomerBrandDiscount> findByCustomerIdAndCustomerTypeAndBrand(
            Long customerId,
            String customerType,
            String brand
    );

    Optional<CustomerBrandDiscount> findByCustomerIdAndBrand(
            Long customerId,
            String brand
    );
}