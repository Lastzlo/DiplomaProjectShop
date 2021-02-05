package diplomaProject.shop2.repos;

import diplomaProject.shop2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
