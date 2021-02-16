package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;

import java.util.List;

public interface ProductService {
    ProductOutputDTO saveProduct (ProductInputDTO product);

    List<ProductDTO> getProducts ();

    boolean deleteProductById (String id);
}
