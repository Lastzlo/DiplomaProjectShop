package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

        final List<ProductDTO> productDTOS = new LinkedList<ProductDTO> (){{
            products.forEach (product -> add (Product.toDTO (product)));
        }};

        return productDTOS;
    }

    @Override
    public boolean deleteProductById (String id) {

        final long productId = Long.parseLong (id);

        Optional<Product> product = productRepository.findById (productId);

        if (product.isPresent ()){
            productRepository.delete (product.get ());
            return true;
        } else {
            return false;
        }

    }
}
