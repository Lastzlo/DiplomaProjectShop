package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO saveProduct (ProductDTO product);

    List<ProductDTO> getProducts ();

    boolean deleteProductById (String id);
}
