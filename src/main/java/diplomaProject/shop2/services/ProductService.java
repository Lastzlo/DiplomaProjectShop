package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductOutputDTO saveProduct (ProductInputDTO product);

    List<ProductOutputDTO> getProducts ();

    boolean deleteProductById (String id);

    boolean addPhotoToProduct (MultipartFile multipartFile, Long id);
}
