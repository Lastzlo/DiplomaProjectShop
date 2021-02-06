package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List<ProductDTO> getProducts () {
        final List<Product> products = productRepository.findAll ();

        final List<ProductDTO> productDTOS = new LinkedList<> (){{
            products.forEach (product -> add (Product.toDTO (product)));
        }};

        return productDTOS;
    }
}
