package diplomaProject.shop2.repos;

import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
