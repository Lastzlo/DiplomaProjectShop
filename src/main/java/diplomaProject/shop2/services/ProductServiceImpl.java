package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDTO saveProduct (ProductDTO product) {
        final Product productFromDTO = ProductDTO.toProduct(product);

        final Product productFromDB = productRepository.save (productFromDTO);

        return Product.toDTO (productFromDB);
    }
}
