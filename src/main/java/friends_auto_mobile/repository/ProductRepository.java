package friends_auto_mobile.repository;

import friends_auto_mobile.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}